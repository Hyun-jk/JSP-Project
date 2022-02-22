<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자 프로필</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/jhmin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dain.css">
<style type="text/css">
	ul.detail-write > li {
		margin: 25px 0;
	}
	ul.seller-who {
		margin: 0 auto;
		width: 400px;
	}
	ul.seller-who li {
		margin: 10px 0;
		padding: 0 10px;
	}
	ul.seller-who .manner-stars {
		margin-top: 0;
	}
	.my-photo { /* CSS 누락 */
		border-radius: 50%;
	}
	#manner ul {
		margin: 0 auto;
		width: 720px;
	}
	#manner ul li div {
		padding: 10px;
	}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main"><br><br><br><br>
   <ul class="detail-write align-center ul">
   	<li class="subtitle">${sellerInfo.nickname}님의 상세 정보</li>
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
				<ul class="flex-column seller-who">
					<li class="flex-row space-between">
					    <div>닉네임 : </div>
						<div>${sellerInfo.nickname}</div>
					</li>
					<li class="flex-row space-between">
						<div>판매동네 : </div>
						<div>${sellerInfo.address}</div>
					</li>
					<li class="flex-row space-between">
						<div>매너 평점 : </div>
						<div class="manner-rate"><span class="bold"><fmt:formatNumber value="${mannerList.get(0).totalRate}" pattern=".00"/></span></div>
					</li>
					<li class="flex-row justify-center">
						<div class="manner-stars">
							<i class="bi bi-star"></i>
							<i class="bi bi-star"></i>
							<i class="bi bi-star"></i>
							<i class="bi bi-star"></i>
							<i class="bi bi-star"></i>
						</div>
					</li>
				</ul>
		</li>
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
	<ul>
		<li class="detail-write align-center">
			<div class= "manner-icons">
				<div style= "display: inline-block">
					<i class="bi bi-person-lines-fill"></i> <input class="buttonE" type="button" onclick="getManner()" value="거래후기">
				</div>
				<div style= "display: inline-block"> 
					<i class="bi bi-cart4"></i> <input class="buttonE" type="button" onclick="getSell()" value="판매물품">
				</div>
			</div>
		</li>
		<li id="sell">
			<!-- 판매중인 상품 -->
			<c:if test="${productCount==0}">
			<div class="result-display">판매중인 상품이 없습니다.</div>
			</c:if>
			<c:if test="${productCount>0}">
				<ul class="list-other flex-row">
					<c:forEach var="sellerProduct" items="${sellerProduct}">
						<c:if test="${sellerInfo.amember_num == sellerProduct.amember_num}">
							<li class="flex-column"><a
								href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${sellerProduct.aproduct_num}">
									<img src="${pageContext.request.contextPath}/upload/${sellerProduct.photo1}"
									width="200" height="200">
								<div class="product-title">${sellerProduct.title}</div>
									<fmt:formatNumber value="${sellerProduct.price}"/>원
								<div class="address">${sellerProduct.address}</div>
								<div class="info gray">관심 ${sellerProduct.likes} · 댓글
										${sellerProduct.replies} · 채팅 ${sellerProduct.chats}
								</div>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
				<div class="align-center paging">${pagingHtml}</div>
			</c:if>
			<!--판매중인 상품 끝 -->
		</li>
		<li id="manner">
			<!-- 거래후기 -->
				<ul class="flex-column">
				<c:forEach var="mannerList" items="${mannerList}">
					<c:if test="${mannerList.amember_num != user_num}">
						<li class="flex-row">
							<div>
								<c:if test="${empty manner.member.photo}">
								<img class="my-photo" src="${pageContext.request.contextPath}/images/face.png"
									width="150" height="150">
								</c:if>
								<c:if test="${!empty manner.member.photo}">
								<img class="my-photo" src="${pageContext.request.contextPath}/upload/${sellerInfo.photo}"
									width="150" height="150">
								</c:if>
							</div>
							<div class="flex-column">
								<div>
								${mannerList.member.nickname} :
								</div>
								<div>
								 ${mannerList.review}
								</div>
							</div>
						</li>
					</c:if>
				</c:forEach>
				</ul>
				<div class="align-center paging">${pagingHtml}</div>
			<!--거래후기 끝 -->
		</li>
	</ul>
</div>
<script type="text/javascript">
	getManner(); // 처음에 거래 후기만 보이도록 처리

	// 매너 평점 처리
	let stars = document.querySelectorAll('.manner-stars i.bi');
	let seller_rate = '${mannerList.get(0).totalRate}';
	if(!seller_rate) {
		for(let i=0;i<stars.length;i++) {
			if(i<2) stars[i].classList.replace('bi-star', 'bi-star-fill');
			if(i==2) stars[i].classList.replace('bi-star', 'bi-star-half');
			stars[i].classList.add('disabled');
		}
		document.querySelector('.manner-rate').textContent = '표시할 매너 평점이 없어요';
	}
	else {
		for(let i=0;i<stars.length;i++) {
			if(i<Math.floor(seller_rate)) stars[i].classList.replace('bi-star', 'bi-star-fill');
			if(i+1==Math.floor(seller_rate) && seller_rate-Math.floor(seller_rate)>=0.5) stars[i+1].classList.replace('bi-star', 'bi-star-half')
		}
	}
</script>
</body>
</html>





