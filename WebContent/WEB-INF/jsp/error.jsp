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

#msg {
	width: 800px;
	height: 500px;
	margin: 0 auto; /*水平居中*/
	position: relative;
	top: 50%; /*偏移*/
	margin-top: -150px;
	text-align: center;
}

#ret {
	width: 200px;
	margin: 0px auto; /*水平居中*/
}
</style>
</head>
<body>
	<div id="ret">
		<h3>
			<span id="miao">3</span>秒后返回上一页
		</h3>
	</div>
	<div id="msg">${err}</div>
</body>
<script>
	window.onload = function() {
		setTimeout("history.back()", 3000);
	}
	var i = 3;
	change();
	function change() {
		setTimeout("change()",1000);
		document.getElementById("miao").innerText = i--;
	}
</script>
</html>