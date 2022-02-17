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
		<ul class="chat-other flex-column justify-start">
			<li class="flex-row justify-center">
				<div class="chat-title">메시지함</div>
			</li>
			<li class="flex-row justify-center">
				<select class="search-area">
					<option value="1">전체</option>
					<option value="2">거래 중</option>
				</select>
			</li>
			<c:if test="${empty chatrooms}">
			<li>
				채팅 중인 물품이 없습니다.
			</li>
			</c:if>
			<c:if test="${!empty chatrooms}">
			<li class="list-area">
				<ul class="flex-column">

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
			<c:if test="${chatroom.seller_num!=0}">
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
				<button type="button" class="reverse-point square" onclick="location.href = '${pageContext.request.contextPath}/product/detail.do?aproduct_num=${chatroom.aproduct_num}'">물품 정보 보러가기</button>
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
			<c:if test="${chatroom.seller_num!=0}">
			<li>
				<form class="send-area flex-row justify-center">
					<input type="text" name="content" id="content">
					<i class="bi bi-send-fill" id="send"></i>
				</form>
			</li>
			</c:if>
			<c:if test="${chatroom.seller_num==0}">
			<li>
				<input type="button" value="FAQ 바로가기" onclick="${pageContext.request.contextPath}/board/memberBoardFAQ.do">
			</li>
			</c:if>
<!-- 메시지 보내기 끝 -->
		</ul>
<!-- 현재 채팅 끝 -->
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/StringUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// ajax 통신용 파라미터 변수 선언
	let achatroom_num = ${chatroom.achatroom_num};
	let aproduct_num = ${chatroom.aproduct_num};
	let opponent_num = ${user_num}==${chatroom.seller_num} ? ${chatroom.buyer_num} : ${chatroom.seller_num};

	if(opponent_num==0) { // 상대방이 관리자인 경우
		document.getElementsByClassName('read-area')[0].classList.add('no-reply');
	}
	else { // 상대방이 일반 회원인 경우
		document.getElementsByClassName('read-area')[0].classList.remove('no-reply');
	}
	
	let cp = '${pageContext.request.contextPath}';
	
	// 채팅 목록 초기 새로고침
	getListChatRoom();
	
	// 채팅 목록 불러오는 함수 정의
	function getListChatRoom() {
		$.ajax({
			url:'listChatRoom.do',
			type:'post',
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 채팅 목록을 확인할 수 있습니다!');	
				}
				else if(param.result=='success') {
					$('.list-area ul').empty();
					
					$(param.chatrooms).each(function(index, item) {
						// 채팅 상대방 확인
						let opponent = ${user_num}==item.seller_num ? item.buyerVO : item.sellerVO;
						let chat_profile = opponent.photo==null ? '/images/face.png' : '/upload/' + opponent.photo;
						
						// 목록의 채팅방이 현재 열려 있는 채팅방인지 확인
						let selected = ${chatroom.achatroom_num}==item.achatroom_num ? ' selected' : '';
						
						// 채팅방이 담긴 태그 만들기
						let stripe = index%2==0 ? '' : ' list-stripe';
						let chatroom = '<li>';
						chatroom += '	<a class="flex-row space-between' + stripe + '" href="chat.do?achatroom_num=' + item.achatroom_num + '">';
						chatroom += '		<div class="flex-row">';
						chatroom += '			<img class="list-profile" src="' + cp + chat_profile +'">'; // 채팅방 상대방 프로필
						chatroom += '			<div class="flex-column">';
						chatroom += '				<div class="list-who flex-row align-end">';
						chatroom += '					<div class="chat-subtitle"><b>' + opponent.nickname + '</b></div>';
						chatroom += '					<div class="chat-info" title="' + opponent.address + '">' + getLastToken(opponent.address, ' ') + ' · ' + '<span title="' + item.latest_date + '">' + getTimeSince(item.latest_date) + '</span></div>';
						chatroom += '				</div>';
						chatroom += '				<div class="latest-chat">' + item.latest_chat + '</div>';
						chatroom += '			</div>';
						chatroom += '			<div class="flex-row">';
						chatroom += '				<img class="list-product" src="' + cp + '/upload/' + item.productVO.photo1 + '">';
						chatroom += '				<div class="chat-selection' + selected + '">';
						chatroom += '			</div>';
						chatroom += '		</div>';
						chatroom += '	</a>';
						chatroom += '</li>';
						$('.list-area ul').append(chatroom); // <ul> 태그 안에 채팅방 추가
					}); // end of each;
				}
				else {
					alert('채팅 목록을 불러오는 데 실패했습니다!');
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		});
	}
	
	// 메시지 발송 처리 변수 선언
	let send_area = document.getElementsByClassName('send-area')[0];
	let send_btn = document.getElementById('send');
	let content = document.getElementById('content');
	
	// <input> 태그에서 엔터 입력시 메시지 발송
	send_area.addEventListener('submit', function(event) {
		event.preventDefault(); // 기본 이벤트 제거
		sendChat();
	}, false);
	// 아이콘 버튼 클릭시 메시지 발송
	send_btn.addEventListener('click', function() {
		sendChat();
	}, false);
	
	// 900자 제한
	validateBytesLength({content:900})
	
	// 메시지 보내는 함수 정의
	function sendChat() {
		if(!content.value.trim()) return; // 아무것도 입력하지 않은 경우 전송하지 않음
		
		$.ajax({
			url:'sendChat.do',
			type:'post',
			data:{
				achatroom_num:achatroom_num,
				aproduct_num:aproduct_num,
				opponent_num:opponent_num,
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
	} // end of sendChat
	
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
	
	// 메시지 초기 새로고침
	getListChat(1);
	// 메시지 1초에 한 번 새로고침
	setInterval(function() {
		if(document.visibilityState=='visible') { // 현재 창/탭이 활성화되어 있으면
			$.ajax({
				url:'countChat.do',
				type:'post',
				data:{achatroom_num:achatroom_num},
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
				achatroom_num:achatroom_num
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
					$(param.chats).each(function(index, item) {
						let lastIndex = $(param.chats).length-1;
						
						// 메시지가 담긴 태그 만들기
						let chat = '<li class="flex-column">';
						if(item.amember_num==${user_num}) { // 현재 메시지를 보낸 회원이 로그인한 사용자인 경우
							chat += '	<div class="chat-me">';
						}
						else { // 현재 메시지를 보낸 회원이 상대방인 경우
							chat += '	<div class="chat-you">';
							if(index==lastIndex || param.chats[index+1].amember_num==${user_num}) { // 상대방이 연속해서 메시지를 보낸 경우 프로필은 한 번만 표시
								chat += '		<img src="' + profile + '" class="chat-profile">';
							}
							
						}
						chat += '		<div class="flex-row align-end">'
						chat += '			<div class="chat-content">' + item.content + '</div>';
						chat += '			<div class="chat-info" title="' + item.send_date + '">' + getTimeSince(item.send_date) + '</div>';
						chat += '		</div>';
						chat += '	</div>';
						chat += '</li>';
						$('.read-area ul').prepend(chat); // <ul> 태그 안에 최신 메시지가 아래로 오도록 메시지 추가
					}); // end of each
					
					// 초기 새로고침 때는 스크롤 아래로 이동, 그 이후에는 메시지 불러오기 전 스크롤 위치 유지
					if(pageNum==1) $('.read-area').scrollTop($('.read-area').prop('scrollHeight'));
					else $('.read-area').scrollTop($('.read-area').prop('scrollHeight') - currentHeight);
					
					// 채팅 목록 새로고침
					getListChatRoom();
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