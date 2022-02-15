<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<div class="flex-row justify-center">
<!-- 상품별 채팅방 목록 시작 -->
		<ul class="chat-other flex-column">
			<li>
				<h3>메시지함</h3>
			</li>
			<li>
				채팅 목록
			</li>
		</ul>
		<hr class="vertical">
<!-- 상품별 채팅방 목록 끝 -->
<!-- 대화 중 시작 -->
		<ul class="chat-main flex-column">
<!-- 헤더 : 상대방 정보 시작 -->
			<li class="chat-header" id="who_area">
			</li>
			<li><hr></li>
<!-- 헤더 : 상대방 정보 끝 -->
<!-- 헤더 : 상품 정보 시작 -->
			<li class="chat-header flex-row space-between" id="header_area">
			</li>
			<li><hr></li>
<!-- 헤더 : 상품 정보 끝 -->
<!-- 주고 받은 메시지 불러오기 시작 -->		
			<li>
				<ul class="flex-column" id="read_area">
				
				</ul>
			</li>
<!-- 주고 받은 메시지 불러오기 끝 -->
			<li><hr></li>
<!-- 메시지 보내기 시작 -->
			<li id="send_area">
				<form class="flex-row justify-center">
					<input type="text" name="content" id="content">
					<i class="bi bi-send-fill" id="send"></i>
				</form>
			</li>
<!-- 메시지 보내기 끝 -->
		</ul>
<!-- 대화 중 끝 -->
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	// 채팅 메시지 보내기
	let form = document.getElementsByTagName('form')[0];
	let send_btn = document.getElementById('send');
	let content = document.getElementById('content');
	form.addEventListener('submit', function(event) {
		event.preventDefault(); // 기본 이벤트 제거
		
		if(!content.value.trim()) return;
		
		$.ajax({
			url:'sendChat.do',
			type:'post',
			data:{
				aproduct_num:${param.aproduct_num},
				opponent_num:${param.opponent_num},
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
		form.submit();
	}, false); // end of addEventListener
	
	let currentPage;
	let count;
	let rowCount;
	// 처음 새로고침
	getListChat(1);
	// 1초에 한 번 새로고침
	// setInterval(function() {
		// getListChat(1);
	// }, 1000);
	// 메시지 불러오는 함수 정의
	function getListChat(pageNum) {
		currentPage = pageNum;
		
		$.ajax({
			url:'listChat.do',
			type:'post',
			data:{
				pageNum:pageNum,
				aproduct_num:${param.aproduct_num},
				opponent_num:${param.opponent_num}
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 채팅을 읽을 수 있습니다!');	
				}
				else if(param.result=='success') {
					count = param.count;
					rowCount = param.rowCount;
	
					if(pageNum==1) { // 처음 호출시 해당 ID의 div 내부 내용물을 제거
						$('#who_area').empty();
						$('#header_area').empty();
						$('#read_area').empty();
					}
					
					// 채팅 중인 상대방 정보 불러오기
					let profile = '${pageContext.request.contextPath}';
					let photo = param.header.opponentVO.photo;
					if(photo==null) profile += '/images/face.png'; // 프로필 사진을 업로드하지 않은 경우 기본 이미지 경로 사용
					else profile += '/upload/' + photo;
					let who = '<div class="chat-nickname">' + param.header.opponentVO.nickname + '</div>';
					who += '<div>' + param.header.opponentVO.rate + '</div>';
					$('#who_area').append(who);
					
					// 채팅 중인 상품 정보 불러오기
					let header = '<img src="${pageContext.request.contextPath}/upload/' + param.header.productVO.photo1 + '">';
					header += '<div class="flex-column">';
					header += '	<div>' + param.header.productVO.title + '</div>';
					header += '	<div>' + param.header.productVO.price + '원</div>';
					header += '</div>';
					if(param.header.productVO.status==1) { // 판매 종료된 물품
						header += '<button type="button" class="point square" disabled>삭제된 물품</button>';
					}
					else if(param.header.productVO.seller_num==${user_num}) { // 물품 판매자가 로그인한 회원인 경우
						header += '<button type="button" class="point square" id="complete_btn">거래 완료하기</button>';
					}
					else if(param.header.productVO.buyer_num==${user_num}){ // 물품 구매자가 로그인한 회원인 경우
						header += '<button type="button" class="point square" id="manner_btn">거래 후기 남기기</button>';
					}
					else if(param.header.productVO.complete==1) { // 로그인한 회원이 판매자도 구매자도 아니고 거래가 완료된 상품인 경우
						header += '<button type="button" class="point square" disabled>거래 완료된 물품</button>';
					}
					else {
						header += '<button type="button" class="point square" id="product_btn">물품 정보 보러가기</button>';
					}
					$('#header_area').append(header);
					
					// 주고 받은 메시지 불러오기
					$.fn.reverse = [].reverse; // 최신 메시지가 아래에 오게 하기 위한 함수 정의
					$(param.list).reverse().each(function(index, item) {
						let chat = '<li class="flex-column">';
						if(item.amember_num==${user_num}) {
							chat += '	<div class="chat-me align-right">';
						}
						else {
							chat += '	<div class="chat-you align-left">';
							chat += '		<img src="' + profile + '" class="chat-profile">';
						}
						chat += '		<div class="chat-content">' + item.content + '</div>';
						chat += '	</div>';
						chat += '</li>';
						$('#read_area').append(chat);
					}); // end of each
					
					// 스크롤 아래로 이동
					$('#read_area').scrollTop($('#read_area').prop('scrollHeight'));
					
					// 스크롤 위로 올리면 이전 메시지 불러오기
					
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