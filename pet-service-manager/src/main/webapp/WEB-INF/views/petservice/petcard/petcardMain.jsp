<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="pageHeader" >
	<div class="searchBar">
		<form onsubmit="return navTabSearch(this);" action="${ctx }/petservice/petcard/petcardMain.html" method="post" id="petcardMainForm" >
			状态:
			<select name="state" >
				<option value="">全部</option>
				<option value="NEW" <c:if test="${ myForm.state eq 'NEW' }">selected="selected"</c:if> >已绑定</option>
				<option value="PENDING" <c:if test="${ myForm.state eq 'PENDING' }">selected="selected"</c:if> >未绑定</option>
			</select>
			<button type="button">查询</button>
			<button type="button" class="close">关闭</button>
		</form>
	</div>
</div>

	
<div class="pageContent" selector="h1" layoutH="2">

		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/petcard/petcardAddOrEdit.html" target="dialog" mask="true" title="创建二维码" width="300" height="250" ><span>创建二维码</span></a>				
				</li>
				<li class="line">line</li>
			</ul>
		</div>

	
		<table class="table" width="100%" layoutH="115" >
			<thead>
				<tr>
					<th width="50"></th>
					<th width="100">二维码</th>
					<th>ID</th>
					<th>绑定状态</th>
					<th>最近修改日期</th>
					<th>创建日期</th>
					<th>创建人</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.data }" var="itm" varStatus="idx">
				<tr height="30" align="left" target="id" rel="${itm.id }" >
					<td>${idx.index+1 }</td>
					<td height="100" >
						<img src="${pet_file_server }/get/${itm.id }" width="100" />
					</td>
					<td>${itm.id }</td>
					<td align="center">
						<c:choose>
							<c:when test="${ itm.userId == null }">
								未绑定
							</c:when>
							<c:otherwise>
								<font color="red">已绑定</font>
							</c:otherwise>
						</c:choose>
					</td>
					<td align="center">
						<fmt:formatDate value="${itm.et }" type="both" />
					</td>
					<td align="center">
						<fmt:formatDate value="${itm.ct }" type="both" />
					</td>
					<td>${itm.cb }</td>
				</tr>
			</c:forEach>
			</tbody>
			
		</table>
		<pet:page form="petcardMainForm" pageBean="${page }" pageSize="${page.pageSize }" />
</div>
