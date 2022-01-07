<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">

	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/js/execDaumPostcode.js"></script>
	<script>
		function form_check() {
			if ($('#id').val().length == 0) {
				alert("아이디는 필수사항입니다.");
				$('#id').focus();
				return;
			}
			if ($('#id').val().length < 4) {
				alert("아이디는 4글자 이상이어야 합니다.");
				$('#id').focus();
				return;
			}
			if ($('#pw').val().length == 0) {
				alert("비밀번호는 필수사항입니다.");
				$('#pw').focus();
				return;
			}
			if ($('#pw').val() != $('#pw_check').val()) {
				alert("비밀번호가 일치하지 않습니다.");
				$('#pw').focus();
				return;
			}
			if ($('#name').val().length == 0) {
				alert("이름은 필수사항입니다.");
				$('#name').focus();
				return;
			}
			submit_form();
			alert("회원가입이 완료되었습니다.");
		}
		function submit_form() {
			$('#frm').submit();
		}

	</script>

	<title>sign up</title>
</head>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/main.css" />
<link rel="stylesheet" href="/css/cart.css" />

<body>
	<div id="signup">
		<table id="centerAlign" class="notice" width="650">
			<form action="signin" method="post" id="frm" name="frm1">
				<div class="form-group">
					<tr>
						<td><label>아이디</label>
							<!--[[[아이디 중복 확인 form 안의 form? or 버튼 처리?]]] -->
							<input type="text" name="mid" id="id">
						</td>
					</tr>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 비밀번호</label>
							<input type="password" name="mpw" id="pw">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 비밀번호 확인</label>
							<input type="password" id="pw_check">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 이름</label>
							<input type="text" name="mname" id="name">
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 사용자 유형</label>
							<select id="type" name="usertype">
								<option value="user">구매자</option>
								<option value="seller">판매자</option>
								<option value="admin">관리자</option>
							</select>
					</tr>
					</td>
				</div>
				<div class="form-group">
					<tr>
						<td><label> 배송지 주소</label>
							<input type="text" name="postcode" id="postcode" placeholder="우편번호">
							<input type="button" class="btn btn-empty" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
							<input type="text" name="address" id="address" placeholder="주소"><br>
							<input type="text" name="detailAddress" id="detailAddress" placeholder="상세주소">
							<input type="text." name="extraAddress" id="extraAddress" placeholder="참고항목">
					</tr>
					</td>
				</div>

				<tr>
					<td><button type="button" class="btn btn-empty" onclick="form_check();">가입 완료</button>
				</tr>
				</td>
			</form>
		</table>
	</div>

</body>

</html>