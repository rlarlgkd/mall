<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.study.springboot.dto.MemberDto" %>
<%
	Object obj=session.getAttribute("mid");
	String sId=(String)obj;
	
	MemberDto mInfo = (MemberDto)session.getAttribute("memberInfo");
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<!-- jQuery -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<!-- iamport.payment.js -->
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="/js/requestPay.js"></script>
	<link rel="stylesheet" href="/css/main.css" />
	<link rel="stylesheet" href="/css/common.css" />
	<link rel="stylesheet" href="/css/cart.css" />

	<title>cart</title>

	<script>
		function addSale() {
			$('#saleAdd').submit();		
		}	
	</script>
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

			<a class="cart-icon" href="cart?mid=<%=sId %>">장바구니</a>&nbsp;
		</div>
	</header>

	<br />
	<hr />
	<br />

	<!-- 장바구니 화면 -->
	<div id="cartView" class="font">
		<table class="notice" width="800" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2" style="text-align: center">상품</td>
				<td>수량</td>
				<td colspan="2">가격</td>
			</tr>

			<c:set var="sum" value="0" />
			<c:forEach items="${cart}" var="dto" varStatus="stat">
				<input type="hidden" value="${dto.PNUM}">
				<input type="hidden" value="${dto.MID}">
				<tr>
					<td><img id="img" src="../img/${dto.PNAME}.jpg"></td>
					<td>${dto.PNAME}</td>
					<td>${dto.CCNT}</td>
					<td>${dto.PRICE}</td>
					<td>
						<button class="btn btn-cancel float-right"
							onclick="location.href='cartDelete?pnum=${dto.PNUM}&mid=${dto.MID}';">X</button>
					</td>
				</tr>
				<c:set var="sum" value="${sum + dto.CCNT*dto.PRICE }" />
				<c:set var="products" value="${stat.first ? '' : products} ${dto.PNAME }" />
			</c:forEach>

			<tr>
				<td rowspan="2" colspan="4" style="text-align: center">
					<br />★배송비는 무료 입니다★<br /><br />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
					<br />총 금액 :${sum}
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<input type="hidden" id="sum" value="${sum }">
					<input type="hidden" id="products" value="${products }">
					<c:set var="infoCheck" value="<%=mInfo %>" />
					<c:choose>
						<c:when test="${infoCheck eq null }">
							<input type="hidden" id="mname" value="visitor">
						</c:when>
						<c:otherwise>
							<input type="hidden" id="mname" value="<%=mInfo.getMname()%>">
						</c:otherwise>
					</c:choose>

					<input type="hidden" id="ccnt" value=1>
					<form action="addSale" method="post" id="saleAdd">
						<c:forEach items="${cart}" var="add">
							<input type="hidden" value="<%=sId %>" name="saleMid">
							<input type="hidden" value="${add.PNUM}" name="salePnum">
						</c:forEach>
					</form>
						<button class="btn btn-pay float-right" onclick="requestPay()">결제하기</button>
					
				</td>
			</tr>
		</table>
		<a href="main">계속 쇼핑</a>
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