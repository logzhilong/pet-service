<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<table class="table" width="100%" layoutH="30">
		<thead>
			<tr>
				<th width="50" align="center">编号</th>
				<th width="200" align="center">名称</th>
				<th width="100" align="center">点击量</th>
				<th width="100" align="center" >是否被举报</th>
				<th width="100" align="center" >是否置顶</th>
				<th width="100" align="center" >是否删除</th>
				<th width="100" align="center" >状态</th>
				<th width="100" align="center" >类型</th>
				<th width="270" align="center" >创建时间</th>
				<th width="270" align="center" >最后修改时间</th>
			</tr>
		</thead>
		<tbody id="alertListTbody" >
			<c:forEach items="${forums }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td><a>${itm.name }</a></td>
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

