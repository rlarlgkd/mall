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
	<script src="http://code.jquery.com/jquery.js"></script>
	<script>
		function form_check() {
			var str = 1;
			if ($('#pname').val().length == 0) {
				alert("상품이름 입력은 필수사항입니다.");
				$('#pname').focus();
				return;
			}

			if ($('#price').val().length == 0) {
				alert("가격 입력은 필수사항입니다.");
				$('#price').focus();
				return;
			}

			if (isNaN($('#price').val())) {
				alert("가격은 숫자를 입력해주세요.");
				$('#price').focus();
				return;
			}

			if ($('#pcnt').val().length == 0) {
				alert("수량 입력은 필수사항입니다.");
				$('#pcnt').focus();
				return;
			}

			if (isNaN($('#pcnt').val())) {
				alert("수량은 숫자를 입력해주세요.");
				$('#pcnt').focus();
				return;
			}



			if ($('#file').val().length == 0) {
				alert("사진 등록은 필수사항입니다.");
				$('#file').focus();
				return;
			}
			submit_form();
			alert("상품 등록이 완료되었습니다.");

		}
		function submit_form() {
			$('#frm').submit();
		}

	</script>

	<title>sellerForm</title>

	<link rel="stylesheet" href="/css/common.css" />
	<link rel="stylesheet" href="/css/reset.css" />
	<link rel="stylesheet" href="/css/sellerForm.css" />
	<link rel="stylesheet" href="/css/main.css" />
	<link rel="stylesheet" href="/css/notice.css" />

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

	<div id="sellerForm">
		<table class="notice" width="650" cellpadding="0" cellspacing="0">
			<form name="fileForm" method="post" action="sellerWrite" id="frm" target="iframe1"
				enctype="multipart/form-data">
				<tr>
					<td>아이디 ${sellerForm.pnum} </td>
					<td>
						<%=sId %>
					</td>

				</tr>
				<input type="hidden" name="pnum" value="${sellerForm.pnum}">
				<tr>
					<td>상품이름</td>
					<td><input type="text" name="pname" id="pname" size="50"></td>
				</tr>
				<tr>
					<td>가격</td>
					<td><input type="text" name="price" id="price" size="50"></td>
				</tr>
				<tr>
					<td>수량</td>
					<td><input type="text" name="pcnt" id="pcnt" size="50"></td>
				</tr>
				<input type="hidden" value=<%=sId %> name="mid" >
				<tr>
					<td>첨부파일(사진)</td>
					<td>

						<input type="file" id="file" name="userfile" /><br>
					</td>

				</tr>
				<tr>
					<td colspan="2">
						<button type="button" onclick="form_check()" class="btn btn-empty btnBrown">입력</button>
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