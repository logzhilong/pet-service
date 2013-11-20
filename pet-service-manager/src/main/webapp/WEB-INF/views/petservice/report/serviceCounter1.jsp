<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
$( document ).ready(function() {
	showChart = function(){
		FusionCharts.printManager.enabled(true);
		var myChart1 = new FusionCharts("${ctx}/static/fusionCharts/Charts/Pie2D.swf", "counter0", "510", "450", "0", "1");
		//myChart1.setXMLUrl("ManagedPrinting.xml");
		myChart1.setXMLData("${xmlData}");
		myChart1.render("count1Charts");
	}
	showChart();
});
</script>


<div class="pageHeader" >
	<div class="searchBar">
			按总数排序:
			<select name="sort" >
				<option value="desc">从大到小</option>
				<option value="asc">从小到大</option>
			</select>
			-
			<button type="button" onclick="alert('开发中...');" >查询</button>
			<button type="button" class="close">关闭</button>
	</div>
</div>


<div class="pageContent" selector="h1" layoutH="2">
<div>

	<div>
	
		<!-- left start -->
		<div style="margin:10px;float:left;min-height:100px">
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th width="100" >渠道名称</th>
						<th width="50" >渠道号</th>
						<th width="90" >总数(次)</th>
						<th width="120" >服务名</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<a href="${ctx }/petservice/report/serviceCounter2.html?serviceMethod=${itm.service }__${itm.method }__${itm.channel}" title="${itm.channelName }-渠道统计" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								${itm.channelName }
							</a>
						</td>
						<td>${itm.channel }</td>
						<td align="center">${itm.totalCount }</td>
						<td>${itm.alias }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- left end -->

		<!-- right start -->
		<div id="count1Charts" style="margin:10px;float:left;min-height:100px"></div>	
		<!-- right end -->	
	</div>


</div>
</div>

