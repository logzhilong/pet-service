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
    background: #BABABA;
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
	<div class="window">
        <div class="main">
            <div id="pane_20121107191151627" class="pane" style="display:">
                <div id="ctrl_20121107191151627" style="">
                      <div>

                        <div>
                            <div style="background-color:#C90; color:white; line-height:40px; ">
                                <p style="padding: 4px 0 0 10px;">
                                	宠物名片
                                </p>
                            </div>
                            <div></div>
                        </div>
						
						<div style="background-color: #EFEFEF;">
							<p style="line-height: 50px;margin-left: 20px;">宠物类型：${p.petType }</p>
							<p style="line-height: 50px;margin-left: 20px;">宠物昵称：${p.petNickname }</p>
							<p style="line-height: 50px;margin-left: 20px;">宠物主人：${p.petOwner }</p>
							<p style="line-height: 50px;margin-left: 20px;">联系电话：${p.petOwnerTel }</p>
							<p style="line-height: 50px;margin-left: 20px;">主人寄语：${p.petOwnerMsg }</p>
                        </div>                            
                        
                        <div style="background: #EBEBEB;border-top: 1px solid #BABABA;padding: 15px 0 10px;color: #212224;text-align: center;">
			        		<a href="${bottom_link }">${bottom }</a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</body></html>