<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" >
	<div class="searchBar">
		<form onsubmit="return navTabSearch(this);" action="${ctx }/petservice/bbs/noteMain.html" method="post">
			<input type="hidden" name="forumId" value="${myForm.forumId }" /> 
			状态:
			<select name="condition_state" >
				<option value="ALL" <c:if test="${ myForm.condition_state eq 'ALL' }">selected="selected"</c:if>>全部</option>
				<option value="PASS" <c:if test="${ myForm.condition_state eq 'PASS' }">selected="selected"</c:if> >PASS--审核通过</option>
				<option value="REJECT" <c:if test="${ myForm.condition_state eq 'REJECT' }">selected="selected"</c:if> >REJECT--审核拒绝</option>
				<option value="REPORT" <c:if test="${ myForm.condition_state eq 'REPORT' }">selected="selected"</c:if> >REPORT--被举报</option>
				<option value="AUDIT" <c:if test="${ myForm.condition_state eq 'AUDIT' }">selected="selected"</c:if> >AUDIT--审核中</option>
				<option value="ACTIVE" <c:if test="${ myForm.condition_state eq 'ACTIVE' }">selected="selected"</c:if> >ACTIVE--正常</option>
			</select>
			置顶:
			<select name="condition_isTop" >
				<option value="ALL" <c:if test="${ myForm.condition_isTop eq 'ALL' }">selected="selected"</c:if>>全部</option>
				<option value="true" <c:if test="${ myForm.condition_isTop eq 'true' }">selected="selected"</c:if> >是</option>
				<option value="false" <c:if test="${ myForm.condition_isTop eq 'false' }">selected="selected"</c:if> >否</option>
			</select>
			精华:
			<select name="condition_isEute" >
				<option value="ALL" <c:if test="${ myForm.condition_isEute eq 'ALL' }">selected="selected"</c:if> >全部</option>
				<option value="true" <c:if test="${ myForm.condition_isEute eq 'true' }">selected="selected"</c:if> >是</option>
				<option value="false" <c:if test="${ myForm.condition_isEute eq 'false' }">selected="selected"</c:if> >否</option>
			</select>
			<button type="submit">查询</button>
			<button type="button" class="close">返回圈子</button>
		</form>
	</div>
</div>

<div class="pageContent" id="noteList">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/petservice/bbs/noteAddOrEdit.html?forumId=${forum.id}" target="dialog" mask="true" title="发帖子" width="650" height="700" ><span>添加</span></a>				
			</li>
			<li class="line">line</li>
			<li>
				<a class="edit" href="${ctx }/petservice/bbs/noteAddOrEdit.html?id={id}&forumId=${forum.id}" target="dialog" warn="请选择一个帖子" width="650" height="700" ><span>修改</span></a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="115">
		<thead>
			<tr>
				<th width="20"></th>
				<th>名称</th>
				<th>状态</th>
				<th width="40">置顶</th>
				<th width="40">精华</th>
				<th width="150">创建人</th>
				<th width="150">最后修改时间</th>
				<th width="150">创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.data }" var="itm" varStatus="idx">
			<tr height="35" align="left" target="id" rel="${itm.id }" >
				<td>${idx.index+1 }</td>
				<td>
					<a href="${ctx }/petservice/bbs/noteAddOrEdit.html?id=${itm.id}&forumId=${forum.id}" target="dialog" mask="true" title="修改" width="650" height="650" >
						${itm.name }
					</a>
				</td>
				<td>${itm.state }</td>
				<td align="center">
					<c:choose>
						<c:when test="${itm.isTop }">
							<font color="red">是</font>
						</c:when>
						<c:otherwise>
							<font color="green">否</font>
						</c:otherwise>								
					</c:choose>
				</td>
				<td align="center">
					<c:choose>
						<c:when test="${itm.isEute }">
							<font color="red">是</font>
						</c:when>
						<c:otherwise>
							<font color="green">否</font>
						</c:otherwise>								
					</c:choose>
				</td>
				<td>${itm.nickname }</td>
				<td align="center">
					<fmt:formatDate value="${itm.et }" type="both" />
				</td>
				<td align="center">
					<fmt:formatDate value="${itm.ct }" type="both" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
		
	</table>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="${page.pageSize }">${page.pageSize }</option>
			</select>
			<span>条，共[${page.totalCount}]条,当前第[${page.pageNo }]页</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>