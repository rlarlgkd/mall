<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	Object obj=session.getAttribute("mid");
	String sId=(String)obj;
	Object error=session.getAttribute("error_message");
	String msg=(String)error;
%>
<!DOCTYPE html>
<html>

<head>

	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>

<body>

	<c:if test="${param.error != null}">
		<p>
			<script>
				alert("${error_message}");
			</script>
		</p>
	</c:if>
	alert("hello world");
	<script>
		window.location.href = "guest/main";
	</script>

</body>

</html>