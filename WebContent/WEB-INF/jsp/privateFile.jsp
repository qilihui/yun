<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>当前分享为私有，请输入密码
<form action="${pageContext.request.contextPath }/privateShare.action" method="post">
	<input type="hidden" name="shareUrl" value="${shareUrl}">
	<input type="text" name="command">
	<input type="submit" value="提交">
</form>
</body>
</html>