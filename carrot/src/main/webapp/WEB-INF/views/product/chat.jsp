<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 : </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="chat flex-row justify-center">
<!-- 물품별 채팅방 목록 시작 -->
		<ul class="chat-other flex-column">
			<li>
				검색 UI?
			</li>
			<c:if test="${empty listChat}">
			<li>
				채팅 중인 물품이 없습니다.
			</li>
			</c:if>
			<c:if test="${!empty listChat}">
			<li class="list-area">
				<ul class="flex-column">
					<c:forEach var="chat" items="${listChat}">
					<li class="flex-row">
						<a href="chat.do?aproduct_num=${chat.aproduct_num}&opponent_num=${chat.opponent_num}">
						<c:if test="${empty chat.opponentVO.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png" class="list-profile">
						</c:if>
						<c:if test="${!empty chat.opponentVO.photo}">
						<img src="${pageContext.request.contextPath}/upload/${chat.opponentVO.photo}" class="list-profile">
						</c:if>
						<div class="flex-column">
							<div class="flex-row">
								<div>${chat.opponentVO.nickname}</div>
								<div>${chat.opponentVO.address}</div>
								<div>${chat.send_date}</div>
							</div>
							<div class="latest-content">${chat.content}</div>
						</div>
						<img src="${pageContext.request.contextPath}/upload/${chat.productVO.photo1}" class="list-product">
						<c:if test="${chat.aproduct_num!=param.aproduct_num}">
						<div class="chat-selection"></div>
						</c:if>
						<c:if test="${chat.aproduct_num==param.aproduct_num}">
						<div class="chat-selection selected"></div>
						</c:if>
						</a>
					</li>
					</c:forEach>
				</ul>
			</li>
			</c:if>
		</ul>
		<hr class="vertical">
<!-- 물품별 채팅방 목록 끝 -->
<!-- 현재 채팅 시작 -->
		<ul class="chat-main flex-column">
<!-- 현재 채팅 헤더 시작 -->
			<li class="chat-header who-area">
				<div class="chat-title">${opponent.nickname}</div>
				<div class="chat-subtitle">매너 평점 <b>${opponent.rate}</b></div>
			</li>
			<li><hr></li>
			<c:if test="${param.opponent_num!=0}">
			<li class="chat-header product-area flex-row space-between">
				<div class="flex-row align-start">
					<img src="${pageContext.request.contextPath}/upload/${product.photo1}">
					<div class="flex-column">
						<div class="chat-title">${product.title}</div>
						<div class="chat-subtitle"><b><fmt:formatNumber value="${product.price}"/></b>원</div>
					</div>
				</div>
				<c:choose>
				<c:when test="${product.status==1}">
				<button type="button" class="point square" disabled>삭제된 물품</button>
				</c:when>
				<c:when test="${product.amember_num==user_num && product.complete!=1}">
				<button type="button" class="point square" onclick="">거래 완료하기</button>
				</c:when>
				<c:when test="${product.buyer_num==user_num}">
				<button type="button" class="point square" onclick="">거래 후기 남기기</button>
				</c:when>
				<c:when test="${product.complete==1}">
				<button type="button" class="point square" disabled>거래 완료된 물품</button>
				</c:when>
				<c:otherwise>
				<button type="button" class="reverse-point square" onclick="location.href = 'detail.do?aproduct_num=${param.aproduct_num}'">물품 정보 보러가기</button>
				</c:otherwise>
				</c:choose>
			</li>
			<li><hr></li>
			</c:if>
<!-- 현재 채팅 헤더 끝 -->
<!-- 주고 받은 메시지 불러오기 시작 -->	
			<li class="read-area">
				<ul class="flex-column">
				
				</ul>
			</li>
			<li><hr></li>
<!-- 주고 받은 메시지 불러오기 끝 -->
<!-- 메시지 보내기 시작 -->
			<c:if test="${param.opponent_num!=0}">
			<li>
				<form class="send-area flex-row justify-center">
					<input type="text" name="content" id="content">
					<i class="bi bi-send-fill" id="send"></i>
				</form>
			</li>
			</c:if>
			<c:if test="${param.opponent_num==0}">
			<li>
				<input type="button" value="FAQ 바로가기" onclick="">
			</li>
			</c:if>
<!-- 메시지 보내기 끝 -->
		</ul>
