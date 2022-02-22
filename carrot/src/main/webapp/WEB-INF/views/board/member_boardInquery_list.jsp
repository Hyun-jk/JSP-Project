<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일대일 문의사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hyun.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jhmin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<body>
	<div class="page-main">
		<div class="content-body">
			<div>
				<jsp:include page="/WEB-INF/views/common/side.jsp" />
				<div class="main-content">
					<div>
						<h3>일대일문의사항</h3>
					</div>
					<div>
						<c:forEach var="board" items="${list}">
							<ul class="content">
									<li id="notice"><a href="memberBoardInqueryDetail.do?aboard_num=${board.aboard_num}">${board.title}</a>
								<hr>
								</li>
							</ul>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>	
		<div class="page-footer">
			<div class="align-center paging">
				${pagingHtml}
			</div>
			<div class="align-right paging">
				<input class="point" type="button" value="글쓰기" onclick="location.href='memberBoardInqueryWriteForm.do'">
			</div>
		</div>
	</div>	
</body>
</html>