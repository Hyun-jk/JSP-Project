<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
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
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<body>	
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp" />		
			<div class="main-content">
				<div>
					<h3>공지사항 수정</h3>
				</div>
				<form action="adminBoardModify.do" method="post">
					<input type="hidden" name="aboard_num" value="${aboard.aboard_num}">
					<ul>
						<li>
							<label for="title">제목</label>
							<input type="text" name="title" id="title" maxlength="50" style="width:500px;height:45px;"
								   value="${aboard.title}">
						</li>
						<li>
							<label for="content">내용</label>
							<textarea rows="17" cols="65" name="content" id="content">${aboard.content}</textarea>
						</li>
					</ul>
					<div class ="align-right">
						<input class="point" type="submit" value="수정">
						<input class="point" type="button" value="목록" onclick="location.href='adminBoard.do'">
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>