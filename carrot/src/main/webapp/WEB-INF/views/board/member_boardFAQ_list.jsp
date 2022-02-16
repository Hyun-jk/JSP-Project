<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
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
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div>
		<jsp:include page="/WEB-INF/views/common/side.jsp"/>
		<div id="My-content">
			<h4>FAQ</h4>
			<form action="ajax로 처리" method="get" id="search_form">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1">운영정책</option>
							<option value="2">구매/판매</option>
							<option value="3">거래매너</option>
							<option value="4">이용제재</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
			<hr size="1" noshade width="100%">
			<c:if test="${count ==0 }">
				등록된 게시물이 없습니다.
			</c:if>
			<c:if test="${count >0}">
			<c:forEach var="board" items="${list}">
			<ul>
				<li><a href="memberBoardFAQDetail.do?aboard_num=${board.aboard_num}">${board.title}</a></li>
			</ul>
			<hr>
			<br>
			</c:forEach>
			</c:if>
			<div class="align-center">
				${pagingHtml}
			</div>
		</div>
	</div>
	
</body>
</html>