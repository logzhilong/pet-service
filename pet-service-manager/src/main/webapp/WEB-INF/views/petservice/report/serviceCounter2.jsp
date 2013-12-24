<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">
$( document ).ready(function() {
	showChart = function(){
		FusionCharts.printManager.enabled(true);
		var myChart1 = new FusionCharts("${ctx}/static/fusionCharts/Charts/Column2D.swf", "counter0", "1100", "300", "0", "1");
		//myChart1.setXMLUrl("ManagedPrinting.xml");
		myChart1.setXMLData("${xmlData}");
		myChart1.render("count2Charts${myForm.serviceMethod }");
	}
	showChart();
});
</script>

<div class="pageHeader" >
	<div class="searchBar">
		<form onsubmit="return navTabSearch(this);" method="post">
			日期:
			<select name="year" >
				<c:forEach begin="2012" end="2018" var="itm">
					<option value="${itm }" <c:if test="${year==itm}">selected="selected"</c:if>>
						${itm }
					</option>
				</c:forEach>
			</select>
			-
			<select name="month" >
				<c:forEach begin="1" end="12" var="itm">
					<fmt:formatNumber value="${itm }" type="currency" pattern="00" var="fitm"/>
					<option value="${fitm }" <c:if test="${month==fitm}">selected="selected"</c:if>>
						${fitm }
					</option>
				</c:forEach>
			</select>
			<button type="submit" >查询</button>
			<button type="button" class="close">关闭</button>
		</form>
	</div>
</div>


<div class="pageContent" selector="h1" layoutH="20">
<div align="center">
	
	<div>
		<div id="count2Charts${myForm.serviceMethod }" style="margin-top: 15px;"></div>
		<div>
			<table width="1100px" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="80" >日期</th>
						<th width="80" >渠道名称</th>
						<th width="50" >渠道号</th>
						<th width="100" >总数(次)</th>
						<th >服务名</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" >
						<td align="center">${itm.cd }</td>
						<td>${itm.channelName }</td>
						<td>${itm.channel }</td>
						<td align="center">${itm.totalCount }</td>
						<td>${itm.alias }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
		</div>	
	</div>


</div>
</div>

