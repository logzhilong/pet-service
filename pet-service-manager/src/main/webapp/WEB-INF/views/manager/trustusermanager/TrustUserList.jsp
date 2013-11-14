<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!--分页操作 -->
<form id="pagerForm" method="post" action="${ctx }/manager/trustuser/userList.html">
	<input type="hidden" name="pageNo" value="1" /> 
	<input type="hidden" name=pageSize value="${pageBean.pageSize}" />
</form>
<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<form onsubmit="return navTabSearch(this);" method="post"
						action="${ctx }/manager/bbs/forumList.html">
						<dt>
							名称:<input type="text" name="name" value="${myForm.name }" />
							<button type="submit">搜索</button>

						</dt>
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
				href="${ctx }/manager/trustuser/TouserAddOrUpdate.html"
				target="dialog" max="false" mask="true" title="添加" width="450"
				height="350" close="forum.refresh"> <span>添加</span>
			</a></li>
			<li><a class="delete" target="ajaxTodo" title="确定要删除吗?"
				href="${ctx }/manager/trustuser/userDel.html?id={id}"> <span>删除</span>
			</a></li>
			<li class="line">line</li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="150" align="center">用户</th>
			</tr>
		</thead>
		<tbody id="trustuserList">
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td><a
						href="${ctx }/manager/trustuser/trustUserDetail.html?id=${itm.userId }"
						rel="trustuser001" target="dialog" max="false" mask="true"
						title="托管用户管理(${itm.nrootId })" width="400" height="300"
						close="forum.refresh">${itm.nrootId } </a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页操作 -->
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select name="pageSize"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select> <span>条，共${pageBean.totalRecorde}条</span>
		</div>
		<div class="pagination" targetType="navTab"
			totalCount="${pageBean.totalRecorde}"
			numPerPage="${pageBean.pageSize}" pageNumShown="10"
			currentPage="${pageBean.pageNo}"></div>
	</div>



</div>

