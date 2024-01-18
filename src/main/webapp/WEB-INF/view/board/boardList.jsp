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
  <h2 class ="text-center">${boardName}[${boardCount}]</h2>
   <p  class="text-right"> <a class="btn btn-primary"  href="boardForm">게시판입력</a></p>               
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>번호</th>
        <th>작성자</th>
        <th>제목</th>
        <th>날짜</th>
        <th>조회수</th>
        <th>파일</th>
      </tr>
    </thead>
    <tbody>
    <c:set var = "boardNum" value="${boardNum}"/>
    
    
    
  
    <c:forEach var="b" items="${li}">  
      <tr>
        <td>${boardNum}</td>
          <c:set var = "boardNum" value="${boardNum-1}"/>
        <td>${b.name}</td>
        <td><a href="boardInfo?num=${b.num}">${b.subject}</a></td>
        <td>${b.regdate}</td>
        <td>${b.readcnt}</td>
        <td>${b.file1}</td>
      </tr>
      </c:forEach>  
    </tbody>
  </table>
  <ul class="pagination justify-content-center text-center">
  <li class="page-item <c:if test="${start<=bottomLine}"> disabled  </c:if> ">
    <a class="page-link" href="${pageContext.request.contextPath}/board/boardList?pageNum=${start-bottomLine}">Previous</a></li>
  
  <c:forEach var = "p" begin ="${start}" end="${end}">
 
  <li class="page-item <c:if test="${pageInt==p}"> active  </c:if>"><a class="page-link" 
       href="${pageContext.request.contextPath}/board/boardList?pageNum=${p}">${p}</a></li>
  </c:forEach>
 
  <li class="page-item <c:if test="${end>=maxPage}"> disabled  </c:if>"> <a class="page-link" href="${pageContext.request.contextPath}/board/boardList?pageNum=${start+bottomLine}">Next</a></li>
</ul>
</div>
</body>
</html>