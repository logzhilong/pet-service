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
			日期:
			<select name="year" >
				<option value="2013">2013</option>
				<option value="2013">2012</option>
			</select>
			-
			<select name="month" >
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="02">02</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			<button type="button" onclick="alert('开发中...');" >查询</button>
			<button type="button" class="close">关闭</button>
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

