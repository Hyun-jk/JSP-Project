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
				<img class="profile" src="${pageContext.request.contextPath}/images/face.png">
				</c:if>
				<c:if test="${!empty seller.photo}">
				<img class="profile" src="${pageContext.request.contextPath}/upload/${seller.photo}">
				</c:if>	
				<div class="flex-cloumn">	
					<div>${seller.nickname}</div>
					<div>${seller.address}</div>
				</div>
			</div>
			<div class="manner">
				<div>
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
			<div class="gray"><a id="toggle_comments">댓글 <span id="current_replies">${product.replies}</span></a> · 채팅 ${product.chats} · 관심 <span id="current_likes">${product.likes}</span></div>
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
					<input type="button" class="big point" value="채팅으로 거래하기" id="link_chatroom" <c:if test="${empty user_num}">disabled title="로그인 후 채팅으로 거래할 수 있습니다"</c:if>>
					</c:otherwise>
				</c:choose>
			</div>
		</li>
		<li><hr></li>
<!-- 버튼들 끝 -->
<!-- 댓글 시작 -->
		<li class="comment-list hide">
			<ul>
			
			</ul>
		</li>
		<li class="comment-list hide"><hr></li>
		<li class="comment-write hide flex-row justify-center align-start">
			<textarea name="content"></textarea>
			<input type="button" class="point" value="댓글 작성" id="write_comment" <c:if test="${empty user_num}">disabled title="로그인 후 댓글을 작성할 수 있습니다"</c:if>>
		</li>
		<li class="comment-write hide"><hr></li>
