<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
	Object obj=session.getAttribute("mid"); 
	String sId=(String)obj; 
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
		<!-- Required meta tags -->
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
			integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<!-- Optional JavaScript -->
		<!-- jQuery first, then Popper.js, then Bootstrap JS -->
		<script src="https://code.jquery.com/jquery-3.3.1.slim.js"
			integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
			crossorigin="anonymous"></script>
		<script src="/js/requestPay.js"></script>

		<script>
			function saleCheck() {
				var userList = new Array();
				<c:forEach items="${users}" var="dto">
					userList.push("${dto}");
				</c:forEach>
				for (var i = 0; i < userList.length; i++) {
					if (userList[i] == "<%=sId%>") {
						return writeReview();
						break;
					}
				}
				alert("이 상품을 구매한 후 리뷰를 작성해주세요");
			}

			function writeReview() {
				location.href = 'reviewForm?pname=${content.pname }&pnum=${content.pnum}';
			}

			function modalOpen() {
				$('#example').modal({ backdrop: 'static', keyboard: false });
			}
			function save() {

				$('#example').modal('hide');
				if ("<%=sId %>" == "null") {
					alert("로그인이 필요합니다.");
				} else {
					location.href = 'cartAdd?mid=<%=sId %>&pnum=${content.pnum}&ccnt=' + $('#ccnt').val();
				}
			}
			function move() {

				if ("<%=sId %>" == "null") {
					alert("로그인이 필요합니다.");
				} else {
					location.href = 'cartAddMove?mid=<%=sId %>&pnum=${content.pnum}&ccnt=' + $('#ccnt').val();
				}

			}

			function reviewCheck(i) {
				var contentMid = $('#contentMid').val();
				var userList = new Array();
				<c:forEach items="${review}" var="dto">
					userList.push("${dto.mid}");
				</c:forEach>
				console.log(userList[i]);
				if (userList[i] == "<%=sId%>" || contentMid == "<%=sId%>") {
					location.href = 'replyForm?reid=' + $('#reId').val();
				}
				else {
					alert("권한이 없습니다.");
				}
			}
		</script>

		<title>contentView</title>

		<link rel="stylesheet" href="/css/reset.css" />
		<link rel="stylesheet" href="/css/common.css" />
		<link rel="stylesheet" href="/css/main.css" />

		<link rel="stylesheet" href="/css/contentView.css" />


</head>




<body>
	<header class="navbar">
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
	<br />
	<hr />
	<br />
	<div class=contentStyle>
		<!-- 상품 상세 창 -->
		<div id="contentView" class="font">
			<table width="800" cellpadding="0" cellspacing="0">
				<tr>
					<td rowspan="4">
						<img id="img" src="../img/${content.pname}.jpg">
					</td>
					<input type="hidden" value="${content.mid }" id="contentMid">
					<td colspan="2">${content.pname}</td>
				</tr>
				<tr>
					<td colspan="2">
						${content.price}&nbsp;&nbsp;원
					</td>
				</tr>
				<tr>
					<td colspan="2">
						수량
						<select name="ccnt" id="ccnt">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
					</td>
				</tr>

				<tr>
					<td>
						<button type="button" class="btn btn-empty" data-toggle="modal" data-target="#example"
							onclick="modalOpen();">
							장바구니
						</button>

						<!-- Modal -->
						<div class="modal fade" id="example" tabindex="-1" role="dialog"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										장바구니에 상품을 담으시겠습니까?
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal"
											onclick="save();">계속 쇼핑</button>
										<button type="button" class="btn btn-primary" data-dismiss="modal"
											onclick="move();">장바구니 이동</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td>
						<input type="hidden" id="sum" value="${content.price}">
						<input type="hidden" id="products" value="${content.pname}">
						<input type="hidden" id="mname" value="visitor">
						
						<form action="addSale" method="post" id="saleAdd">
							<input type="hidden" value="visitor" name="saleMid">
							<input type="hidden" value="${content.pnum}" name="salePnum">
						</form>
						<button class="btn btn-pay" onclick="requestPay()">결제하기</button><br />
					</td>
				</tr>
			</table>
		</div>
		<br />
		<br />
		<!-- 상품평 -->
		<div class="review">
			<table class="notice" width="800" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2">
						<h2>상품평</h2>
					</td>
				</tr>

				<c:forEach items="${review}" var="dto" varStatus="loop">
					<tr>
						<td>
							<c:forEach begin="1" end="${dto.reindent}">ㄴ</c:forEach>
							<input type="hidden" value="${dto.reid}" id="reId">
							<c:set var="string1" value="${dto.mid}" />
							<c:set var="length" value="${fn:length(string1)}" />
							<c:set var="string2" value="${fn:substring(string1, 0, length-0)}" />
							${string2}*** :

							${dto.review}
						</td>
						<td><button class="btn reply btn-empty" onclick="reviewCheck(${loop.index})">댓글</button></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="2"><button class="btn btn-empty" onclick="saleCheck()">리뷰 작성하기</button></td>
				</tr>
			</table>

		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
</body>
<hr />
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