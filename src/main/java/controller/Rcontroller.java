package controller;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardMybatisDao;
import dao.MemberMybatisDao;
import model.Board;
import model.KicMember;



@RestController
@RequestMapping("/rest/")
//allowCredentials = "true" 쿠키 보낼때 꼭 작성 해야함.
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
public class Rcontroller{
   
   @Autowired
   MemberMybatisDao md ;
   HttpSession session;
   HttpServletRequest request;
   @Autowired
   BoardMybatisDao bd;

   @ModelAttribute
   protected void init(HttpServletRequest req) throws ServletException, IOException {
      this.request = req;
      
      request.setCharacterEncoding("utf-8");
      this.session = req.getSession();
      //super.service(request, resp);
   }
@GetMapping("index")
   public String index()throws Exception{
      //http://localhost:8080/KicSpringMvc/rest/index
      return "index";
   }
   
   

@PostMapping("memberPro")
   public String memberPro(KicMember kicmem) throws Exception {
   
      System.out.println(kicmem);
      int num = md.insertMember(kicmem);
      
      String msg = "저장하지않았습니다";
      if(num==1) {
         msg = "저장하였습니다";
      }
      return msg;
   }

@PostMapping("loginPro")
public ResponseEntity loginPro(String id, String pass,
	HttpServletResponse response,
	@CookieValue(value="id",required = false) Cookie cookie
		) throws Exception {

   System.out.println(id+","+pass);
   if(cookie==null) {
	   cookie = new Cookie("id", "init");
	   response.addCookie(cookie);
   }else {
	   System.out.println(cookie.getValue());
   }
   
   cookie.setValue(id);
   cookie.setDomain("localhost");
   cookie.setPath("/");
   cookie.setMaxAge(30); // 정하지 않으면 브라우저가 종료 되면 삭제 된다.
   cookie.setSecure(true); //ssl 연결시에 만 가능함 단 localhost 된다
   response.addCookie(cookie);
   
   
   //return new ResponseEntity("{\"message\":\"login_success\"}", HttpStatus.UNAUTHORIZED);
   return new ResponseEntity("{\"message\":\"login_success\"}", HttpStatus.OK);
   
}

@PostMapping("boardPro") 
public String boardPro(@RequestParam("f") MultipartFile multipartFile, Board board) throws Exception {
	
	String path =
			request.getServletContext().getRealPath("/")+"image/board/";
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
		return "msg";
}

@RequestMapping("boardList") 
public Map<String,Object> boardList(String boardid, String pageNum) throws Exception {
	// TODO Auto-generated method stub
	Map<String,Object> map = new HashMap<String,Object>();
	
	if(boardid==null)boardid="1";
	if(pageNum==null)pageNum="1";
	
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
			
	map.put("bottomLine", bottomLine);
	map.put("start", start);
	map.put("end", end);
	map.put("maxPage", maxPage);
	map.put("pageInt", pageInt);
	
	map.put("li", li);
	map.put("boardName", boardName);
	map.put("boardCount", boardCount);
	map.put("boardNum", boardNum);
	
	
	return map;
}
}