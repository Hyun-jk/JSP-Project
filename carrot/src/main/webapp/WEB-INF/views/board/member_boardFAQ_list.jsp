<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
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
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp"/>
				<div id="My-content">
					<div>
						<h3 class="title">FAQ</h3>
					</div>
					<form action="memberBoardFAQ.do" method="get" id="search_form">
						<ul class="search">
							<li>
								<select name="keyfield">
									<option value="1" <c:if test="${param.keyfield ==1}">selected</c:if>>운영정책</option>
									<option value="2" <c:if test="${param.keyfield ==2}">selected</c:if>>구매/판매</option>
									<option value="3" <c:if test="${param.keyfield ==3}">selected</c:if>>거래매너</option>
									<option value="4" <c:if test="${param.keyfield ==4}">selected</c:if>>이용제재</option>
								</select>
							</li>
							<li>
								<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
							</li>
							<li>
								<input type="submit" value="검색">
							</li>
						</ul>
					</form>
					<div>
						<c:if test="${count ==0 }">
						등록된 게시물이 없습니다.
						</c:if>
						<c:if test="${count >0}">
						<c:forEach var="board" items="${list}">
						<ul class="content">
							<li id="notice">
								<a href="memberBoardFAQDetail.do?aboard_num=${board.aboard_num}">${board.title}</a>
							</li>
						</ul>
							<hr>
						</c:forEach>
						</c:if>		
					</div>
				</div>
			</div>
			
			<div class="page-footer">
				<div class="align-center paging">
					${pagingHtml}
				</div>
			</div>
		</div>
	</div>
</body>
</html>