<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/userforumcondition/ToAddOrUpdateuserforum.html" target="dialog" max="false" rel="rolemanage_add_dialog" mask="true" title="添加" width="550" height="360" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/userforumcondition/ToAddOrUpdateuserforum.html?id={id}" target="dialog" max="false" rel="rolemanage_update_dialog" mask="true" title="修改" width="550" height="360" >
					<span>修改</span>
				</a>				
			</li>
			<li>
				<a class="delete"  target="ajaxTodo" title="确定要删除吗?" href="${ctx }/manager/userforumcondition/deleteuserforum.html?id={id}" >
					<span>删除</span>
				</a>
			</li>
			
			<li class="line">line</li>
			
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="40" align="left">编号</th>
				<th width="100" align="left">圈子</th>
				<th width="150" align="left">value</th>
				<th width="150" align="left">type</th>
				<th width="100" align="left">创建时间</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<c:forEach items="${userforumlist }" var="ite" varStatus="idx">
				<tr target="id" rel="${ite.id }">
					<td align="left">${idx.index+1 }</td>
					<td align="left">${ite.forumId}</td>
					<td  align="left">${ite.conditionValue}</td>
					<td  align="left">${ite.conditionType}</td>
					<td><fmt:formatDate value="${ite.ct}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

