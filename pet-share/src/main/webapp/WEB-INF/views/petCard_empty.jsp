<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=480">
<title>
</title>
<style>
body, h1, h2, h3, h4, h5, h6, hr, p, blockquote, dl, dt, dd, ul, ol, li, pre, form, fieldset, legend, button, input, textarea, th, td {
    margin: 0;
    padding: 0;
}
IMG {
	BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-BOTTOM-STYLE: none
}
A:link {
	COLOR: #666666; TEXT-DECORATION: none
}
A:hover {
	COLOR: #ff0000; TEXT-DECORATION: underline
}
A:visited {
	COLOR: #666666; TEXT-DECORATION: none
}
.clear {
	CLEAR: both
}
body {
	margin:0px; padding:0px;
	font-size: 17px;
    color: #444;
    line-height: 150%;
    background: #EBEBEB;
    -webkit-text-size-adjust: none;
    min-width: 320px;
}
.window {
	text-align:center; 0px; MARGIN: 0px; COLOR: #666666; BACKGROUND: #aaaaaa; 
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: PADDING-TOP: 0px;
}
.main {
	text-align:left;
	MIN-HEIGHT: 350px; BACKGROUND: #ffffff;
	width: 100%;
}
.videobox {
    overflow: hidden;
}
.videobox, .videohelp {
    clear: both;
    overflow: hidden;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    border: 1px solid #C7CCD3;
    background: white;
    margin: 3px 3px 3px 3px;
}
.videomate {
    clear: both;
    overflow: hidden;
    padding: 18px;
    line-height: 160%;
    white-space: normal;
}
</style>

<script type="text/javascript">
    function css() {
			if (window.ActiveXObject == null) {
			    document.write('<style type="text/css"> * {MARGIN: 0px auto}</style>');
			}
			if(window.top != window) {
					document.write('<style type="text/css"> * {font-size:16px;} </style>');
			}
  	}
  	css();
</script><style type="text/css"> * {MARGIN: 0px auto}</style>
</head>
<body>
	
<table width="640">
	
	<tr><td align="center" valign="middle" style="background-color: #EE7600;font-size: 25pt;" >
			<div style="margin-top: 20;margin-bottom: 30;color: white;">
				二维码挂件信息
			</div>
	</td></tr>

	<tr><td align="center" valign="middle" style="background-color: white;font-size: 20pt;" >
		<div style="height: 200px; margin-top: 50;color: #8B2252;">
			<p style="margin-bottom: 20;">糟糕！</p>
			<p style="margin-bottom: 20;">该二维码挂件尚未被绑定，</p>
			<p style="margin-bottom: 20;">立即下载宠物圈，寻找宠物主人</p>
		</div>
	</td></tr>
	
	<tr><td align="left">
		<div style="margin-top:20;margin-bottom:10; font-size: 15pt;">
			下载宠物圈:
		</div>
	</td></tr>
	<tr><td align="center" valign="middle">
		<a href="https://itunes.apple.com/us/app/chong-wu-quan-ai-chong-wu/id686838840?ls=1&mt=8" >
			<img alt="" src="${ctx }/static/images/iphone_down_normal.png" />
		</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="http://221.122.114.216/pet-file-server/update/1_7_0/pet.apk" >
			<img alt="" src="${ctx }/static/images/android_down_normal.png" />
		</a>
	</td></tr>
	<tr><td align="left">
		<div style="margin-top:20;margin-bottom:10; font-size: 15pt;">
			宠物圈防丢二维码挂件：
		</div>		
	</td></tr>
	<tr><td style="font-size: 13pt;color: #7A7A7A">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		将主人和宠物信息转换为二维码，挂在宠物身上，好心人扫一扫，即可找到主人。爱它请挂上我，多一份关心，增添一份快乐
	</td></tr>
	<tr><td align="left" nowrap="nowrap">
		<div style="margin-top: 20;font-size: 20pt;">
			激活二维码很方便，一共就三步
		</div>
	</td></tr>
	<tr><td align="left">
		<table width="100%" style="margin-top: 30;">
			<tr>
				<td rowspan="2" width="100" align="center">
					<img alt="" src="${ctx }/static/images/1.png" />
				</td>
				<td>
					<div style="font-size: 15pt;color: #EE4000">
						激活它
					</div>
				</td>
			</tr>
			<tr>
				<td style="color: #838B8B;">下载注册宠物圈，用宠物圈扫描添加二维码</td>
			</tr>
		</table>
		
	</td></tr>
	<tr><td>
		<table width="100%" style="margin-top: 30;">
			<tr>
				<td rowspan="2" width="100" align="center">
					<img alt="" src="${ctx }/static/images/2.png" />
				</td>
				<td>
					<div style="font-size: 15pt;color: #FF7F00">
						完善它
					</div>
				</td>
			</tr>
			<tr>
				<td style="color: #838B8B;">完善您和爱宠信息</td>
			</tr>
		</table>
	</td></tr>
	<tr><td>
		<table width="100%" style="margin-top: 30;">
			<tr>
				<td rowspan="2" width="100" align="center">
					<img alt="" src="${ctx }/static/images/3.png" />
				</td>
				<td>
					<div style="font-size: 15pt;color: #66CD00">
						挂上它
					</div>
				</td>
			</tr>
			<tr>
				<td style="color: #838B8B;">给爱宠带上激活的二维码，安心又快乐</td>
			</tr>
		</table>
	</td></tr>
</table>
	
</body></html>