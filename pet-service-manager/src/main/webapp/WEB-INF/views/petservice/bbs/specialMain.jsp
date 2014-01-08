<%@page import="com.momoplan.pet.commons.IDCreater"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageHeader" >
	<div class="searchBar">
		<form onsubmit="return navTabSearch(this);" action="${ctx }/petservice/bbs/specialMain.html" method="post" id="specialMainForm" >
			<button type="submit">查询</button>
			<button type="button" class="close">关闭</button>
		</form>
	</div>
</div>

<div class="pageContent" id="noteList">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/petservice/bbs/specialAddOrEdit_s.html?&navTabId=panel0203" title="添加专题-单" target="navTab" rel="speciaAddOrEdit_s" >
					<span>添加-单的</span>
				</a>
			</li>
			<li class="line">line</li>
				<a class="add" href="${ctx }/petservice/bbs/specialAddOrEdit_m/<%=IDCreater.uuid() %>.html?&navTabId=panel0203" title="添加专题-多" target="navTab" rel="speciaAddOrEdit_m" >
					<span>添加-组的</span>
				</a>
			<li class="line">line</li>
			<li>
				<a class="delete" href="${ctx }/petservice/bbs/specialDelete.html?id={id}&navTabId=panel0203" target="ajaxTodo" title="确定删除？" warn="请选择"><span>删除</span></a>
			</li>
			<li class="line">line</li>
			<li>
				<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&src=bbs_special_subject&navTabId=panel0203" target="ajaxTodo" title="确定要推送吗？" warn="请选择一个专题"><span>推送</span></a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="115">
		<thead>
			<tr>
				<th width="20"></th>
				<th width="166">图片</th>
				<th>标题</th>
				<th>摘要</th>
				<th width="40">类型</th>
				<th width="40">状态</th>
				<th width="60">创建人</th>
				<th width="150">最后修改时间</th>
				<th width="150">创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.data }" var="itm" varStatus="idx">
			<tr height="35" align="left" target="id" rel="${itm.id }" >
				<td>${idx.index+1 }</td>
				<td>
					<img alt="" src="${pet_file_server }/get/${itm.img }" width="166" height="90"  />
				</td>
				<td>
					<c:choose>
						<c:when test="${ empty itm.gid }">
							<%-- 单的 --%>
							<a href="${ctx }/petservice/bbs/specialAddOrEdit_s.html?id=${itm.id}&navTabId=panel0203" target="navTab" rel="speciaAddOrEdit_s" title="修改-单的">
								${itm.name }
							</a>
						</c:when>
						<c:otherwise>
							<%-- 组的 --%>
							<a href="${ctx }/petservice/bbs/specialAddOrEdit_m/${itm.gid }.html?&navTabId=panel0203" target="navTab" rel="speciaAddOrEdit_m" title="修改-组的">
								${itm.name }
							</a>
						</c:otherwise>
					</c:choose>
					
				</td>
				<td>${itm.summary }</td>
				<td align="center" >
					<c:choose>
						<c:when test="${ empty itm.gid }">
							<font color="blue">单的</font>
						</c:when>
						<c:otherwise>
							<font color="green">组的</font>
						</c:otherwise>
					</c:choose>
				</td>
				<td align="center">${itm.state }</td>
				<td align="center">${itm.cb }</td>
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

	<pet:page form="specialMainForm" pageBean="${page }" pageSize="${page.pageSize }" />

</div>
