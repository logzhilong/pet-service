<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader" >
	<div class="searchBar">
		<button type="button" class="close">关闭</button>
	</div>
</div>

<div class="pageContent" id="noteList">
	<div class="panelBar">
		<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/albums/publicAlbumsAddOrEdit.html" target="dialog" mask="true" title="添加" width="700" height="650" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/albums/publicAlbumsAddOrEdit.html?id={id}" target="dialog" warn="请选择一个美图" width="700" height="650" ><span>修改</span></a>
				</li>
				<li class="line">line</li>
			</ul>
	</div>
	<table class="grid" width="100%" layoutH="95" border="1" style="border-color: #F0F0F0;">
		<c:forEach items="${page.data }" var="itm" varStatus="idx">
			<c:if test="${ idx.index%5==0 }">
				<tr align="left" target="id" rel="${itm.id }" >
			</c:if>
					<td>
						<a class="edit" href="${ctx }/petservice/albums/publicAlbumsAddOrEdit.html?id=${itm.id }" target="dialog" width="700" height="650" >
							<img src="${pet_file_server }/get/${itm.id }" width="200" />
						</a>
					</td>
			<c:if test="${ (idx.index+1)%5==0 }">
				</tr>
			</c:if>
		</c:forEach>
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
<%-- "albumsList" --%>

