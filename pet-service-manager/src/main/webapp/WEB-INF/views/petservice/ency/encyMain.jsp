<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="encyList">

<div class="panel" defH="600" id="encyList_panel">
	<h1>
		父级&nbsp;>&nbsp;${pf.name }
	</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/ency/encyAddOrEdit.html?pid=${pf.id}" target="dialog" mask="true" title="添加" width="420" height="240" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li><a class="edit" href="${ctx }/petservice/ency/encyAddOrEdit.html?id={id}&pid=${pf.id}" target="dialog" warn="请选择"><span>修改</span></a></li>
				<li>
					<a class="add" href="${ctx }/petservice/push/pushSave.html?id={id}&src=ency&navTabId=panel0402" target="ajaxTodo" title="确定要推送吗？" warn="请选择"><span>推送</span></a>
				</li>
				<li class="line">line</li>
				
				<c:if test="${pf!=null}">
					<li class="line">line</li>
					<li>
						<a href="${ctx }/petservice/ency/encyMain.html" target="ajax" rel="encyList" >
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
						<th>日期</th>
						<th>百科URL</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${itm.seq }</td>
						<td>
							<c:choose>
								<c:when test="${empty itm.pid }">
									<a href="${ctx }/petservice/ency/encyMain.html?pid=${itm.id }" target="ajax" rel="encyList" >
										${itm.name }>
									</a>
								</c:when>
								<c:otherwise>
									${itm.name }>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${itm.cb }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td>${itm.info }</td>
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>	
	</div>
</div>

</div>
<%-- "encyList_panel" --%>

</div>
<%-- "encyList" --%>

