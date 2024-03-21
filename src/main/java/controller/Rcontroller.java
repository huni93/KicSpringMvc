package controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.MemberMybatisDao;
import model.KicMember;



@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = "http://localhost:3000")
public class Rcontroller{
   
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
}