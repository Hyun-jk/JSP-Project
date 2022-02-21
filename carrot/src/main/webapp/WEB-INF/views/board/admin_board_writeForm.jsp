<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#write_form').submit(function(){
		if($('#title').val().trim()==''){
			alert('제목을 입력하세요');
			$('#title').val('').focus();
			return false;
		}
		if($('#content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#content').val('').focus();
			return false;
		}
	});
});
</script>
</head>
<body>	
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp" />		
		<div id="My-content">
			<h2>글쓰기</h2>
			<hr size="1" noshade width="100%">
			<form id="write_form" action="adminBoardWrite.do">
				<ul>
					<li>
						<label for="title">제목</label>
						<input type="text" name="title" id="title" maxlength="50">
					</li>
					<li>
						<label for="content">내용</label>
						<textarea rows="5" cols="30" name="content" id="content"></textarea>
					</li>
				</ul>
				<div class="align-right">
					<input type="submit" value="등록">
					<input type="button" value="목록" onclick="location.href='adminBoard.do'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>