<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FileUpload/uploadForm</title>
</head>
<body>
<h2>이미지 파일 업로드 폼</h2>
<form name="fileForm" method="post" action="uploadAction"
		enctype="multipart/form-data">
	제목 : <input type="text" name="title" />
	첨부파일1 : <input type="file" name="userfile1" /><br/>
	첨부파일2 : <input type="file" name="userfile2" /><br/>	
	<button type="submit" class"btn btn-danger">파일 업로드</button>	
</form>
<br/><p></p>
</body>
</html>