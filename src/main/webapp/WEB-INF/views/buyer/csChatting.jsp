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
	<link rel="stylesheet" href="/css/main.css" />
	<link rel="stylesheet" href="/css/common.css" />
	<link rel="stylesheet" href="/css/cart.css" />
	<title>csChatting</title>
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

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

			<!-- navbar 버튼 -->
			<a class="cart-icon" href="cart?mid=<%=sId %>">장바구니</a>&nbsp;

		</div>

	</header>
	<br /><br /><br /><br />
	<div id="cartView" class="font">
		<div class="notice">고객센터</div>
		<table class="notice">
			<tr>
				<td style="text-align: center">1:1 채팅 문의</td>
				<td style="text-align: center">카카오톡 채팅 문의</td>
			</tr>
			<tr>
				<td>
					<div class="cs"><a class="font" href="index"></a></div>
				</td>
				<td style="text-align: center">
					<div id="chatting">
						<!-- PC 카카오톡 상담 버튼 -->
						<a href="javascript:void kakaoChatStart()">
							<img src="/img/consult_large_yellow_pc.png">
						</a>
						<!-- 카카오톡 채널 스크립트 -->
						<script type='text/javascript'>
							Kakao.init('ccf541d968c76fc3c6f734c6dabb2c01'); // 사용할 앱의 JavaScript키를 입력해 주세요.
							function kakaoChatStart() {
								Kakao.Channel.chat({
									channelPublicId: '_CfGCb' // 카카오톡 채널 홈 URL에 명시된 ID를 입력합니다.
								});
							}
						</script>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">※카카오톡 계정이 없으실 경우 1:1 체팅 문의를 이용해주시길 바랍니다.</td>
			</tr>
		</table>
	</div>
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