<!-- 현재 채팅 끝 -->
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	if(${opponent_num}==null) { // 상대방이 관리자인 경우
		document.getElementsByClassName('read-area')[0].classList.add('no-reply');
	}
	else { // 상대방이 일반 회원인 경우
		document.getElementsByClassName('read-area')[0].classList.remove('no-reply');
	}

	// 채팅 메시지 보내기
	let send_area = document.getElementsByClassName('send-area')[0];
	let send_btn = document.getElementById('send');
	let content = document.getElementById('content');
	send_area.addEventListener('submit', function(event) {
		event.preventDefault(); // 기본 이벤트 제거
		
		if(!content.value.trim()) return; // 아무것도 입력하지 않은 경우 전송하지 않음
		
		$.ajax({
			url:'sendChat.do',
			type:'post',
			data:{
				aproduct_num:${aproduct_num},
				opponent_num:${opponent_num},
				content:content.value
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 채팅을 보낼 수 있습니다!')
				}
				else if(param.result=='success') {
					content.value = ''; // 입력 칸 초기화
					getListChat(1); // 새로고침
				}
				else {
					alert('메시지 전송에 실패하였습니다!')
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!')
			}
		}); // end of ajax
	}, false); // end of addEventListener
	send_btn.addEventListener('click', function() {
		send_area.submit();
	}, false); // end of addEventListener
	
	// 채팅 상대방 프로필 사진 가져오기
	let profile = '${pageContext.request.contextPath}';
	let photo = '${opponent.photo}';
	if(!photo) profile += '/images/face.png'; // 프로필 사진을 업로드하지 않은 경우 기본 이미지 경로 사용
	else profile += '/upload/' + photo;
	
	// 페이지 처리 변수 선언
	let currentPage;
	let currentHeight;
	let count;
	let rowCount;
	
	// 초기 새로고침
	getListChat(1);
	// 1초에 한 번 새로고침
	setInterval(function() {
		if(document.visibilityState=='visible') { // 현재 창/탭이 활성화되어 있으면
			$.ajax({
				url:'countChat.do',
				type:'post',
				data:{aproduct_num:${aproduct_num}},
				dataType:'json',
				timeout:10000,
				success:function(param) {
					if(param.unread>0) { // 안 읽은 메시지가 있으면
						getListChat(1); // 새로고침
					}
				}
			}); // end of ajax
		}
	}, 1000);
	
	// 스크롤 끝에 도달하면 추가로 채팅 내역 불러오기
	document.querySelector('.read-area').addEventListener('scroll', function() {
		if(currentPage<Math.ceil(count/rowCount) && this.scrollTop==0) { // 다음 페이지가 있고 스크롤 끝에 도달하면
			getListChat(currentPage+1);
		}
	}, false);
	
	// 메시지 불러오는 함수 정의
	function getListChat(pageNum) {
		currentPage = pageNum;
		
		$.ajax({
			url:'listChat.do',
			type:'post',
			data:{
				pageNum:pageNum,
				aproduct_num:${aproduct_num}
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 채팅을 읽을 수 있습니다!');	
				}
				else if(param.result=='success') {
					currentHeight = $('.read-area').prop('scrollHeight');
					count = param.count;
					rowCount = param.rowCount;
	
					if(pageNum==1) { // 처음 호출시 <ul> 태그 안의 내용 초기화
						$('.read-area ul').empty();
					}

					// 주고 받은 메시지 불러오기
					$(param.list).each(function(index, item) {
						let lastIndex = $(param.list).length-1;
						
						// 메시지가 담긴 태그 만들기
						let chat = '<li class="flex-column">';
						if(item.amember_num==${user_num}) { // 현재 메시지를 보낸 회원이 로그인한 사용자인 경우
							chat += '	<div class="chat-me">';
						}
						else { // 현재 메시지를 보낸 회원이 상대방인 경우
							chat += '	<div class="chat-you">';
							if(index==lastIndex || param.list[index+1].amember_num==${user_num}) { // 상대방이 연속해서 메시지를 보낸 경우 프로필은 한 번만 표시
								chat += '		<img src="' + profile + '" class="chat-profile">';
							}
							
						}
						chat += '		<div class="flex-row align-end">'
						chat += '			<div class="chat-content">' + item.content + '</div>';
						chat += '			<div class="chat-time">' + item.send_date + '</div>';
						chat += '		</div>';
						chat += '	</div>';
						chat += '</li>';
						$('.read-area ul').prepend(chat); // <ul> 태그 안에 최신 메시지가 아래로 오도록 메시지 추가
					}); // end of each
					
					// 초기 새로고침 때는 스크롤 아래로 이동, 그 이후에는 메시지 불러오기 전 스크롤 위치 유지
					if(pageNum==1) $('.read-area').scrollTop($('.read-area').prop('scrollHeight'));
					else $('.read-area').scrollTop($('.read-area').prop('scrollHeight') - currentHeight);
				}
				else {
					alert('채팅을 불러오는 데 실패했습니다!');
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	} // end of getListChat
</script>
</body>
</html>