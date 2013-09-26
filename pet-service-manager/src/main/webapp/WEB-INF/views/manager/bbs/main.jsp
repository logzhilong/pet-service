<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent sortDrag" selector="h1" layoutH="2">

	<div class="panel collapse" >
		<h1>
			统计信息汇总
		</h1>
		<div id="platform_info_div" style="height: 100px;">
			<%-- 平台基本信息 --%>
			<table class="grid" width="99%" align="center" >
				
			</table>
		</div>
	</div>
	
	<div class="panel" defH="190">
		<h1>发帖量统计</h1>
		<div id="rt_internal_f${platformName }">

		</div>
	</div>

	<div class="panel" defH="190">
		<h1>回帖量统计</h1>
		<div id="rt_internal_t${platformName }">
		
		</div>
	</div>

</div>
