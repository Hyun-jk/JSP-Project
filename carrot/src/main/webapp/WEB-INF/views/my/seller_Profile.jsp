<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자 프로필</title>
</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<h2>판매자정보</h2>
	<div>
		<h3>프로필 사진</h3>
		<ul>
			<li>
			<c:if test="${empty sellerInfo.photo}">
				<img class="my-photo"
						src="${pageContext.request.contextPath}/images/face.png"
						width="300" height="300" class="my-photo">
				</c:if> <c:if test="${!empty sellerInfo.photo}">
					<img class="my-photo"
						src="${pageContext.request.contextPath}/upload/${sellerInfo.photo}"
						width="300" height="300" class="my-photo">
			</c:if>
			</li>
		</ul>
	</div>
	<div>
		<h3>${sellerInfo.nickname}님의상세정보</h3>
		<table>
			<tr>
				<th>별명</th>
				<td>${sellerInfo.nickname}</td>
			</tr>
			<tr>
				<th>판매동네</th>
				<td>${sellerInfo.address}</td>
			</tr>
			<tr>
				<th>매너점수</th>
				<td>
					<%--${mannerList.totalRate}--%>
				</td>
			</tr>
		</table>
	</div>
	<div class="align-center">
		<button type="button" onclick="getManner()">거래후기</button> 
		<button type="button" onclick="getSell()">판매물품</button>
	</div>
<script type="text/javascript">
	function getManner(){
		document.getElementById('sell').style.display='none';
		document.getElementById('manner').style.display='';
	}
	function getSell(){
		document.getElementById('manner').style.display='none';
		document.getElementById('sell').style.display='';
	}

</script>
	
	<c:if test="${productCount==0}">
		<div class="result-display">판매중인 상품이 없습니다.</div>
	</c:if>
	<c:if test="${productCount>0}">
		<!-- 판매중인 상품 -->
		<div id="sell">
		<ul class="list-main flex-row">
			<c:forEach var="sellerProduct" items="${sellerProduct}">
				<c:if test="${sellerInfo.amember_num == sellerProduct.amember_num}">
					<li class="flex-column"><a
						href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${sellerProduct.aproduct_num}">
							<img
							src="${pageContext.request.contextPath}/upload/${sellerProduct.photo1}">
					</a>
						<div class="title">${sellerProduct.title}</div>
							<fmt:formatNumber value="${sellerProduct.price}"/>원
						<div class="address">${sellerProduct.address}</div>
						<div class="info gray">관심 ${sellerProduct.likes} · 댓글
								${sellerProduct.replies} · 채팅 ${sellerProduct.chats}
						</div>
					</li>
				</c:if>
			</c:forEach>
		</ul>
		<div class="align-center paging">${pagingHtml}</div>
		</div>
		<!--판매중인 상품 끝 -->
		<!-- 거래후기 -->
		<div id="manner">
		<ul>
			<c:forEach var="mannerList" items="${mannerList}">
				<c:if test="${mannerList.amember_num != user_num}">
				<ul>
					<li>
					<span>
					<c:if test="${empty manner.member.photo }">
						<img src="${pageContext.request.contextPath}/images/face.png"
							width="100" height="100">
					</c:if>
					<c:if test="${!empty manner.member.photo}">
						<img src="${pageContext.request.contextPath}/upload/${sellerInfo.photo}"
							width="100" height="100">
					</c:if>
					</span>
					${mannerList.member.nickname} : ${mannerList.review}
					</li>
				</ul>
				</c:if>
			</c:forEach>
		</ul>
		<div class="align-center paging">${pagingHtml}</div>
		</div>
		<!--거래후기 끝 -->
	</c:if>
</body>
</html>





