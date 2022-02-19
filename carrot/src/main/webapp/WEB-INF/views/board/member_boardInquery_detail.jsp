<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일대일 게시판 상세정보보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp" />
		<div id="My-content">
			<h4>일대일 게시판</h4>
			<hr size="1" noshade width="100%">
			<span> ${board.title}<br>
			${board.reg_date}
			</span>
			<p>${board.content}</p>
			<hr size="1" noshade="noshade" width="100%">
			<div class="align-right">
				<input type="button" value="목록" onclick="location.href='memberBoardInquery.do'">	
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						//이벤트 연결
						delete_btn.onclick = function(){
							let choice = confirm('삭제하시겠습니까?');
							if (choice) {
								location.replace('adminBoardDelete.do?aboard_num=${board.aboard_num}');
							}
						}
				</script>
			</div>
		</div>
	</div>
</body>
</html>