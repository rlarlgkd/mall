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
	<title>notice</title>
	<!-- Required meta tags -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
		integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>

</head>
<link rel="stylesheet" href="/css/main.css" />
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/notice.css" />
<link rel="stylesheet" href="/css/reset.css" />

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

	<div class="noticeContainer">
		<div class="notice">공지사항</div>

		<table class="notice" width="800">
			<tr>
				<td>글번호</td>
				<td>글제목</td>
				<td>작성일</td>
			</tr>
			<c:forEach items="${notice}" var="dto">
				<tr>
					<td>${dto.nonum}</td>
					<td>
						<a href="noticeView?nonum=${dto.nonum}">${dto.notitle}</a>
					</td>
					<td>${dto.nodate}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3">
					<!-- 페이징 -->
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center">
							<li class="page-item">
								<!-- 처음 -->
								<c:choose>
									<c:when test="${(page.curPage -1) <1}">
										<a class="page-link" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
											<span class="sr-only">[&lt; &lt;]</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="notice?page=1" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
											<span class="sr-only">[&lt; &lt;]</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>

							<!-- 이전 -->
							<li class="page-item">
								<c:choose>
									<c:when test="${(page.curPage -1) <1}">
										<a class="page-link" aria-label="Previous">
											<span aria-hidden="true">&lsaquo;</span>
											<span class="sr-only">[&lt;]</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="notice?page=${page.curPage -1}"
											aria-label="Previous">
											<span aria-hidden="true">&lsaquo;</span>
											<span class="sr-only">[&lt;]</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>

							<!-- 개별페이지 -->

							<c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
								<c:choose>
									<c:when test="${page.curPage == fEach}">
										<li class="page-item"><a class="page-link"> ${fEach} </a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="notice?page=${fEach}"> ${fEach}
											</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<!-- 다음 -->
							<li class="page-item">
								<c:choose>
									<c:when test="${(page.curPage +1) > page.totalPage }">
										<a class="page-link" aria-label="Next">
											<span aria-hidden="true">&rsaquo;</span>
											<span class="sr-only">[&gt;]</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="notice?page=${ page.curPage +1 }" aria-label="Next">
											<span aria-hidden="true">&rsaquo;</span>
											<span class="sr-only">[&gt;]</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>

							<!-- 끝 -->
							<li class="page-item">
								<c:choose>
									<c:when test="${page.curPage == page.totalPage }">
										<a class="page-link" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
											<span class="sr-only">[&gt;&gt;]</span>
										</a>
									</c:when>
									<c:otherwise>
										<a class="page-link" href="notice?page=${ page.totalPage }" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
											<span class="sr-only">[&gt;&gt;]</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>
						</ul>
					</nav>
				</td>
			</tr>
		</table>
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