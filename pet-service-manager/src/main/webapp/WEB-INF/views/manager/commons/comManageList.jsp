<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/commons/TocommanageSaveOrUpdate.html" target="dialog" max="false" rel="areaCode_add_dialog" mask="true" title="添加" width="450" height="260" close="forum.refresh" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/commons/TocommanageSaveOrUpdate.html?key={key}" target="dialog" max="false" rel="areaCode_update_dialog" mask="true" title="修改" width="450" height="260" close="forum.refresh" >
					<span>修改</span>
				</a>				
			</li>
			<li>
				<a class="delete"  target="ajaxTodo" title="确定要删除吗?" href="${ctx }/manager/commons/commanageDel.html?key={key}" >
					<span>删除</span>
				</a>
			</li>
			
			<li class="line">line</li>
			
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="15" align="center"></th>
				<th width="200" align="center">Key</th>
				<th width="200" align="center" >Value</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<c:forEach items="${configlist }" var="ite" varStatus="idx">
				<tr target="key" rel="${ite.key }">
					<td>${idx.index+1 }</td>
					<td>${ite.key}</td>
					<td>${ite.value }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

