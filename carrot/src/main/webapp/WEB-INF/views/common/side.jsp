<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/haeun.css">
<!-- sidebar시작 -->
<div id="My-sidebar">
	<div class="user_photo">
		<c:if test="${!empty user_num && !empty user_photo}">
			<img src="${pageContext.request.contextPath}/upload/${user_photo}"
			             width="200" height="200" class="user_photo">
			<br><br>
			<div class="user_id"><span>${user_id}</span></div>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png"
			                   width="200" height="200" class="my-photo">
		</c:if>
	</div>
	<!-- 일반회원으로 로그인 -->
	<c:if test="${!empty user_num && user_auth == 2}">
	<h4>My Page</h4>
	<a href="${pageContext.request.contextPath}/member/myPageDetail.do">회원정보 수정</a>
	
	<h4>찜한 상품</h4>
	<a href="${pageContext.request.contextPath}/member/MyProduct.do">관심 상품</a>
	

	<h4>나의 거래 내용</h4>
	<div class="aa">
	<a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=1">구매내역</a> <br><br>
	<a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=2">판매 내역</a> <br><br>
	<a href="${pageContext.request.contextPath}/member/memberProduct.do?complete=0">판매중인 상품</a>
	</div>
	
	<h4>매너 평가</h4>
	<a href="${pageContext.request.contextPath}/">내 매너 점수</a>
	
	
	<h4>공지사항</h4>
	<div class="aa">
	<a href="${pageContext.request.contextPath}/board/memberBoard.do">공지사항</a><br><br>
	<a href="${pageContext.request.contextPath}/board/memberBoardFAQ.do">자주묻는 질문(FAQ)</a><br><br> 
	<a href="${pageContext.request.contextPath}/board/memberBoardInquery.do">1:1 문의</a>
	</div>
	</c:if>
	<!-- 일반회원으로 로그인 끝 -->
	
	<!-- 관리자로 로그인 시작 -->
	<c:if test="${!empty user_num && user_auth == 3}">
		<h4>회원관리</h4>
		<a href="${pageContext.request.contextPath}/member/memberList.do">회원관리</a>
		<hr class="line" noshade="noshade">
		
		<h4><a href="${pageContext.request.contextPath}/">상품관리</a></h4>
		<hr class="line" noshade="noshade">
		
		<h4><a href="${pageContext.request.contextPath}/board/adminBoard.do">공지사항</a></h4>
		<a href="${pageContext.request.contextPath}/board/adminBoardFAQ.do">자주묻는 질문(FAQ)</a> 
		<a href="${pageContext.request.contextPath}/board/adminBoardInquery.do">1:1 문의</a>
		<hr class="line" noshade="noshade">
	</c:if>
	<!-- 관리자로 로그인 끝 -->
</div>
<!-- sidebar끝 -->







