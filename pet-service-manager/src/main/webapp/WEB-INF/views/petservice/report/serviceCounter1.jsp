<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="serviceDictList">

<div class="panel" defH="600" id="serviceDictList_panel">
	
	<h1>
		${serviceName }-服务统计
		
		<button>返回</button>
	</h1>
	
	<div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th width="150" >渠道名称</th>
						<th width="150" >渠道编码</th>
						<th width="100" >服务名</th>
						<th width="100" >响应次数</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" >
						<td align="center">${idx.index+1 }</td>
						<td>${itm.channelName }</td>
						<td>${itm.channel }</td>
						<td>${itm.alias }</td>
						<td>${itm.totalCount }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>	
	</div>
</div>



</div>
</div>

