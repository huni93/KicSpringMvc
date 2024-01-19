package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;


//import dao.MemberDao;
  import dao.MemberMybatisDao;
//import kic.mskim.Login;
//import kic.mskim.MskimRequestMapping;
//import kic.mskim.RequestMapping;
import model.KicMember;

@Controller
@RequestMapping("/member/")
public class MemberController  {
	
	@Autowired
	 MemberMybatisDao md ;
	HttpSession session;
	HttpServletRequest request;
	
	@ModelAttribute
	protected void init(HttpServletRequest req) throws ServletException, IOException {
		this.request = req;
		
		request.setCharacterEncoding("utf-8");
		this.session = req.getSession();
		//super.service(request, resp);
	}
	
	@RequestMapping("memberinput")
	public String memberinput() throws Exception {
		// TODO Auto-generated method stub
		return "member/memberinput";
	}
	
	@RequestMapping("loginForm")
	public String loginForm() throws Exception {
		// TODO Auto-generated method stub
		return "member/loginForm";
	}
	
	@RequestMapping("index")
	public String index() throws Exception {
		// TODO Auto-generated method stub
		return "member/index";
	}
	
	@RequestMapping("logout")
	public String logout() throws Exception {
		
		session.invalidate();
		request.setAttribute("msg", "logout 했습니다.");
		request.setAttribute("url", "/member/loginForm");
		
		return "alert";
	}
	
	
	@RequestMapping("memberinfo")
	public String memberinfo() throws Exception {	
			
		String login = (String) session.getAttribute("id");
		KicMember mem = md.oneMember(login);
		request.setAttribute("mem", mem);
		return "member/memberinfo";
	}
	
	@RequestMapping("loginPro")
	public String loginPro(String id, String pass) throws Exception {
		KicMember mem = md.oneMember(id);
		
		HttpSession session=request.getSession();

		String msg = "아이디를 확인하세요";
		String url = "/member/loginForm";
		if(mem != null) { //id 존재할때
			if (pass.equals(mem.getPass())) { //login ok
				session.setAttribute("id", id);
			msg = mem.getName() + "님이 로그인 하셨습니다.";
		    url = "/member/index";
			}else {
				msg = "비밀번호를 확인하세요";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "alert";
	}

	@RequestMapping("memberPro")
	public String memberPro(KicMember kicmem) throws Exception {
	
		System.out.println(kicmem);
		int num = md.insertMember(kicmem);
		
		String msg = "저장 하였습니다.";
		String url = "/member/loginForm";
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "alert";
	}
	//@Login(key = "id")
	@RequestMapping("memberUpdateForm")
	public String memberUpdateForm() throws Exception {		
		String login =  (String) session.getAttribute("id");		
		KicMember mem = md.oneMember(login);		
		request.setAttribute("mem", mem);			
		return "member/memberUpdateForm";
	}
	
	@RequestMapping("memberUpdatePro")
	public String memberUpdatePro(KicMember mem) throws Exception {
		
		String login =  (String) session.getAttribute("id");
		System.out.println(mem + "======================");		
		KicMember memdb = md.oneMember(login);  //db에서 넘어온자료

		String msg = "수정 되지 않았습니다.";
		String url ="/member/memberUpdateForm";
		if(memdb!=null) {
			if(memdb.getPass().equals(mem.getPass())) {  //수정 ok
				  msg = "수정 되었습니다.";
				  md.updateMember(mem);
				  url ="/member/memberinfo";
			}else {
				msg="비밀번호가 틀렸습니다.";
			}
		}		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return "alert";
	}
	
	@RequestMapping("memberDeleteForm")
	public String memberDeleteForm() throws Exception {		
		
		return "member/memberDeleteForm";
	}
	
	@RequestMapping("memberDeletePro")
	public String memberDeletePro(String pass) throws Exception {
		
	String login =  (String) session.getAttribute("id");
	KicMember memdb = md.oneMember(login);
	String msg = "탈퇴되지 않았습니다.";
	String url ="/member/memberDeleteForm";

	if(memdb!=null) {
		if(memdb.getPass().equals(pass)) {  //비밀번호 확인
			  msg = "탈퇴 되었습니다.";
			  md.deleteMember(login);
			  session.invalidate();
			  url ="/member/index";
		}else {
			msg="비밀번호가 틀렸습니다.";
		}
	}
	request.setAttribute("msg", msg);
	request.setAttribute("url", url);
	
	return "alert";
	}
	
	@RequestMapping("memberPassForm")
	public String memberPassForm() throws Exception {		
		
		return "member/memberPassForm";
	}
	
	@RequestMapping("memberPassPro")
	public String memberPassPro(String pass,String chgpass) throws Exception {
		
	String login =  (String) session.getAttribute("id");
	//1. md.oneMember(login)
	//2. db 저장 pass확인 : msg, url 변경
	//3. md.passMember()
	KicMember memdb = md.oneMember(login);

	String msg = "비밀번호 수정을 실패 했습니다.";
	String url ="/member/memberPassForm";

	if(memdb!=null) {
		if(memdb.getPass().equals(pass)) {  
			  md.passMember(login, chgpass);
			  msg = login+"님이 비밀번호가 수정 되었습니다.";	  
			  url = "/member/index";
		}else {
			msg="비밀번호가 틀렸습니다.";
		}
	}
	request.setAttribute("msg", msg);
	request.setAttribute("url", url);
	
	return "alert";
	}
	
	@RequestMapping("pictureimgForm")
	public View pictureimgForm() throws Exception {		
		
		return new InternalResourceView("/single/pictureimgForm.jsp");
	}
	
	@RequestMapping("picturePro")
	public View picturePro(@RequestParam("picture") MultipartFile multipartFile) throws Exception {		
		String path =request.getServletContext().getRealPath("/")
				     +"/image/member/picture/";
		System.out.println(path);
		String filename=null;
		if(!multipartFile.isEmpty()) {
			File file = new File(path,multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			try {
				multipartFile.transferTo(file);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("filename", filename);
		return new InternalResourceView("/single/picturePro.jsp");
	}
	
	
}
