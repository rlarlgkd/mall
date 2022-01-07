<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일목록보기</title>
</head>
<body>
<h2>파일 목록 보기</h2>
<ul>
	<c:forEach items="${fileMap }" var="file">
	<li>
		파일명 : 
		<a href="download?fileName=${file.key}&orFileName=test.png">${file.key}</a>
		파일 크기 : ${file.value}
	</li>
	</c:forEach>
</ul>
<hr/>

<h2>파일 업로드</h2>
<a href="./uploadForm">파일 업로드 폼 바로 가기</a>
<a href="./main">메인으로 가기</a>
</body>
</html>