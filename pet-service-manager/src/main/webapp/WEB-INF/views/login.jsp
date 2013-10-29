<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宠物-后台v1.0</title>
<link href="${ctx }/static/dwz1.4.5/themes/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="" style="font-size: 40px;">宠物-后台</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">关于我们</a></li>
					</ul>
				</div>
				<h2 class="login_title">登录</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="${ctx }/j_spring_security_check"  method="post">
					<p style="color: red;">
						${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
					</p>
					<p>
						<label>用户名：</label>
						<input type="text" name="j_username" size="15" class="login_input" value=""/>
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="j_password" size="16" class="login_input"  value=""/>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner">
					<img src="${ctx }/static/dwz1.4.5/themes/default/images/login_banner.jpg" />
			</div>
			
			<div class="login_main">
				<div class="login_inner">
					<p>宠物-后台v1.0</p>
				</div>
			</div>
			
		</div>
		<div id="login_footer">
			Copyright &copy; 2013 XXXXXX Inc. All Rights Reserved.
		</div>
	</div>
	
</body>
</html>