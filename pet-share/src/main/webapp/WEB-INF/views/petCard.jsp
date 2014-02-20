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
<c:set var="sp" value="&nbsp;&nbsp;&nbsp;&nbsp;" />
<table width="600" style="font-size: 15pt;">
	<tr>
		<td align="center" valign="middle" style="background-color: #EE7600;font-size: 25pt;" colspan="2">
			<div style="margin-top: 20;margin-bottom: 30;color: white;">
				二维码挂件信息
			</div>
		</td>
	</tr>
	<tr><td height="10px" colspan="2"></td></tr>
	<tr style="background-color: white;height: 70px;"><td width="150" align="left" >${sp}${sp}宠物品种</td><td style="color: #838B8B;">${sp}${p.petType }</td></tr>
	<tr style="background-color: white;height: 70px;"><td width="150" align="left" >${sp}${sp}宠物昵称</td><td style="color: #838B8B;">${sp}${p.petType }</td></tr>
	<tr><td height="10px" colspan="2"></td></tr>
	<tr style="background-color: white;height: 70px;"><td width="150" align="left" >${sp}${sp}主人昵称</td><td style="color: #838B8B;">${sp}${p.petOwner }</td></tr>
	<tr style="background-color: white;height: 70px;"><td width="150" align="left" >${sp}${sp}主人电话</td><td style="color: #838B8B;">${sp}${p.petOwnerTel }</td></tr>
	<tr style="background-color: white;height: 70px;"><td align="left" colspan="2">${sp}${sp}主人寄语</td></tr>
	<tr style="background-color: white;height: 70px;">
		<td align="left" colspan="2" style="color: #838B8B;">
			${sp}${sp}${p.petOwnerMsg }
		</td>
	</tr>
	<tr><td height="10px" colspan="2"></td></tr>
	<tr><td align="left" colspan="2">
		<div style="margin-top:20;margin-bottom:10; font-size: 20pt;color: #EE4000;">
			${sp }下载宠物圈:
		</div>
	</td></tr>
	<tr><td align="center" valign="middle" colspan="2">
		<a href="https://itunes.apple.com/us/app/chong-wu-quan-ai-chong-wu/id686838840?ls=1&mt=8" >
			<img alt="" src="${ctx }/static/images/iphone_down_normal.png" />
		</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="http://221.122.114.216/pet-file-server/update/1_7_0/pet.apk" >
			<img alt="" src="${ctx }/static/images/android_down_normal.png" />
		</a>
	</td></tr>

</table>
    
</body></html>