<!-- 댓글 끝 -->
<!-- 실시간 중고 더보기 시작 -->
		<li>
			<div class="title">실시간 중고 더보기</div>
			<ul class="list-other flex-row space-between">
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validateInput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/StringUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	let cp = '${pageContext.request.contextPath}';
	
	// 댓글 토글
	let comment_lists = document.getElementsByClassName('comment-list');
	let comment_writes = document.getElementsByClassName('comment-write');
	document.getElementById('toggle_comments').addEventListener('click', function() {
		for(let i=0;i<comment_writes.length;i++) comment_writes[i].classList.toggle('hide'); // 댓글 작성 영역 토글
		for(let i=0;i<comment_lists.length;i++) comment_lists[i].classList.toggle('hide'); // 댓글 목록 영역 토글
		getListComment(1); // 댓글 목록 새로고침
	}, false); // end of addEventListener
	
	// 댓글 길이 제한
	validateBytesLengthByName({content:900});
	
	// 댓글 작성
	let content = document.querySelector('.comment-write textarea[name=content]');
	document.getElementById('write_comment').addEventListener('click', function() {
		if(!content.value.trim()) return;
		writeComment(content, undefined); // null을 인자로 전달시 모델 클래스에서는 빈 문자열로 간주됨
	}, false); // end of addEventListener
	
	// 대댓글 작성 UI 토글
	let lastTarget;
	document.addEventListener('click', function(event) { // 동적 이벤트 바인딩
		if(event.target && event.target.classList.contains('comment-toggle-reply')) {
			let reply = document.querySelector('#reply_area');
			if(reply!=null) clearReplyArea(reply); // 해당 아이디의 요소가 이미 있으면 내부 초기화
			else { // 없으면 <ul> 요소를 생성하고 아이디 부여
				reply = document.createElement('ul');
				reply.id = 'reply_area';	
			}
			reply.dataset.parent = event.target.dataset.parent;
			reply.appendChild(comment_writes[0].cloneNode(true));
			reply.querySelector('.comment-write').classList.replace('comment-write', 'reply-write');
			reply.querySelector('#write_comment').id = 'write_reply';
			event.target.parentNode.insertBefore(reply, event.target.nextSibling); // 토글 버튼 바로 다음에 <ul> 요소 삽입
			
			// 토글 처리
			if(lastTarget!=null && lastTarget==event.target) reply.classList.toggle('hide');
			else reply.classList.remove('hide');
			lastTarget = event.target;
		}
	}, false); // end of addEventListener
	
	// 대댓글 작성 UI 초기화
	function clearReplyArea(reply) {
		while(reply.firstChild) { // 내부를 초기화
			reply.removeChild(reply.lastChild);
		}
	}
	
	// 대댓글 작성
	document.addEventListener('click', function(event) { // 동적 이벤트 바인딩
		if(event.target && event.target.id=='write_reply') {
			let content = document.querySelector('.reply-write textarea[name=content]');
			let reply = event.target.parentNode.parentNode;
			writeComment(content, reply.dataset.parent);
			reply.classList.add('hide'); // 대댓글 작성 UI 숨기기
			clearReplyArea(reply); // 대댓글 작성 UI 내부 초기화
		}
	}, false); // end of addEventListener
	
	// 댓글/대댓글 작성하는 함수 정의
	function writeComment(content, acomment_parent) {
		$.ajax({
			url:'writeComment.do',
			type:'post',
			data:{
				aproduct_num:${param.aproduct_num},
				content:content.value,
				acomment_parent:acomment_parent
				},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 댓글을 작성할 수 있습니다!');
				}
				else if(param.result=='success') {
					content.value = ''; // 입력 칸 초기화
					current_replies.textContent = Number(current_replies.textContent) + 1; // 댓글 수 증가
					if(acomment_parent==null) getListComment(1) // 댓글 목록 새로고침
					else getListReply(acomment_parent); // 대댓글 목록 새로고침
				}
				else {
					alert('댓글 작성에 실패했습니다!');
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	} // end of writeComment
	
	// 페이지 처리 변수 선언
	let currentPage;
	let count;
	let rowCount;
	
	// 댓글 목록 불러오는 함수 정의
	function getListComment(pageNum) {
		currentPage = pageNum;
		
		$.ajax({
			url:'listComment.do',
			type:'post',
			data:{
				pageNum:pageNum,
				aproduct_num:${param.aproduct_num}
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='success') {
					if(param.comments.length==0) { // 댓글이 없으면 <ul> 태그를 숨김
						for(let i=0;i<comment_lists.length;i++) comment_lists[i].classList.add('hide');
						return;
					}
					
					count = param.count;
					rowCount = param.rowCount;
	
					if(pageNum==1) { // 처음 호출시 <ul> 태그 안의 내용 초기화
						$('.comment-list ul').empty();
					}

					// 댓글 목록 불러오기
					$(param.comments).each(function(index, item) {
						// 프로필 사진 처리
						let comment_profile = item.memberVO.photo==null ? '/images/face.png' : '/upload/' + item.memberVO.photo;
						
						// 판매자 확인
						let seller_tag = ${product.amember_num}==item.amember_num ? '<span class="seller-tag">판매자</span>' : '';

						// 댓글이 담긴 태그 만들기
						let comment = '<li class="flex-row align-start" data-comment="' + item.acomment_num + '">';
						// 프로필에 매너 평가 확인 페이지로 이동하는 링크 추가
						comment += '	<img class="profile" src="' + cp + comment_profile + '">';
						comment += '	<div class="comment-text flex-column">';
						comment += '		<div class="comment-subtitle">' + item.memberVO.nickname + seller_tag + '</div>';
						comment += '		<div class="comment-info"><span title="' + item.memberVO.address + '">' + getLastToken(item.memberVO.address, ' ') + '</span> · <span title="' + item.reg_date + '">' + getTimeSince(item.reg_date) + '</span></div>';
						comment += '		<div>' + item.content + '</div>'
						comment += '		<a class="comment-toggle-reply" data-parent="' + item.acomment_num + '">답글 쓰기</a>'
						comment += '		<ul class="reply-list flex-column hide">';
						comment += '		</ul>';
						comment += '	</div>'
						comment += '</li>';
						$('.comment-list > ul').prepend(comment); // <ul> 태그 안에 최신 댓글이 아래로 오도록 대댓글 추가
						
						// 현재 댓글의 대댓글 목록 새로고침
						getListReply(item.acomment_num);
					}); // end of each
					
					// 더보기 버튼
					if(currentPage<Math.ceil(count/rowCount)) { // 다음 페이지가 있으면
						
					}
				}
				else {
					alert('댓글을 불러오는 데 실패했습니다!');
				}				
			}, // end of success
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	} // end of getListComment
	
	// 대댓글 목록 불러오는 함수 정의
	function getListReply(acomment_parent) {		
		$.ajax({
			url:'listReply.do',
			type:'post',
			data:{
				acomment_parent:acomment_parent,
				aproduct_num:${param.aproduct_num}
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='success') {
					let parent = 'li[data-comment=' + acomment_parent + '] ul.reply-list';
					// 대댓글이 없으면 <ul> 태그를 숨김
					if(param.replies.length==0) {
						$(parent).addClass('hide');
						return;
					}
					
					$(parent).empty(); // 호출시 <ul> 태그 내부를 초기화
					
					// 댓글 목록 불러오기
					$(param.replies).each(function(index, item) {
						// 프로필 사진 처리
						let comment_profile = item.memberVO.photo==null ? '/images/face.png' : '/upload/' + item.memberVO.photo;
						
						// 판매자 확인
						let seller_tag = ${product.amember_num}==item.amember_num ? '<span class="seller-tag">판매자</span>' : '';

						// 대댓글이 담긴 태그 만들기
						let reply = '<li class="flex-row align-start" data-comment="' + item.acomment_num + '">';
						// 프로필에 매너 평가 확인 페이지로 이동하는 링크 추가
						reply += '	<img class="profile" src="' + cp + comment_profile + '">';
						reply += '	<div class="comment-text flex-column">';
						reply += '		<div class="comment-subtitle">' + item.memberVO.nickname + seller_tag + '</div>';
						reply += '		<div class="comment-info"><span title="' + item.memberVO.address + '">' + getLastToken(item.memberVO.address, ' ') + '</span> · <span title="' + item.reg_date + '">' + getTimeSince(item.reg_date) + '</span></div>';
						reply += '		<div>' + item.content + '</div>'
						reply += '		<a class="comment-toggle-reply" data-parent="' + acomment_parent + '">답글 쓰기</a>'; // 부모 댓글 번호를 유지
						reply += '	</div>'
						reply += '</li>';
						$(parent).prepend(reply); // <ul> 태그 안에 최신 대댓글이 아래로 오도록 대댓글 추가
					}); // end of each
					
					$(parent).removeClass('hide'); // <ul> 태그를 노출시킴
				}
				else {
					alert('댓글을 불러오는 데 실패했습니다!');
				} // end of success			
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	} // end of getListComment

	// 관심 상품 토글
	let like_btn = document.getElementById('like');
	let current_likes = document.getElementById('current_likes');	
	like_btn.addEventListener('click', function() {
		$.ajax({
			url:'toggleMyProduct.do',
			type:'post',
			data:{aproduct_num:${product.aproduct_num}},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 관심 상품에 담을 수 있습니다!');
				}
				else if(param.result=='insert') { // 아이콘을 채워진 하트로 교체하고 관심 수 증가
					like_btn.classList.remove('bi-heart');
					like_btn.classList.add('bi-heart-fill');
					current_likes.textContent = Number(current_likes.textContent) + 1;
				}
				else if(param.result=='delete') { // 아이콘을 빈 하트로 교체하고 관심 수 차감
					like_btn.classList.remove('bi-heart-fill');
					like_btn.classList.add('bi-heart');
					current_likes.textContent = Number(current_likes.textContent) -1;
				}
				else {
					alert('관심 상품 추가/삭제에 실패했습니다!')
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		}); // end of ajax
	}, false); // end of addEventListener
	
	// 채팅방 연결
	let link_chatroom = document.getElementById('link_chatroom');
	if(link_chatroom!=null) {
	link_chatroom.addEventListener('click', function() {
		$.ajax({
			url:cp + '/chat/linkChatRoom.do',
			type:'post',
			data:{
				aproduct_num:${product.aproduct_num},
				seller_num:${product.amember_num}
			},
			dataType:'json',
			timeout:10000,
			success:function(param) {
				if(param.result=='logout') {
					alert('로그인 후 채팅할 수 있습니다!');
				}
				else if(param.result=='success') {
					location.href = cp + '/chat/chat.do?achatroom_num=' + param.achatroom_num;
				}
				else {
					alert('채팅방을 불러오는 데 실패했습니다!');
				}
			},
			error:function() {
				alert('네트워크 오류가 발생했습니다!');
			}
		})
	}, false); // end of addEventListener
	} // end of if
</script>
</body>
</html>