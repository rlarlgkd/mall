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


	<title>review Form</title>
	<!-- include libraries(jQuery, bootstrap) -->
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

	<script>
		function form_check() {


			if ($('#review').val().length == 0) {
				alert("내용 입력은 필수사항입니다.");
				$('#review').focus();
				return;
			}

			submit_form();

		}
		function submit_form() {
			$('#frm').submit();
		}
	</script>

</head>
<link rel="stylesheet" href="/css/main.css" />
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/notice.css" />
<link rel="stylesheet" href="/css/cart.css" />
<link rel="stylesheet" href="/css/reset.css" />

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

	<div class="notice" id="cartView">
		<table class="notice" width="650">
			<form action="review" method="post" id="frm">
				<th>
				<td>상품명: ${pname } 구매자 : <%= sId %>
						<input type="hidden" name="pname" value="${pname}">
						<input type="hidden" name="pnum" value="${pnum}">
						<input type="hidden" name="mid" value=<%=sId %>></td>
				</th>

				<tr>
					<td colspan="2">내용<br />
						<textarea id="review" name="review" rows="10"></textarea>
					</td>
					<script>
						$('#review').summernote({
							tabsize: 2,
							height: 100
						});
					</script>
				</tr>

				<tr>
					<td colspan="2">
						<button type="button" onclick="form_check();" class="btn btn-empty">입력</button>
						<a href="contentView?pnum=${pnum}">목록보기</a>
					</td>
				</tr>
			</form>
		</table>
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