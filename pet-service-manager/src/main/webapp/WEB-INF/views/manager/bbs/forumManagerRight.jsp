<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/notemanager/ToNoteAddOrUpdate.html" target="dialog" max="false" rel="rolemanage_add_dialog" mask="true" title="添加" width="850" height="760" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/notemanager/ToNoteAddOrUpdate.html?id={id}" target="dialog" max="false" rel="rolemanage_update_dialog" mask="true" title="修改" width="550" height="360" >
					<span>修改</span>
				</a>				
			</li>
			<li>
				<a class="delete"  target="ajaxTodo" title="确定要删除吗?" href="${ctx }/manager/notemanager/NoteDel.html?id={id}" >
					<span>删除</span>
				</a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>

	<table class="table" width="105%" layoutH="30">
		<thead>
			<tr>
				<th width="100" align="center">编号</th>
				<th width="200">名称</th>
				<th width="100">点击量</th>
				<th width="100">被举报</th>
				<th width="100">置顶</th>
				<th width="100">被删除</th>
				<th width="100">状态</th>
				<th width="100">类型</th>
				<th width="500">创建时间</th>
				<th width="500">最后修改时间</th>
			</tr>
		</thead>
		<tbody id="alertListTbody">
			<c:forEach items="${forums }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td><a
						href="${ctx }/manager/notemanager/notedetail.html?id={id}"
						target="dialog" max="false" rel="note_detail_dialog" mask="true"
						title="帖子详情(${itm.name })" width="600" height="500"
						close="forum.refresh" param="'${itm.id }'"> ${itm.name } </a></td>
					<td>${itm.clientCount }</td>
					<td>${itm.isEute }</td>
					<td>${itm.isTop }</td>
					<td>${itm.isDel }</td>
					<td>${itm.state }</td>
					<td>${itm.type }</td>
					<td>${itm.ct }</td>
					<td>${itm.et }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

