<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="serviceDictList">

<div class="panel" defH="600" id="serviceDictList_panel">
	
	<h1>服务使用率统计-总数</h1>
	
	<div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th width="100">服务名</th>
						<th width="100" >响应次数</th>
						<th width="150" >服务注册码</th>
						<th width="150" >方法</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="serviceMethod" rel="${itm.service }__${itm.method }" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=${itm.service }__${itm.method }" target="navTab" rel="serviceCounter1Tab">
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
	</div>
</div>



</div>
</div>

