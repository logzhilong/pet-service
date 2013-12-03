<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="pushList">

<div class="panel" defH="600" id="pushList_panel">
	
	<h1>系统通知</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&navTabId=panel0601&push=OK" target="ajaxTodo" title="确定要推送吗？" warn="请选择一个"><span>推送</span></a>
				</li>
				<li class="line">line</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="50"></th>
						<th>标题</th>
						<th>状态</th>
						<th>创建日期</th>
						<th>修改日期</th>
						<th>创建人</th>
						<th>修改人</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${idx.index+1 }</td>
						<td>${itm.name }</td>
						<td>${itm.state }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.et }" type="both" />
						</td>
						<td>${itm.cb }</td>
						<td>${itm.eb }</td>
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>	
	</div>
</div>

</div>
<%-- "pushList_panel" --%>

</div>
<%-- "pushList" --%>

