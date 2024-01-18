<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-light navbar-light">
  <!-- Brand -->
  <a class="navbar-brand" href="${pageContext.request.contextPath}/member/index">
  <img src="${pageContext.request.contextPath}/image/logo.png"  width="60%"></a>
<ul class="navbar-nav" style="font-weight:900; font:22px;">

<c:if test = "${sessionScope.id==null}">
  <!-- Links --> 
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/member/memberinput">회원가입</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/member/loginForm">로그인</a>
    </li>
    </c:if>
    <c:if test = "${sessionScope.id!=null}">
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/member/memberinfo">[${id}]&nbsp;&nbsp;회원정보</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
    </li>
    </c:if>
    <!-- Dropdown -->
       <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        게시판[${boardid}]
      </a>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList?boardid=1">공지사항</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList?boardid=2">자유게시판</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/board/boardList?boardid=3">QnA</a>
      </div>
    </li>
  
  </ul>
</nav>
<br>
  


</body>
</html>