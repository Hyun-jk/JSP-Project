<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일대일 문의사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp"/>
		<div id="My-content">
			<h4>일대일문의사항</h4>
			<hr size="1" noshade width="100%">
			<c:forEach var="board" items="${list}">
			<ul>
				<li><a href="memberBoardInqueryDetail.do?aboard_num=${board.aboard_num}">${board.title}</a></li>
			</ul>
			<hr>
			<br>
			</c:forEach>
			<div class="align-right">
				<input type="button" value="글쓰기" onclick="location.href='memberBoardInqueryWriteForm.do'">
			</div>
			<div class="align-center">
				${pagingHtml}
			</div>
		</div>
	</div>
	
</body>
</html>