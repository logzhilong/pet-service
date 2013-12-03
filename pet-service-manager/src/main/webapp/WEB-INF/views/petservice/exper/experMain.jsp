<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="experList">

<div class="panel" defH="600" id="experList_panel">
	<h1>
		父级&nbsp;>&nbsp;${pf.name }
	</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/exper/experAddOrEdit.html?pid=${pf.id}" target="dialog" mask="true" title="添加" width="600" height="600" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li><a class="edit" href="${ctx }/petservice/exper/experAddOrEdit.html?id={id}&pid=${pf.id}" target="dialog" warn="请选择" width="600" height="600" ><span>修改</span></a></li>
				<li>
					<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&src=exper&navTabId=panel0403" target="ajaxTodo" title="确定要推送吗？" warn="请选择"><span>推送</span></a>
				</li>
				<li class="line">line</li>
				
				<c:if test="${pf!=null}">
					<li class="line">line</li>
					<li>
						<a href="${ctx }/petservice/exper/experMain.html" target="ajax" rel="experList" >
							<span>&lt;返回上级</span>
						</a>
					</li>
				</c:if>
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
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${itm.seq }</td>
						<td>
							<c:choose>
								<c:when test="${empty itm.pid }">
									<a href="${ctx }/petservice/exper/experMain.html?pid=${itm.id }" target="ajax" rel="experList" width="500" height="600"  >
										${itm.name }>
									</a>
								</c:when>
								<c:otherwise>
									<a class="edit" href="${ctx }/petservice/exper/experAddOrEdit.html?id=${itm.id}&pid=${pf.id}" target="dialog" warn="请选择" width="600" height="600" >
										${itm.name }>
									</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${itm.cb }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td>
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
<%-- "experList_panel" --%>

</div>
<%-- "experList" --%>

