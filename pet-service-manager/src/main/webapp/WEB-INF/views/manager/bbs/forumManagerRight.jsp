<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div>

<div class="tabs" currentIndex="0" eventType="click" style="width:100%" >
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"><span>度量监控图</span></a></li>
				<li><a href="javascript:;"><span>度量数据</span></a></li>
			</ul>
		</div>
	</div>
	<%-- START tabsContent --%>
	<div class="tabsContent" layoutH="40">
	
		<%-- START indicatorsDiv --%>
		<div id="indicatorsDiv" ></div>
		
		
		
		<%-- START metricDataDiv --%>
		<div id="metricDataDiv">
		
			<table class="table" width="100%" >
				<thead>
					<th align="left">可用性</th>
					<th width="20px">最小值</th>
					<th width="20px">平均值</th>
					<th width="20px">最大值</th>
					<th width="60px">收集间隔</th>
				</thead>
				<tbody>
					<c:forEach begin="1" end="3">
						<tr>
							<td>Availability</td>		
							<td width="50px">0.0%</td>		
							<td width="50px">0.0%</td>		
							<td width="50px">0.0%</td>		
							<td width="100px">00:05:00</td>	
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table" width="100%" >
				<thead>
					<th align="left">使用率</th>
					<th width="20px">最小值</th>
					<th width="20px">平均值</th>
					<th width="20px">最大值</th>
					<th width="60px">收集间隔</th>
				</thead>
				<tbody>
					<c:forEach begin="1" end="3">
						<tr>
							<td>Availability</td>		
							<td width="50px">0.0%</td>		
							<td width="50px">0.0%</td>		
							<td width="50px">0.0%</td>		
							<td width="100px">00:05:00</td>	
						</tr>
					</c:forEach>
				</tbody>
			</table>			
			
		</div>
		<%-- END metricDataDiv --%>
		
		
	</div>
	<%-- END tabsContent --%>
	
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>









</div>