<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0;
}

div {
	width: 300px;
	height: 300px;
	margin: 0 auto; /*水平居中*/
	position: relative;
	top: 50%; /*偏移*/
	margin-top: -150px;
}
</style>
</head>
<body>
	<div>
		<h4>当前分享为私有，请输入提取码</h4>
		<form action="${pageContext.request.contextPath }/privateShare.action"
			method="post">
			<input type="hidden" name="shareUrl" value="${shareUrl}"> <input
				type="text" name="command"> <input type="submit" value="提交">
		</form>
	</div>
</body>
</html>