<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
$( document ).ready(function() {
	showChart = function(){
		FusionCharts.printManager.enabled(true);
		var myChart1 = new FusionCharts("${ctx}/static/fusionCharts/Charts/Pie2D.swf", "counter0", "510", "450", "0", "1");
		//myChart1.setXMLUrl("ManagedPrinting.xml");
		myChart1.setXMLData("${xmlData}");
		myChart1.render("count0Charts");
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
		<div id="count0Charts" style="margin:10px;float:left;min-height:100px"></div>	
		<!-- left end -->
		
		<!-- right start -->
		<div style="margin:10px;float:left;min-height:100px">
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th width="120">服务名</th>
						<th width="100" >总数(次)</th>
						<th width="120" >服务注册码</th>
						<th width="120" >方法</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="40" align="left" target="serviceMethod" rel="${itm.service }__${itm.method }" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=${itm.service }__${itm.method }" title="${itm.alias }--服务统计" target="navTab" rel="serviceCounter1Tab">
								${itm.alias }
							</a>
						</td>
						<td>${itm.totalCount }</td>
						<td>${itm.service }</td>
						<td>${itm.method }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- right end -->	
		
	</div>

</div>
</div>

