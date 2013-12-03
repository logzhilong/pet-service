<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="noticeList">

<div class="panel" defH="600" id="noticeList_panel">
	
	<h1>系统通知</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/notice/noticeAddOrEdit.html" target="dialog" mask="true" title="添加" width="600" height="600" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li><a class="edit" href="${ctx }/petservice/notice/noticeAddOrEdit.html?id={id}" target="dialog" warn="请选择" width="600" height="600" ><span>修改</span></a></li>
				<li class="line">line</li>
				<li>
					<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&src=notice&navTabId=panel0501" target="ajaxTodo" title="确定要推送吗？" warn="请选择"><span>推送</span></a>
				</li>
				<li class="line">line</li>
				
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="50"></th>
						<th>标题</th>
						<th>创建人</th>
						<th>创建日期</th>
						<th>修改人</th>
						<th>修改日期</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${idx.index+1 }</td>
						<td>
							<a class="edit" href="${ctx }/petservice/notice/noticeAddOrEdit.html?id=${itm.id}" target="dialog" warn="请选择" width="600" height="600" >
								${itm.name }>
							</a>
						</td>
						<td>${itm.cb }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td>${itm.eb }</td>
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
<%-- "noticeList_panel" --%>

</div>
<%-- "noticeList" --%>

