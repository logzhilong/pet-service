<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>宠物-后台管理</title>

<link href="${ctx }/static/dwz1.4.5/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx }/static/dwz1.4.5/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx }/static/dwz1.4.5/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${ctx }/static/dwz1.4.5/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="${ctx }/static/dwz1.4.5/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="${ctx }/static/dwz1.4.5/js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script type="text/javascript" src="${ctx }/static/fusionCharts/Charts/FusionCharts.js"></script>

<script src="${ctx }/static/dwz1.4.5/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/raphael.js"></script>
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/g.raphael.js"></script>
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/g.bar.js"></script>
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/g.line.js"></script>
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/g.pie.js"></script>
<script type="text/javascript" src="${ctx }/static/dwz1.4.5/chart/g.dot.js"></script>

<script src="${ctx }/static/dwz1.4.5/js/dwz.core.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.drag.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.tree.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.ui.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.theme.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.tab.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.resize.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.stable.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.database.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.effects.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.panel.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.history.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.combox.js" type="text/javascript"></script>
<script src="${ctx }/static/dwz1.4.5/js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="${ctx }/static/dwz1.4.5/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="${ctx }/static/js/petServiceManager.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("${ctx }/static/dwz1.4.5/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{
			pageNum:"pageNo", 
			numPerPage:"pageSize", 
			orderField:"orderField", 
			orderDirection:"orderDirection"
		}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctx }/static/dwz1.4.5/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">
					把LOGO放在这个目录就可以了：static/dwz1.4.5/themes/default/images/logo.png
				</a>
				<ul class="nav">
					<li><a href="###">
						<%--
						<sec:authentication property="principal.username"/>
						--%>
						,&nbsp;您好</a></li>
					<li><a href="${ctx }/login.html">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="green"><div>绿色</div></li>
					<li theme="default"><div class="selected" >蓝色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<%--
					左侧导航菜单 开始 >>>>>>>>>>>>>>>>>>>>>
					--%>
					<div class="accordionHeader">
						<h2><span>Folder</span>功能菜单</h2>
						<!-- <sec:authentication property="principal.username"/> -->
					</div>
					<div class="accordionContent" id="left_1">
						<%-- include 功能树 --%>
						<jsp:include page="/funTree.html" />
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>系统管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li>
								<a>账户管理</a>
								<ul>
									<li><a>修改密码</a></li>
								</ul>
							</li>
							<li>
								<a>配置管理</a>
								<ul>
									<li><a rel="commanage" target="navTabId" href="/pet-service-manager/manager/commons/commanageList.html">公共配置管理</a></li>
								</ul>
							</li>
						</ul>
					</div>

					<%--
					左侧导航菜单 结束 <<<<<<<<<<<<<<<<<<<<<<<
					--%>
					
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main">
								<a href="javascript:;"><span>
									<span class="home_icon">我的主页</span>
								</span></a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo"></div>
						<div class="pageFormContent" layoutH="80">
							欢迎来到服务器监控平台 v0.1<br/><br/><br/><br/>
							<h1>
							<a href="${ctx }/static/dwz1.4.5/index.html" target="_blank">帮助（点我查看dwz demo）</a>
							</h1>
							<br/><br/><br/><br/>
							<h1>
							<a href="${ctx }/static/dwz1.4.5/index.html" target="_blank">帮助（点我查看dwz demo）</a>
							</h1>
							<br/><br/><br/><br/>
							<h1>
							<a href="${ctx }/static/dwz1.4.5/index.html" target="_blank">帮助（点我查看dwz demo）</a>
							</h1>
							<br/><br/><br/><br/>
							<h1>
							<a href="${ctx }/static/dwz1.4.5/index.html" target="_blank">帮助（点我查看dwz demo）</a>
							</h1>
							<br/><br/><br/><br/>
							
						</div>
					</div>
					
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2013 <a href="" target="dialog">XXXXXX.ltd</a> Tel：88888888</div>
</body>
</html>


