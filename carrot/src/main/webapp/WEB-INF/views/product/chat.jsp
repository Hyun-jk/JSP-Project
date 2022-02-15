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
	<div>
		<ul class="chat-main flex-column">
			<li>
				<ul class="flex-column" id="read_area">
				
				</ul>
			</li>
			<li class="flex-row" id="send_area">
				<input type="text" name="content" id="content">
				<i class="bi bi-send-fill" id="send"></i>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	let send_btn = document.getElementById('send');
	let content = document.getElementById('content');
	send_btn.addEventListener('click', function() {
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
					alert('메시지 전송에 성공하였습니다!')
					// 새 데이터 읽어오기
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
	
	let currentPage;
	let count;
	let rowCount;
	
	getListChat(1);
	
	function getListChat(pageNum) {
		currentPage = pageNum;
		
		$.ajax({
			url:'listChat.do',
			type:'post',
			data:{
				pageNum:pageNum,
				aproduct_num:${param.aproduct_num}
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
	
					if(pageNum==1) { // 처음 호출시는 해당 ID의 div 내부 내용물을 제거
						$('#read_area').empty();
					}
					
					$(param.list).each(function(index, item) {
						let output = '<li class="flex-column">';
						if(item.amember_num==${user_num}) {
							output += '	<div class="chat-me align-right">';
						}
						else {
							output += '	<div class="chat-you align-left">';
						}
							output += '		<div class="chat-nickname">' + item.memberVO.nickname + '</div>';
						output += '		<div class="chat-content">' + item.content + '</div>';
						output += '	</div>';
						output += '</li>';
						// 문서 객체에 추가
						$('#read_area').append(output);
					}); // end of each
/*
					// 다음 댓글 보기 버튼 처리
					if(currentPage>=Math.ceil(count/rowCount)) {
						// 다음 페이지가 없음
						$('.paging-button').hide();
					}
					else {
						// 다음 페이지가 존재
						$('.paging-button').show();
					}*/
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