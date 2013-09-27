<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					日期：<input id="queryDate" type="text" class="date" readonly="true" dateFmt="yyyy-MM-dd" value="${queryDate }" />
					TODO : 需要增加很多检索条件
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="defineAlert.queryAlertsByDate( $('#queryDate').val() )" >检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>

</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/commons/areaCodeAdd.html" target="dialog" max="false" rel="areaCode_add_dialog" mask="true" title="添加" width="450" height="260" close="forum.refresh" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/commons/areaCodeAdd.html?id={id}" target="dialog" max="false" rel="alert_dialog" mask="true" title="修改预警" width="800" height="550" close="defineAlert.refresh" param="'${id }'">
					<span>修改</span>
				</a>
			</li>
			<li>
				<a class="delete" onclick="defineAlert.deleteAlert('checkedAlert')" href="javascript:void" title="确定要删除吗?" >
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
				<th width="100" align="center">ID</th>
				<th width="100" align="center">PID</th>
				<th width="200" align="center">父名称</th>
				<th width="200" align="center" >名称</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>${itm.id }</td>
					<td>${itm.pid }</td>
					<td>${itm.pname }</td>
					<td>${itm.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</div>

