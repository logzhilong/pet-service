<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="forumList">

<div class="panel" defH="600" id="forumList_panel">
	<h1>
		父级圈子&nbsp;>&nbsp;${pf.name }
	</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
			
				<li>
					<a class="add" href="${ctx }/petservice/bbs/forumAddOrEdit.html?pid=${pf.id}" target="dialog" mask="true" title="添加圈子" width="420" height="240" ><span>添加圈子</span></a>				
				</li>
				<li class="line">line</li>
				<li><a class="edit" href="${ctx }/petservice/bbs/forumAddOrEdit.html?id={id}&pid=${pf.id}" target="dialog" warn="请选择一个圈子"><span>修改</span></a></li>
				<c:if test="${pf!=null}">
					<li class="line">line</li>
					<li>
						<a href="${ctx }/petservice/bbs/forumMain.html" target="ajax" rel="forumList" >
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
						<th width="100" >类型</th>
						<th>日期</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${itm.seq }</td>
						<td>
							<c:choose>
								<c:when test="${ itm.pid==null }">
									<a href="${ctx }/petservice/bbs/forumMain.html?pid=${itm.id }" target="ajax" rel="forumList" >
										${itm.name }>
									</a>
								</c:when>
								<c:otherwise>
									<a href="${ctx }/petservice/bbs/noteMain.html?forumId=${itm.id}" target="navTab" rel="noteMainTab">
										${itm.name }
									</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${itm.cb }</td>
						<td align="center">${itm.type }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>	
	</div>
</div>

</div>
<%-- "forumList_panel" --%>

</div>
<%-- "forumList" --%>

