<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="pageHeader" >
	<div class="searchBar">
		<form onsubmit="return navTabSearch(this);" action="${ctx }/petservice/push/pushMain.html" method="post" id="pushMainForm" >
			状态:
			<select name="state" >
				<option value="">全部</option>
				<option value="NEW" <c:if test="${ myForm.state eq 'NEW' }">selected="selected"</c:if> >NEW--未发送</option>
				<option value="PUSHED" <c:if test="${ myForm.state eq 'PUSHED' }">selected="selected"</c:if> >PUSHED--已经发送</option>
				<option value="LAZZY" <c:if test="${ myForm.state eq 'LAZZY' }">selected="selected"</c:if> >LAZZY--延迟发送</option>
				<option value="PENDING" <c:if test="${ myForm.state eq 'PENDING' }">selected="selected"</c:if> >PENDING--发送中</option>
			</select>
			<button type="submit">查询</button>
			<button type="button" class="close">关闭</button>
		</form>
	</div>
</div>

<div class="pageContent" selector="h1" layoutH="2">

	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&navTabId=panel0601&push=OK" target="ajaxTodo" title="确定要推送吗？" warn="请选择一个"><span>推送</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="add" href="${ctx }/petservice/push/timerAdd.html?id={id}" target="dialog" warn="请选择" width="550" height="200" ><span>计划推送</span></a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="115" >
		<thead>
			<tr>
				<th width="50"></th>
				<th>标题</th>
				<th>来源</th>
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
				<td>${itm.src }</td>
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
	
	<pet:page form="pushMainForm" pageBean="${page }" pageSize="${page.pageSize }" />
</div>
<%-- "pushList" --%>

