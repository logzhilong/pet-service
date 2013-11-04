<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

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
				href="${ctx }/manager/trustuser/TouserAddOrUpdate.html" target="dialog"
				max="false" mask="true" title="添加" width="450" height="350"
				close="forum.refresh"> <span>添加</span>
			</a></li>
			<li><a class="edit"
				href="${ctx }/manager/trustuser/TouserAddOrUpdate.html?id={id}"
				target="dialog" max="false" mask="true" title="修改" width="450"
				height="350" close="forum.refresh"> <span>修改</span>
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
				<th width="15" align="center"></th>
				<th width="150" align="center">用户</th>
			</tr>
		</thead>
		<tbody id="trustuserList">
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>${itm.nrootId }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

