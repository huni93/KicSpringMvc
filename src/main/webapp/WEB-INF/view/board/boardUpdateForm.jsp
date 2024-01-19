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
<h4 class ="text-center">게시판 수정</h4>
<form class="container"  action="boardUpdatePro" method = "post" enctype="multipart/form-data">
 <input type = "hidden" name = "num" value="${board.num}">
 <input type = "hidden" name = "originfile" value="${board.file1}">
  <div class="form-group">
    <label for="name">작성자:</label>
    <input type="text" class="form-control" placeholder="Enter name" id="name" name="name" value="${board.name}">
  </div>
  <div class="form-group">
    <label for="pass">비밀번호:</label>
    <input type="password" class="form-control" placeholder="Enter pass" id="pass" name="pass">
  </div>
  <div class="form-group">
    <label for="subject">제목:</label>
    <input type="text" class="form-control" placeholder="Enter subject" id="subject" name="subject" value="${board.name}">
  </div>
  <div class="form-group">
  <label for="content">내용:</label>
  <textarea class="form-control" rows="5" id="content" name="content" >${board.content}</textarea>
</div>
<div class="form-group">
    <label for="file">파일:${board.file1}</label>
    <input type="file" class="form-control" id="file" name="f">
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
</div>
</body>
</html>