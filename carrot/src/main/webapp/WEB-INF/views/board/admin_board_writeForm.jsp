<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
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
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<body>	
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp" />		
			<div class="main-content">
				<div>
					<h3>공지사항 글쓰기</h3>
				</div>
				<form id="write_form"  class="align-left" action="adminBoardWrite.do">
					<ul>
						<li>
							<label for="title">제목</label>
							<input type="text" name="title" id="title" maxlength="50" style="width:500px;height:45px;">
						</li>
						<li>
							<label for="content">내용</label>
							<textarea rows="17" cols="65" name="content" id="content"></textarea>
						</li>
					</ul>
					<div class ="align-right">
						<input class="point" type="submit" value="등록">
						<input class="point" type="button" value="목록" onclick="location.href='adminBoard.do'">
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>	
</body>
</html>
