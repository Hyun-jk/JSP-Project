<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ 게시판 상세정보보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp" />
				<div class="main-content">
					<div>
						<h3 class="title">[공지사항]${board.title}</h3>
					</div>
					<div>
						<span class="date">[${board.reg_date}]</span>
						<p>${board.content}</p>
					</div>
				</div>
			</div>
		</div>
		<div class="page-footer">
			<div class="align-right paging">
				<input type="button" class="point" value="목록"
					onclick="location.href='memberBoardFAQ.do'">
			</div>
		</div>
	</div>
</body>
</html>

