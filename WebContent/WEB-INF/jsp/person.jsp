<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>青软云盘</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${pageContext.request.contextPath }/css/lightbox.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/layer.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/index.css" rel="stylesheet">
<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/lightbox.js"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath }/js/layer.js"></script>
<script src="${pageContext.request.contextPath }/js/ckplayer/ckplayer.js"></script>
<script src="http://static.bcedocument.com/reader/v2/doc_reader_v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/index.js"></script>
</head>
<body>
	<div class="content">
		<div class="top">
			<nav class="navbar navbar-default navbar-fixed-top"
				style="background-color: #EFF4F8; margin-bottom: 0px; height: 10%; z-index: 50">
			<div class="container">
				<div class="navbar-header" style="float:left;">
				<span style="float: left;">
				</span> <a class="navbar-brand" href="index.action"
				style="margin-left: 100px;">个人中心</a>
				</div>
				<div id="navbar" style="float: right;">
				<ul class="nav navbar-nav">
					<li class="dropdown" style="width:100px">
					<a href="#" class="dropdown-toggle" id="user" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					<img src="${pageContext.request.contextPath }/img/titalpicture.jpg" height="20px" class="img-circle"/>
					${username	 } <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="user/logout.action">退出登录</a></li>
					</ul></li>
					<li><a>|</a></li>
					<li><a href="#" class="glyphicon glyphicon-bell" title="系统通知"></a></li>
					<li><a href="#" class="glyphicon glyphicon-list-alt"
					title="意见反馈"></a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="bottom" onclick="">
			<div class="left">
				<div
				style="width: 100%;height: 100%;background-color:#EFF4F8">
				<div style="padding: 10px 0px 0px 0px;margin: 0px;">
					<ul class="ul"
						style="list-style-type: none;font-size: 14px;list-style-position: outside;font-family: 'Microsoft YaHei',arial,SimSun,'宋体';padding-left: 0px">

						<li style="width: 100%; height: 38px;padding: 0px;margin: 0px;">
							<a href="index.action"
							style="display: inline-block;width: 179px;height: 38px;padding: 0px 0px 0px 15px;position: relative; vertical-align:middle; line-height: 38px;text-decoration: none;">
								<span
							style="width: 38px;height: 38px;display: inline-block;text-align: center;">
							<span class="glyphicon glyphicon-file"></span>
						</span> <span
						style="width:auto ;height: 38px;display: inline-block;text-align: left;vertical-align: middle;">我的网盘</span>
				</a>
				</li>
				<li style="width: 100%; height: 38px;padding: 0px;margin: 0px;">
					<a onclick="return searchFileType('docum')"
					style="display: inline-block;width: 179px;height: 38px;padding: 0px 0px 0px 15px;position: relative;vertical-align:middle; line-height: 38px;text-decoration: none;">
						<span
						style="width: 38px;height: 38px;display: inline-block;text-align: center;">
							<span class="glyphicon glyphicon-file"
							style="visibility: hidden;"></span>
					</span> <span style="display: inline-block;text-align: left;">绑定邮箱</span>
				</a>
				</li>

				<li style="width: 100%; height: 38px;padding: 0px;margin: 0px;">
					<a onclick="return searchFileType('file')"
					style="display: inline-block;width: 179px;height: 38px;padding: 0px 0px 0px 15px;position: relative;vertical-align:middle; line-height: 38px;text-decoration: none;">
						<span
						style="width: 38px;height: 38px;display: inline-block;text-align: center;">
							<span class="glyphicon glyphicon-file"
							style="visibility: hidden;"></span>
					</span> <span style="display: inline-block;text-align: left;">身份验证</span>
				</a>
				</li>

				<li style="width: 100%; height: 38px;padding: 0px;margin: 0px;">
					<a  onclick="return openMyShare()"
					style="display: inline-block;width: 179px;height: 38px;padding: 0px 0px 0px 15px; position: relative;vertical-align:middle; line-height: 38px;text-decoration: none;">
						<span
						style="width: 38px;height: 38px;display: inline-block;text-align: center;">
							<span class="glyphicon glyphicon-file"
							style="visibility: hidden;"></span>
					</span> <span style="display: inline-block;text-align: left;">关联账号</span>
				</a>
				</li>

				<li style="width: 100%; height: 38px;padding: 0px;margin: 0px;">
					<a style="display: inline-block;width: 179px;height: 38px;padding: 0px 0px 0px 15px;position: relative;vertical-align:middle; line-height: 38px;text-decoration: none;"
					href="user/deleteUser.action">
						<span
						style="width: 38px;height: 38px;display: inline-block;text-align: center;">
							<span class="glyphicon glyphicon-trash"></span>
					</span> <span style="display: inline-block;text-align: left;">用户注销</span>
				</a>
				</li>

			</ul>
		</div>
		<div style="padding: 0px 0px 0px 15px;margin: 100px 0px 0px 0px;">
			<div class="progress" style="margin-bottom: 5px; margin-right: 15px">
			  <div id="sizeprogress" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="10" aria-valuemax="100" style="min-width: 2em;">
			    60%
			  </div>
			</div>
			<span id="countSize">${countSize }</span>/<span id="totalSize">${totalSize }</span>
			<div
				style="margin-top: 10px;word-break:keep-all;white-space:nowrap;overflow:hidden;">
				<span style="display: inline-block;width: 24%;"><a
					style=";text-decoration: none;"><span
						class="glyphicon glyphicon-asterisk"></span></a></span> <span
					style="display: inline-block;width: 24%;"><a
					style=";text-decoration: none;"><span
						class="glyphicon glyphicon-plus"></span></a></span> <span
					style="display: inline-block;width: 24%;"><a
					style=";text-decoration: none;"><span
						class="glyphicon glyphicon-minus"></span></a></span> <span
					style="display: inline-block;width: 24%;"><a
					style=";text-decoration: none;"><span
						class="glyphicon glyphicon-euro"></span></a></span>
				</div>
			</div>
			</div>
				</div>
			</div>
		<div class="right">
			<div>
				<center>
					<form action="${pageContext.request.contextPath }/user/updateUser.action" method="post">
						<img src="${pageContext.request.contextPath }/img/titalpicture.jpg" height="100px" class="img-circle"
							style="margin-top:50px;"/>
						<div style="margin-top:20px">
							用户名：<input name="username" value="${user.username }" readonly><br/><br/>
							密&nbsp;&nbsp;&nbsp;码：<input name="password" placeholder="********" type="text"><br/><br/>
							<input type="submit" value="修改密码">
						</div>
					</form>
				</center>
			</div>
		</div>
	</div>
	<%@include file="tab.jsp" %>
</body>
</html>
