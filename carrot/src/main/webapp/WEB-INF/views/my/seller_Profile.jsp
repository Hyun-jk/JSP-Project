<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자 프로필</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.carousel.css">
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dain.css">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main"><br><br><br><br>
   <ul class="detail-write align-center ul">
    <h2>${sellerInfo.nickname}님의 상세 정보</h2>
			<li class="align-center">
			<div style= "display: inline-block">
			<c:if test="${empty sellerInfo.photo}">
			    <img class="my-photo" src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
			</c:if> 
			<c:if test="${!empty sellerInfo.photo}">
				<img class="my-photo" src="${pageContext.request.contextPath}/upload/${sellerInfo.photo}" width="200" height="200" class="my-photo">
			</c:if> 
			</div>
			</li>
			<li>
		<table>
		<div style= "display: inline-block">
			<tr>
			    <th>닉네임 : </th>
				<td>${sellerInfo.nickname}</td>
			</tr>
			<tr>
				<th>판매동네 : </th>
				<td>${sellerInfo.address}</td>
			</tr>
			<tr>
				<th>매너점수 : </th>
				<td><fmt:formatNumber value="${mannerList.get(0).totalRate}" pattern=".00"/></td>
			</tr>
			</div>
		</table></li>
	</ul>
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
	<div class="detail-write align-center">
	<div class= "manner-icons">
	<div style= "display: inline-block">
		<i class="bi bi-person-lines-fill"></i> <input class="buttonE" type="button" onclick="getManner()" value="거래후기"></div>
	<div style= "display: inline-block"> 
		<i class="bi bi-cart4"></i> <input class="buttonE" type="button" onclick="getSell()" value="판매물품"></div>
	</div>
	<c:if test="${productCount==0}">
		<div class="result-display">판매중인 상품이 없습니다.</div>
	</c:if>
	<c:if test="${productCount>0}">
		<!-- 판매중인 상품 -->
		<table>
		<div id="sell">
		<ul class="list-main flex-row">
			<c:forEach var="sellerProduct" items="${sellerProduct}">
				<c:if test="${sellerInfo.amember_num == sellerProduct.amember_num}">
					<li class="flex-column"><a
						href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${sellerProduct.aproduct_num}">
							<img src="${pageContext.request.contextPath}/upload/${sellerProduct.photo1}"
							width="200" height="200"></a>
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
		</div></table>
		<!--판매중인 상품 끝 -->
		<!-- 거래후기 -->
		<table>
		<div id="manner">
		<ul>
			<c:forEach var="mannerList" items="${mannerList}">
				<c:if test="${mannerList.amember_num != user_num}">
				<ul>
					<li class="li">
					<span>
					<c:if test="${empty manner.member.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png"
							width="150" height="150">
					</c:if>
					<c:if test="${!empty manner.member.photo}">
						<img src="${pageContext.request.contextPath}/upload/${sellerInfo.photo}"
							width="150" height="150">
					</c:if>
					</span>
					<div class="inner">
					${mannerList.member.nickname} : ${mannerList.review}
					</div>
					</li>	
				</ul>
				</c:if>
			</c:forEach>
		</ul>
		<div class="align-center paging">${pagingHtml}</div>
		</div>
		</table>
		</div>
		
		<!--거래후기 끝 -->
	</c:if>
</body>
</html>





