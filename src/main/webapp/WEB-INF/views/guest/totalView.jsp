<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Object obj=session.getAttribute("mid");
	String sId=(String)obj;
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>total view</title>
	<link rel="stylesheet" href="/css/main.css" />
	<link rel="stylesheet" href="/css/common.css" />
	<link rel="stylesheet" href="/css/totalView.css" />
</head>

<body>

	<header class="navbar clearfix">
		<div class="float--left">
			<div class="logo"><a href="main">로고</a></div>
			<c:set var="name" value="<%=sId %>" />
			<c:choose>
				<c:when test="${name eq null }">
					<button class="btn--nav" onclick="location.href='signup';">회원가입</button>
					<button class="btn--nav" onclick="location.href='login';">로그인</button>
					<!-- <label>Please log in</label> -->
				</c:when>
				<c:otherwise>


					<button class="btn--nav" onclick="location.href='logout';">로그아웃</button>
					<button class="btn--nav" onclick="location.href='myPage?mid=<%=sId%>';">마이페이지</button>
					<button class="btn--nav" onclick="location.href='sellerForm';">판매자페이지</button>
					<label style="margin-left:15px;">
						<%=sId %>님 환영합니다!
					</label>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="float--right">
			<!-- 전체 상품 검색 -->
			<form class="search_field" action="totalView" method="post" autocomplete="off">
				<input class="search" type="text" name="pname" placeholder="상품검색">
				<button type="submit">검색</button>
			</form>


		</div>
	</header>
	<br /><br /><br />

	<div id="contentwrap">
		<c:set var="result" value="${result}" />
		<c:choose>
			<c:when test="${result > 0}">
				<div class="bestlist font">
					<c:forEach items="${list}" var="dto">
						<div class="product">
							<img id="img" src="../img/${dto.pname}.jpg"
								onclick="location.href='contentView?pnum=${dto.pnum}';" style="cursor:pointer"> <a
								class="font" href="contentView?pnum=${dto.pnum}">${dto.pname}</a>
							<div class="font"> ${dto.price }원</div>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<div class="nosearch"></div>
			</c:otherwise>
		</c:choose>
	</div>

</body>
<footer>
	<hr />
	<!-- 회사 정보 -->
	<div id="comInfo" rows=10 cols=100 wrap=off>
		Company : Shopping Mall I Company Registration No : 000-00-0000 <br>
		Mail-Order License : 0000-Seoul-0000 I Address : 서울 서대문구 신촌로 119 3층 쇼핑몰 사무실 <br>
		Tel : 02 123 4567 I Fax : 02 123 4568 <br>

		Term of Use Privacy

		Hosting by I'MWEB
	</div>
</footer>

</html>