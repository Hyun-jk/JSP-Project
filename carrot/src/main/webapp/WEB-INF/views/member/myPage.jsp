<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>My page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPageMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div><jsp:include page="/WEB-INF/views/common/header.jsp"/>	
<!-- sidebar시작 -->
	<div class="mypage-photo">
		<c:if test="${!empty user_num && !empty user_photo}">
			<img src="${pageContext.request.contextPath}/upload/${user_photo}"
			             width="200" height="200" class="mypage-photo">
			<br><br>
			<div class="user_id"><span>${user_id}님, 환영합니다!</span></div>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png"
			                   width="200" height="200" class="mypage-photo">
		</c:if>
	</div>
	<div class="my-pageMain">
	<!-- 일반회원으로 로그인 -->
	<c:if test="${!empty user_num && user_auth == 2}">
	
	<ul>
	<li><h4>My Page</h4></li>
	<li><a href="${pageContext.request.contextPath}/member/myPageDetail.do">회원정보 수정</a><li>	
	</ul>

	<ul>
	<li><h4>찜한 상품</h4></li>
	<li><a href="${pageContext.request.contextPath}/member/MyProduct.do">관심 상품</a></li>
	</ul>
     
    <ul>
	<li><h4>나의 거래 내용</h4></li>
	<li><a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=1">구매내역</a><br><br></li>
	<li><a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=2">판매 내역</a><br><br></li>
	<li><a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=0">판매중인 상품</a></li>
	</ul>
	
	<ul>
	<li><h4>매너 평가</h4></li>
	<li><a href="${pageContext.request.contextPath}/">내 매너 점수</a></li>
	</ul>
	
	<ul>
	<li><h4>공지사항</h4></li>
	<li><a href="${pageContext.request.contextPath}/board/memberBoard.do">공지사항</a><br><br></li>
	<li><a href="${pageContext.request.contextPath}/board/memberBoardFAQ.do">자주묻는 질문(FAQ)</a><br><br></li>
	<li><a href="${pageContext.request.contextPath}/board/memberBoardInquery.do">1:1 문의</a></li>
	
	</ul>
	</c:if></div>
	<!-- 일반회원으로 로그인 끝 -->
	
	<!-- 관리자로 로그인 시작 -->
	<div class="admin_page">
	<c:if test="${!empty user_num && user_auth == 3}">
		<ul>
		<li><h4>회원관리</h4></li>
		<li><a href="${pageContext.request.contextPath}/member/memberList.do">회원목록</a></li>
		</ul>
		<ul>
		<li><h4>상품관리</h4></li>
		<li><a href="${pageContext.request.contextPath}/">상품목록</a></li>
		</ul>
		
		<ul>
		<li><h4>공지사항</h4></li>
		<li><a href="${pageContext.request.contextPath}/board/adminBoard.do">공지사항</a><br><br><li>
		<li><a href="${pageContext.request.contextPath}/board/adminBoardFAQ.do">자주묻는 질문(FAQ)</a><br><br></li> 
		<li><a href="${pageContext.request.contextPath}/board/adminBoardInquery.do">1:1 문의</a></li>
		</ul>
	</c:if></div>
	<!-- 관리자로 로그인 끝 -->
</div>
</body>
</html>