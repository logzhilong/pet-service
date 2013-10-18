<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
<!-- 
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

 -->
	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="100" align="left">名称</th>
				<th width="200" align="left">内容</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<td>${note2.name }</td>
			<td>${note2.content }</td>
		</tbody>
	</table>
</div>

