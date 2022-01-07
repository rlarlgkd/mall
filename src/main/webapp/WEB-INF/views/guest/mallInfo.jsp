<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	Object obj=session.getAttribute("mid");
	String sId=(String)obj;
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/main.css" />
	<link rel="stylesheet" href="/css/common.css" />
	<link rel="stylesheet" href="/css/mallInfo.css" />


	<!-- 지도 API -->
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	b6f4241ba81313914cc262faf4165c4e"></script>
</head>


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

<body>
	<br /><br /><br />
	<div class="outer">
		<div class="inner">
			<h1>찾아오시는 길</h1>

			서울특별시 서대문구 신촌로 119 3층 쇼핑몰 사무실<br />
			[2호선] 신촌역 4번 출구에서 전방 80m<br />
			Tel : 02 123 4567 <br />
			Fax : 02 123 4568 <br />

		</div>

		<div id="map" style="width:600px;height:300px;">
			<script>
				//지도 구현
				var container = document.getElementById('map');
				var options = {
					center: new kakao.maps.LatLng(37.5561426145781, 126.93907821785625),
					level: 3
				};
				var map = new kakao.maps.Map(container, options);
				//마커 표시
				var markerPosition = new kakao.maps.LatLng(37.5561426145781, 126.93907821785625);
				var marker = new kakao.maps.Marker({
					position: markerPosition
				});
				marker.setMap(map);
			</script>
		</div>
	</div>

	<br>
	<div class="sidebar">
		<a class="sideContent border-btm" href="notice">공지사항</a> &nbsp;
		<a class="sideContent border-btm" href="csChatting">고객센터</a>&nbsp;
		<a class="sideContent border-btm" href="mallInfo">찾아오는 길</a>
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