<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 정보 : ${product.title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<ul class="detail flex-column">
		<li>
			<img src="${pageContext.request.contextPath}/upload/${product.photo1}">
		</li>
<!-- 판매자 프로필, 매너 평가 시작 -->
		<li class="seller flex-row space-between">
			<div class="who flex-row">
				<c:if test="${empty seller.photo}">
				<img src="${pageContext.request.contextPath}/images/face.png">
				</c:if>
				<c:if test="${!empty seller.photo}">
				<img src="${pageContext.request.contextPath}/upload/${seller.photo}">
				</c:if>	
				<div class="flex-cloumn">	
					<div>${seller.nickname}</div>
					<div>${seller.address}</div>
				</div>
			</div>
			<div class="manner">
				<div class>
					<c:if test="${empty seller.rate}">
					정보가 없습니다.
					</c:if>
					<c:if test="${!empty seller.rate}">
					${seller.rate}
					</c:if>
				</div>
				<div>매너 평점</div>
			</div>
		</li>
		<li><hr></li>
<!-- 판매자 프로필, 매너 평가 끝 -->
<!-- 물품 판매글 시작 -->
		<li class="product flex-column">
			<div class="title">${product.title}</div>
			<div class="gray"><a>${category.name}</a> · ${product.reg_date}</div>
			<div class="subtitle"><fmt:formatNumber value="${product.price }"/>원</div>
			<div class="content">${product.content}</div>
			<div class="gray"><a href="#" id="toggle_replies">댓글 ${product.replies}</a> · 채팅 ${product.chats} · 관심 <span id="current_likes">${product.likes}</span></div>
		</li>
		<li><hr></li>
<!-- 물품 판매글 끝 -->
<!-- 버튼들 시작 -->
		<li class="flex-row space-between">
			<c:if test="${exist}">
			<i class="bi bi-heart-fill" id="like"></i>
			</c:if>
			<c:if test="${!exist}">
			<i class="bi bi-heart" id="like"></i>
			</c:if>
			<div class="other">
				<input type="button" class="big" value="이전으로" onclick="history.go(-1);">
				<c:choose>
					<c:when test="${user_num==product.amember_num}">
					<input type="button" class="big point" value="상품 수정하기" onclick="location.href = 'modifyForm.do?aproduct_num=${product.aproduct_num}';">
					</c:when>
					<c:when test="${user_num==product.buyer_num}">
					<input type="button" class="big point" value="거래 후기 남기기" onclick="">
					</c:when>
					<c:otherwise>
					<input type="button" class="big point" value="채팅으로 거래하기" onclick="location.href = 'chat.do?aproduct_num=${product.aproduct_num}';" <c:if test="${empty user_num}">disabled title="로그인하세요"</c:if>>
					</c:otherwise>
				</c:choose>
			</div>
		</li>
		<li><hr></li>
<!-- 버튼들 끝 -->
<!-- 댓글 시작 -->
		<li id="comment_wrap" style="display: none;">
		</li>
<!-- 댓글 끝 -->
<!-- 실시간 중고 더보기 시작 -->
		<li>
			<div class="title">실시간 중고 더보기</div>
			<ul class="list-other">
				<c:forEach var="other" items="${listProduct}">
				<li>
					<a href="${pageContext.request.contextPath}/product/detail.do?aproduct_num=${other.aproduct_num}">
					<img src="${pageContext.request.contextPath}/upload/${other.photo1}">
					<div class="title">${other.title}</div>
					<div class="price">
						<c:if test="${other.price==0}">
						나눔
						</c:if>
						<c:if test="${other.price>0}">
						<fmt:formatNumber value="${other.price}"/>원
						</c:if>
					</div>
					<div class="address">${other.address}</div>
					<div class="info gray">
						관심 ${other.likes} · 댓글 ${other.replies} · 채팅 ${other.chats}
					</div>
					</a>
				</li>
				</c:forEach>
			</ul>
		</li>
<!-- 실시간 중고 더보기 끝 -->
	</ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// 관심 상품 토글
	like_btn = document.getElementById('like');
	current_likes = document.getElementById('current_likes');	
	like_btn.addEventListener('click', function() {
		$.ajax({
			url:'toggleMyProduct.do',
			type:'post',
			data:{aproduct_num:${product.aproduct_num}},
			dataType:'json',
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 관심 상품에 담을 수 있습니다!');
				}
				else if(param.result=='insert') {
					alert('관심 상품에 담겼습니다!');
					like_btn.classList.remove('bi-heart');
					like_btn.classList.add('bi-heart-fill');
					current_likes.textContent = Number(current_likes.textContent) + 1;
				}
				else if(param.result=='delete') {
					alert('관심 상품에서 제외되었습니다!');
					like_btn.classList.remove('bi-heart-fill');
					like_btn.classList.add('bi-heart');
					current_likes.textContent = Number(current_likes.textContent) -1;
				}
			},
			error:function() {
				alert('네트워크 오류 발생!');
			}
		}); // end of ajax
	}, false);
</script>
</body>
</html>