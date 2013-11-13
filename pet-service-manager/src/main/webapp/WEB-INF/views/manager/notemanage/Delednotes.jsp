<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="130">
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
		<tbody id="areaCode_tbody" >
			<c:forEach items="${list }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>
						<a href="${ctx }/manager/notemanager/notedetail.html?id=${itm.id }"
							target="dialog" max="false"  mask="true"
							title="帖子详情(${itm.name })" width="600" height="500"
							close="forum.refresh"> ${itm.name } 
						</a>
					</td>
					<td>${itm.clientCount }</td>
					<td>${itm.isEute }</td>
					<td>${itm.isTop }</td>
					<td>${itm.isDel }</td>
					<td>${itm.state }</td>
					<td>${itm.type }</td>
					<td><fmt:formatDate value="${itm.ct}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${itm.et}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

