<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/mgrusermanager/TousermanageSaveOrUpdate.html" target="dialog" max="false" rel="rolemanage_add_dialog" mask="true" title="添加" width="550" height="360" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/mgrusermanager/TousermanageSaveOrUpdate.html?id={id}" target="dialog" max="false" rel="rolemanage_update_dialog" mask="true" title="修改" width="550" height="360" >
					<span>修改</span>
				</a>				
			</li>
			<li>
				<a class="delete"  target="ajaxTodo" title="确定要删除吗?" href="${ctx }/manager/mgrusermanager/usermanageDel.html?id={id}" >
					<span>删除</span>
				</a>
			</li>
			
			<li class="line">line</li>
			
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="30" align="left">编号</th>
				<th width="100" align="left">用户名称</th>
				<th width="100" align="left">创建时间</th>
				<th width="100" align="left">修改时间</th>
				<th width="100" align="left">创建人</th>
				<th width="100" align="left">修改人</th>
				<th width="100" align="left">是否可用</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<c:forEach items="${musers }" var="ite" varStatus="idx">
				<tr target="id" rel="${ite.id }">
					<td align="left">${idx.index+1 }</td>
					<td align="left">${ite.name}</td>
					<td align="left">${ite.ct }</td>
					<td align="left">${ite.et }</td>
					<td align="left">${ite.cb }</td>
					<td align="left">${ite.eb }</td>
					<td align="left">${ite.enable }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

