<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="assortList">

<div class="panel" defH="600" id="assortList_panel">
	<h1>帖子分类管理</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/bbs/assortAddOrEdit.html" target="dialog" mask="true" title="添加分类" width="440" height="520" ><span>添加</span></a>	
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/bbs/assortAddOrEdit.html?id={id}" target="dialog" warn="请选择一个分类" width="440" height="520"><span>修改</span></a>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="50">序号</th>
						<th>名称</th>
						<th>创建人</th>
						<th>创建日期</th>
						<th>修改日期</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${itm.seq }</td>
						<td>
							<a href="${ctx }/petservice/bbs/assortAddOrEdit.html?id=${itm.id}" target="dialog" width="440" height="520" >
								${itm.name }
							</a>
						</td>
						<td>${itm.cb }</td>
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

</div>
<%-- "assortList_panel" --%>

</div>
<%-- "assortList" --%>

