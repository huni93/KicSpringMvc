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
<h4 class ="text-center">게시판 입력</h4>
<form class="container"  action="boardPro" method = "post" enctype="multipart/form-data">
  <div class="form-group">
    <label for="name">작성자:</label>
    <input type="text" class="form-control" placeholder="Enter name" id="name" name="name">
  </div>
  <div class="form-group">
    <label for="pass">비밀번호:</label>
    <input type="password" class="form-control" placeholder="Enter pass" id="pass" name="pass">
  </div>
  <div class="form-group">
    <label for="subject">제목:</label>
    <input type="text" class="form-control" placeholder="Enter subject" id="subject" name="subject">
  </div>
  <div class="form-group">
  <label for="content">내용:</label>
  <textarea class="form-control" rows="5" id="content" name="content"></textarea>
</div>
<div class="form-group">
    <label for="file">파일:</label>
    <input type="file" class="form-control" placeholder="Enter file1" id="file" name="f">
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
</div>
</body>
</html>