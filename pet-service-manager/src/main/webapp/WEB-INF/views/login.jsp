<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务监控云平台v0.1</title>
<link href="${ctx }/static/dwz1.4.5/themes/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="" style="font-size: 40px;">服务器监控云平台</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">设为首页</a></li>
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
						<input type="text" name="j_username" size="15" class="login_input" value="admin"/>
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="j_password" size="16" class="login_input"  value="123"/>
					</p>
					<!-- 
					<p>
						<label>验证码：</label>
						<input class="code" type="text" size="5" />
						<span><img src="themes/default/images/header_bg.png" alt="" width="75" height="24" /></span>
					</p>
					 -->
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
					<p>服务器监控云平台v0.1</p>
				</div>
			</div>
			
		</div>
		<div id="login_footer">
			Copyright &copy; 2013 XXXXXX Inc. All Rights Reserved.
		</div>
	</div>
	
	
	
	
	
	
	
							
	
							<script type="text/javascript" src="${ctx }/static/fusionCharts/Charts/FusionCharts.js"></script>

							<script src="${ctx }/static/dwz1.4.5/js/jquery-1.7.2.js" type="text/javascript"></script>
	
	
							<div id="chartContainer" ></div>
							<script type="text/javascript">
							function createCharts(id,jsonDataUrl){
							    var chart1 = new FusionCharts("${ctx }/static/fusionCharts/Charts/ZoomLine.swf", "eid", "650", "250", "0", "0");
							    chart1.setJSONUrl(jsonDataUrl);
							    chart1.setDataURL(jsonDataUrl);
							    chart1.render(id);
							}
							function createCharts2(id,data){
							    var chart1 = new FusionCharts("${ctx }/static/fusionCharts/Charts/ZoomLine.swf", "ChId1", "900", "400", "0", "1");
                                chart1.setXMLData(data);
                                chart1.render(id);
							}
							$(document).ready ( function() {

								
								var xmlData = 	 '<chart compactDataMode="1" dataSeparator="|" caption="Business Results 2005 v 2006" xaxisname="Month" yaxisname="Revenue" showvalues="0" numberprefix="$" paletteThemeColor="5D57A5" divLineColor="5D57A5" divLineAlpha="40" vDivLineAlpha="40">'
												+'<categories>Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec</categories>'
												+'<dataset seriesname="2006">27400|29800|25800|26800|29600|32600|31800|36700|29700|31900|34800|24800</dataset>'
												+'<dataset seriesname="2005">10000|11500|12500|15000|11000|9800|11800|19700|21700|21900|22900|20800</dataset>'
												+'</chart>';
									
								//alert(xmlData);
								//createCharts2("chartContainer",xmlData);
							});
							</script>
								
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>