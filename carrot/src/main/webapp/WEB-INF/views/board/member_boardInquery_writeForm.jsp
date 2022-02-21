<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원일대일문의</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
<script type="text/javascript">
$(function(){
	$('#write_form').submit(function(){
		if($('#title').val().trim()==''){
			alert('제목을 입력하세요');
			$('#title').val('').focus();
			return false;
		}
		if($('#content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#content').val('').focus();
			return false;
		}
	});
});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<body>	
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp" />		
			<div class="main-content">
				<div>
					<h2>공지사항 글쓰기</h2>
				</div>
				<form id="write_form" action="memberBoardInqueryWrite.do">
				<label for="keyfield">카테고리</label>
				<select name="keyfield" id="keyfield">
					<option value="1">운영정책</option>
					<option value="2">구매/판매</option>
					<option value="3">거래매너</option>
					<option value="4">이용제재</option>
				</select>
				<ul>
					<li><label for="title">제목</label> <input type="text"
						name="title" id="title" maxlength="50" style="width:500px;height:45px;"></li>

					<li><label for="content">내용</label> <textarea rows="23"
							cols="70" name="content" id="content"></textarea></li>
				</ul>
				<div class="align-right">
					<input class="point" type="submit" value="등록"> 
					<input class="point" type="button" value="목록" onclick="location.href='memberBoardInquery.do'">
				</div>
			</form>
			</div>
		</div>
	</div>
	</div>	
</body>