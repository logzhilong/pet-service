<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox');" action="demo/pagination/list1.html" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/notemanager/ToNoteAddOrUpdate.html?forumId=${forumid}"  target="dialog" max="false" mask="true" title="添加" width="650" height="500" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/notemanager/ToNoteAddOrUpdate.html?id={id}" rel="ffeer1"  target="dialog" max="false"  mask="true" title="修改" width="600" height="560" >
					<span>修改</span>
				</a>				
			</li>
			<li>
				<a class="delete"  target="ajaxTodo" title="确定要删除吗?" href="${ctx }/manager/notemanager/NoteDel.html?id={id}" >
					<span>删除</span>
				</a>
			</li>
			<li>
				<form  method="post" onsubmit="return divSearch(this, 'jbsxBox1');"  action="${ctx }/manager/bbs/forumrightmanagelist.html?id=${forumid}">
						<input type="text" name="tname"/>
						<button type="submit">搜索</button>
				</form>
			</li>
			<li class="line">line</li>
			<li>&nbsp;&nbsp;&nbsp;</li>
			<li><a class="edit" href="${ctx }/manager/forummamage/Toupdateforum.html?id=${forumid}" rel="quanzi001"  target="dialog" max="false"  mask="true" title="修改圈子" width="600" height="350" >
					<span>修改本圈</span>
				</a></li>
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

