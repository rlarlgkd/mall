<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">

	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/execDaumPostcode.js"></script>
	<script>

		function submit_form() {
			$('#myPage').submit();

		}x
		function withdrawal() {

			let pw = prompt("탈퇴하려면 비밀번호를 입력하세요");
			$('#withdraw').submit();
		}


		function Buttontoggle() {
			var x = document.getElementById("onoff");
			if (x.style.display === "none") {
				console.log("1");
				x.style.display = "block";
				console.log("2");
			}
			else {
				console.log("3");
				x.style.display = "none";
			}
		}

	</script>

	<title>myPage</title>
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
		integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
</head>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/main.css" />
<link rel="stylesheet" href="/css/signup.css" />
<body>

	<div id="centerAlign">
		<table class="notice" width="650">
			<form action="myPageChange" method="post" id="myPage">
				<div class="form-group">
					<tr>
						<td><label>아이디 : ${member.mid }</label>
					</tr>
					</td>
					<input type="hidden" name="mid" value="${member.mid }">
				</div>
				<div class="form-group">
					<tr>
						<td><label> 이름 : ${member.mname }</label>
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 비밀번호 변경 | 현재: </label>
							<input type="password" name="mpw" id="mpw">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 새 비밀번호 </label>
							<input type="password" name="newpw" id="newpw">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 비밀번호 확인 </label>
							<input type="password" id="pw_check">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 회사 주소</label>
							<input type="text" name="postcode" id="postcode" placeholder="우편번호"
								value="${member.postcode }">
							<input type="button" class="btn btn-empty" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
							<input type="text" name="address" id="address" placeholder="주소"
								value="${member.address }"><br>
							<input type="text" name="detailAddress" id="detailAddress" placeholder="상세주소"
								value="${member.detailAddress }">
							<input type="text." name="extraAddress" id="extraAddress" placeholder="참고항목"
								value="${member.extraAddress }">
					</tr>
					</td>
				</div>

				<tr>
					<td><button type="button" class="btn btn-empty btnBrown" onclick="submit_form()">변경 내용 저장</button>

						<button type="button" class="btn btn-empty" id="withdrawBtn" onclick="Buttontoggle()" value="NO"
							style="margin-left:450px">탈퇴</button>
					</td>
					<!-- <button type="button" class="btn btn-empty" onclick="withdrawal();">회원탈퇴</button> -->
					</td>
				</tr>
			</form>



			<tr id="onoff" style="display:none; width:650;">
				<td style="width:650px; text-align: right; padding-bottom:0px; padding-top:10px">
					<form action="withdraw" method="post" id="withdraw">
						<!-- [[[button 형식 onclick-> href=withdraw?mid]]]  -->
						<input type="hidden" name="mid" value="${member.mid }">
						<input type="text" class="btn btn-empty" name="mpw" placeholder="현재 비밀번호 입력">
						<!-- <button type="button" class="btn btn-empty" onclick="withdrawal();">회원탈퇴</button> -->
						<input type="submit" class="btn btn-empty" value="회원탈퇴">
					</form>
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

</html>