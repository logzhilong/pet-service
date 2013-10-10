<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="pageHeader">
</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a 	class="add"
					href="${ctx }/manager/configmanager/add.html" 
					target="dialog"
					max="false" 
					rel="cfg_mgr_add" 
					mask="true" title="添加" width="450" height="260">
					<span>添加</span>
				</a>
			</li>
			<li><a class="edit"
				href="${ctx }/manager/configmanager/add.html?id={id}"
				target="dialog" max="false" rel="cfg_mgr_edit" mask="true"
				title="修改" width="450" height="260" close="forum.refresh"> <span>修改</span>
			</a></li>
			<li><a class="delete" target="ajaxTodo" title="确定要删除吗?"
				href="${ctx }/manager/commons/areaCodeDel.html?id={id}"> <span>删除</span>
			</a></li>
			<li class="line">line</li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="15" align="center"></th>
				<th width="200" align="center">key</th>
				<th width="200" align="center">value</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody">
			<c:forEach items="${list }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.key }">
					<td>${idx.index+1 }</td>
					<td>${itm.key }</td>
					<td>${itm.value }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

