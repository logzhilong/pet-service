<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader">
<h1>用户统计</h1>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="10%" align="left">渠道</th>
				<th width="15%" align="left">日注册量</th>
				<th width="15%" align="left">周注册量</th>
				<th width="15%" align="left">月注册量</th>
				<th width="15%" align="left">日用户量</th>
				<th width="15%" align="left">周用户量</th>
				<th width="15%" align="left">月用户量</th>
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

