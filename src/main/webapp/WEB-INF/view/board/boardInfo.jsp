<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	let count = ${count}
function enterkey(num) {
	if(window.event.keyCode==13){
		commentPro(num)
	}
}







function commentPro(num) {
	count=count+1
	let comment = document.querySelector("#comment").value
	alert(comment)
	const xhttp = new XMLHttpRequest()
	let url = "${pageContext.request.contextPath}/board/boardCommentPro?comment="
			+comment+"&boardnum="+num+"&count=" +count
	xhttp.open("GET",url,true)
	xhttp.send()
	xhttp.onreadystatechange = function() {		
		if(this.readyState ==4 && this.status==200) {
		alert("data:"+this.responseText)
		
	let commentList = document.querySelector("#commentList")
	commentList.innerHTML = this.responseText + "<br>" + commentList.innerHTML
	}
		document.querySelector("#comment").value=""
}}
</script>
</head>
<body>

	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3">게시판</h4>
				<table class="table">
					<tr>
						<th>name</th>
						<th>value</th>
					</tr>
					<tr>
						<td>작성자</td>
						<td>${board.name}</td>
					<tr>
					<tr>
						<td>파일</td>
						<td>${board.file1}<img
							src="${pageContext.request.contextPath}/image/board/${board.file1}"
							width="100px" height="120px"></td>
					<tr>
					<tr>
						<td>제목</td>
						<td>${board.subject}</td>
					<tr>
					<tr>
						<td>내용</td>
						<td>${board.content}</td>
					<tr>
					<tr>
						<td>날짜</td>
						<td>${board.regdate}</td>
					<tr>
					<tr>
						<td>조회수</td>
						<td>${board.readcnt}</td>
					<tr>
					<tr>
						<td colspan="2" class="text-right"><a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardUpdateForm?num=${board.num}">변경</a>
							<a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardDeleteForm?num=${board.num}">삭제</a>
							<a class="btn btn-primary"
							href="${pageContext.request.contextPath}/board/boardList">목록</a>
						</td>
					</tr>
				</table>

				<div class="row">
					<div class="col-sm-1">답변글</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="comment"
							onkeyup="enterkey('${board.num}')">
					</div>
					<div class="col-sm-1">
						<button class="btn btn-primary"
							onclick="commentPro('${board.num}')">저장</button>
					</div>
				</div>
				<c:set var="ser" value="${count}"/>
				
				<div class="row" id="commentList">
					<c:forEach var="c" items="${commentLi }">
					<div class="col-sm-1">&nbsp;</div>
						<div class="col-sm-1">${ser }</div>
						<c:set var="ser" value="${ser-1}"/>
						<c:set var="boardNum" value="${boardNum }"/>
						<div class="col-sm-9">${c.content }</div>
					<div class="col-sm-1">&nbsp;</div>
	
					
					
					
					
					
					
					
					
					
					</c:forEach>
						
				</div>
				
				
			</div>
		</div>
	</div>
</body>
</html>