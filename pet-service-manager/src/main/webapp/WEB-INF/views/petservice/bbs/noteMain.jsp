<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent sortDrag" selector="h1" layoutH="2">
<div id="noteList">

<div class="panel" defH="550" >
	<h1>${forum.name }</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/bbs/noteAddOrEdit.html?forumId=${forum.id}" target="dialog" mask="true" title="发帖子" width="650" height="650" ><span>添加</span></a>				
				</li>
				<li>
					<a class="edit" href="${ctx }/petservice/bbs/noteAddOrEdit.html?id={id}&forumId=${forum.id}" target="dialog" warn="请选择一个帖子" width="650" height="650" ><span>修改</span></a>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20"></th>
						<th>名称</th>
						<th>状态</th>
						<th width="40">置顶</th>
						<th width="50">创建人</th>
						<th width="150">创建时间</th>
						<th width="150">修改时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="35" align="left" target="id" rel="${itm.id }" >
						<td>${idx.index }</td>
						<td>
							<a href="${ctx }/petservice/bbs/noteView.html?id=${itm.id}" target="dialog" mask="true" title="查看帖子" width="650" height="650" >
								${itm.name }
							</a>
						</td>
						<td>${itm.state }</td>
						<td align="center">
							<c:choose>
								<c:when test="${itm.isTop }">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>								
							</c:choose>
						</td>
						<td>TODO</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.et }" type="both" />
						</td>
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>	

</div>

</div>
<%-- "forumList" --%>


<%-- 分页 --%>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<div class="combox"><div id="combox_9119327" class="select">
			<a href="javascript:" class="" name="numPerPage">${page.pageSize }</a>
		</div></div>
		<span>条，共[${page.totalCount}]条,当前第[${page.pageNo }]页</span>
	</div>
	<div class="pagination" targettype="navTab" currentpage="1">
		
		<ul>
			<c:choose>
				<c:when test="${page.pageNo==0}">
					<li class="j-first disabled">
						<a class="first" href="javascript:alert(0);" ><span>首页-first</span></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="j-prev disabled">
						<a class="previous" href="javascript:alert(0);"><span>上一页-prev</span></a>
					</li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${ page.pageNo >= page.totalCount/page.pageSize }">
					<li class="j-last">
						<a class="last" href="javascript:alert(0);"><span>末页-last</span></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="j-next">
						<a class="next" href="javascript:alert(0);"><span>下一页-next</span></a>
					</li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>
</div>





<div class="formBar">
	<ul>
		<li>
			<div class="button"><div class="buttonContent"><button type="button" class="close">返回圈子</button></div></div>
		</li>
	</ul>
</div>


