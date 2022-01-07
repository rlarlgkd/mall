<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FileUpload/uploadAction</title>
</head>
<body>
<br/><p>
<a href="./fileList">파일 리스트 바로가기</a></p>
<hr/>

<h2>파일 업로드 결과 보기</h2>
<c:forEach begin="0" end="${returnObj.files.size()-1}" var="i">
	<ul>
		<li><img src="./img/${returnObj.files[i].saveFileName}" width="300"/></li>
	</ul>
</c:forEach>
</body>
</html>