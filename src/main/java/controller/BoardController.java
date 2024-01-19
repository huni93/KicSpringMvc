package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import dao.BoardMybatisDao;

//import dao.BoardMybatisDao;


import model.Board;
import model.Comment;
import model.KicMember;

@Controller
@RequestMapping("/board/")
public class BoardController {
	@Autowired
	BoardMybatisDao bd;
	HttpSession session;
	HttpServletRequest req;

	
	@ModelAttribute
	protected void init(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		this.session = request.getSession();
		this.req=request;
		
	}
	
	@RequestMapping("index") //~~/board/index
	public String index() throws Exception {
		return "board/index";
	}
	
	@RequestMapping("boardForm") 
	public String boardForm() throws Exception {
		return "board/boardForm";
	}
	
	@RequestMapping("boardPro") 
	public String boardPro(@RequestParam("f") MultipartFile multipartFile, Board board) throws Exception {
		
		String path =
				req.getServletContext().getRealPath("/")+"image/board/";
		String filename = null;
		String msg = "게시물 등록 실패";
		String url = "/board/boardForm";
		
		String boardid = (String) session.getAttribute("boardid");
		if(boardid==null) boardid = "1";				
		board.setBoardid(boardid);
		
		if(!multipartFile.isEmpty()) {
			File file = new File(path,multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			try {
				multipartFile.transferTo(file);
				board.setFile1(filename);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
					
		int num = bd.insertBoard(board);
		if(num>0) {
			msg = "게시물 등록 성공";
			url = "/board/boardList";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "alert";
	}
	@RequestMapping("boardList") 
	public String boardList(String boardid, String pageNum) throws Exception {
		// TODO Auto-generated method stub
		
		//board session 처리한다.		
		if(boardid!=null) { //? boardid = 3
			session.setAttribute("boardid", boardid);
		    session.setAttribute("pageNum", "1");
		}
		boardid = (String) session.getAttribute("boardid");
		if(boardid==null) boardid = "1";
		String boardName = "";
		switch(boardid) {
		case "1":
			boardName = "공지사항";
			break;
		case "2":
			boardName = "자유게시판";
			break;
		case "3":
			boardName = "QnA";
			break;
		}
		//page 설정
		if(pageNum!=null) { 
			session.setAttribute("pageNum", pageNum);}
		
		pageNum = (String) session.getAttribute("pageNum");
		if(pageNum == null) pageNum ="1";
		
	
		
		int limit = 3; //한페이장 게시글 갯수
		int pageInt = Integer.parseInt(pageNum); //페이지 번호
		int boardCount = bd.boardCount(boardid); //전체 개시글 갯수
		
		int boardNum = boardCount -((pageInt-1)*limit);
		
		List<Board> li = bd.boardList(pageInt,limit,boardid);
		
		//pagging
		int bottomLine =3;
		int start = (pageInt-1)/bottomLine * bottomLine +1; //1,2,3->1 ,,4,5,6->4
		int end = start + bottomLine -1;
		int maxPage = (boardCount/limit) + (boardCount % limit ==0?0:1);
		if (end > maxPage)
			end = maxPage;
				
		req.setAttribute("bottomLine", bottomLine);
		req.setAttribute("start", start);
		req.setAttribute("end", end);
		req.setAttribute("maxPage", maxPage);
		req.setAttribute("pageInt", pageInt);
		
		req.setAttribute("li", li);
		req.setAttribute("boardName", boardName);
		req.setAttribute("boardCount", boardCount);
		req.setAttribute("boardNum", boardNum);
		
		
		return "board/boardList";
}
	
	@RequestMapping("boardInfo") 
	public String boardInfo(int num) throws Exception {
		// TODO Auto-generated method stub
						
		Board board = bd.oneBoard(num);
		List<Comment> commentLi=bd.commentList(num);
		int count = commentLi.size();
		
		req.setAttribute("commentLi", commentLi);
		
		req.setAttribute("board", board);
		req.setAttribute("count", count);
		
		return "board/boardInfo";
	}
	
	@RequestMapping("boardUpdateForm") 
	public String boardUpdateForm(int num) throws Exception {
		// TODO Auto-generated method stub
	
		Board board = bd.oneBoard(num);		
		req.setAttribute("board", board);
		return "board/boardUpdateForm";
}
	
	@RequestMapping("boardUpdatePro") 
	public String boardUpdatePro(@RequestParam("f") MultipartFile multipartFile, Board board) throws Exception {

		String path =
				req.getServletContext().getRealPath("/")+"image/board/";
		String filename = null;		
	
		Board originboard = bd.oneBoard(board.getNum());
		
		String msg = "게시물 수정 실패";
		String url = "/board/boardForm?num="+originboard.getNum();
		if(originboard.getPass().equals(board.getPass())){
					
			if(!multipartFile.isEmpty()) {
				File file = new File(path,multipartFile.getOriginalFilename());
				filename = multipartFile.getOriginalFilename();
				try {
					multipartFile.transferTo(file);
					board.setFile1(filename);
				}catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				board.setFile1(originboard.getFile1());
			}
						
		System.out.println(board);
		int count = bd.updateBoard(board);		
		if (count>0) {
			msg="게시판 수정 완료";
			url="/board/boardInfo?num="+originboard.getNum();
		}
	    }else  {
	    	msg = "비밀번호를 확인하세요.";
	    }
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "alert";
			
}
	
	@RequestMapping("boardDeleteForm") 
	public String boardDeleteForm() throws Exception {		
		req.setAttribute("num", req.getParameter("num"));
		return "board/boardDeleteForm";
}
	
	@RequestMapping("boardDeletePro")
	public String boardDeletePro(int num) throws Exception {
		
		Board board = bd.oneBoard(num);
		String msg = "삭제 불가합니다";
		String url = "/board/boardDeleteForm?num="+num;
		if (board.getPass().equals(req.getParameter("pass"))) {
			int count = bd.boardDelete(num);
			if (count>0) {
				msg = "게시글이 삭제 되었습니다";
				url = "/board/boardList";
			}
			
		} else {
			msg = "비밀번호 확인하세요";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "alert";	
		
	}
	
	@RequestMapping("boardCommentPro") 
	public View boardCommentPro(String comment, int boardnum) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * req.setAttribute("comment", req.getParameter("comment"));
		 * req.setAttribute("boardnum", req.getParameter("boardnum"));
	*/		
		bd.insertComment( comment,boardnum);
		Comment c= new Comment();
		c.setNum(boardnum); 
 		c.setContent(comment);
		req.setAttribute("c", c);
		req.setAttribute("count", req.getParameter("count"));
		//viewresolver 적용안됨
		return new InternalResourceView("/single/boardCommentPro.jsp");
	}
}

