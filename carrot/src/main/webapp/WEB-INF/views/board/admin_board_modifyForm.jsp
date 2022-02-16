<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#modify_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('제목을 입력하세요');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요');
				$('#content').val('').focus();
				return false;
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp" />		
		<div id="My-content">
			<div class="page-main">
				<h2>공지사항 수정</h2>
				<form action="adminBoardModify.do" method="post" id="modify_form">
					<input type="hidden" name="aboard_num" value="${aboard.aboard_num}">
					<ul>
						<li>
							<label for=title>제목</label>
							<input type="text" name="title" id="title" value="${aboard.title}">
						</li>
						<li>
							<label for="content">내용</label>
							<textarea name="content" id="content" rows="5" cols="30">${aboard.content}</textarea>
						</li>
					</ul>
					<div class="align-right">
						<input type="submit" value="수정">
						<input type="button" value="목록" onclick="location.href='adminBoard.do'">
					</div>
				</form>
			<div>
			</div>
		</div>
	</div>
</div>
</html>