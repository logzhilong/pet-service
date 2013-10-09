<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="pagerForm" method="post" action="w_list.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form> 

<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<form onsubmit="return navTabSearch(this);" method="post"
						action="${ctx }/manager/commons/areaCodeList.html">
						<select class="combox" name="father" ref="w_combox_city"
							refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
							<option value="all">--请选择国家--</option>
							<c:forEach var="itr" items="${codes }">
								<option value="${itr.id }">${itr.name }</option>
							</c:forEach>
						</select> <select class="combox" name="grandsunid" id="w_combox_city">
							<option value="all">--请选择省份(市)--</option>
						</select>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">搜索</button>
							</div>

						</div>
					</form>
				</td>
			</tr>
		</table>
	</div>

</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="${ctx }/manager/commons/areaCodeAdd.html" target="dialog"
				max="false" rel="areaCode_add_dialog" mask="true" title="添加"
				width="450" height="260" close="forum.refresh"> <span>添加</span>
			</a></li>
			<li><a class="edit"
				href="${ctx }/manager/commons/areaCodeAdd.html?id={id}"
				target="dialog" max="false" rel="areaCode_update_dialog" mask="true"
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
				<th width="200" align="center">父名称</th>
				<th width="200" align="center">名称</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody">
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>${itm.pname }</td>
					<td>${itm.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!--分页使用-->
	<form id="pagerForm" method="post" action="">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
		<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	</form>
	<!--  	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${pageBean.totalRecorde}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageBean.totalRecorde}" numPerPage="${pageBean.pageSize}" pageNumShown="10" currentPage="${pageBean.pageNo}"></div>
	</div>
	-->
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共23条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="2"></div>

	</div>
	
	
	
	
</div>

