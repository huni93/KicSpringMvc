<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="container">
      <div class="input-form-backgroud row">
         <div class="input-form col-md-12 mx-auto">
            <h4 class="mb-3">회원정보</h4>
            <table class="table">
            <tr><th>name</th><th>value</th> </tr>
            <tr><td>사진</td><td><img src ="${pageContext.request.contextPath}/image/member/picture/${mem.picture}" width="100px"  height="120px"></td><tr>
            <tr><td>id</td><td>${mem.id}</td><tr>
            <tr><td>name</td><td>${mem.name}</td><tr>
            <tr><td>gender</td><td>${mem.gender}</td><tr>
            <tr><td>tel</td><td>${mem.tel}</td><tr>
            <tr><td>email</td><td>${mem.email}</td><tr>
            <tr><td>picture</td><td>&nbsp;</td><tr>                     
             <tr>
             <td colspan = "2" class = "w3-center">
             <a class="btn btn-primary"    
             href="${pageContext.request.contextPath}/member/memberUpdateForm">회원정보수정</a>
             <a class="btn btn-primary" 
             href="${pageContext.request.contextPath}/member/memberDeleteForm">회원탈퇴</a>
             <a class="btn btn-primary" 
             href="${pageContext.request.contextPath}/member/memberPassForm">비밀번호수정</a>
           </td>
           </tr>
            </table>
         </div>
      </div>
   
   </div>
</body>
</html>