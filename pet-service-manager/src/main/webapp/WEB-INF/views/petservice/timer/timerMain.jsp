<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="timerList">

<div class="panel" defH="600" id="timerList_panel">
	
	<h1>计划任务</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="edit" href="${ctx }/petservice/timer/timerAddOrEdit.html?state=new&id={id}" target="dialog" warn="请选择" width="550" height="200" ><span>调整</span></a>
				</li>
				<li class="line">line</li>
				<li>
					<a class="delete" href="${ctx }/petservice/timer/timerSave.html?state=cancel&id={id}" target="ajaxTodo" title="确定撤销此计划？" warn="请选择"><span>撤销</span></a>
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/timer/timerSave.html?state=active&id={id}" target="ajaxTodo" title="确定激活此计划？" warn="请选择"><span>激活</span></a>
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
						<th>类型</th>
						<th>状态</th>
						<th>计划执行时间</th>
						<th>修改日期</th>
						<th>创建日期</th>
						<th>创建人</th>
						<th>修改人</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${idx.index+1 }</td>
						<td>${itm.name }</td>
						<td>
							<c:choose>
								<c:when test="${ 'mgr_push' eq itm.src }">
									<c:set var="src_cn" value="定时推送" />
									<c:set var="src_color" value="green" />
								</c:when>
								<c:otherwise>
									<c:set var="src_cn" value="定时发帖" />
									<c:set var="src_color" value="blue" />
								</c:otherwise>
							</c:choose>
							<font color="${src_color }">${src_cn }</font>
						</td>
						
						<td>${itm.state }</td>
						<td align="center">
							<font color="red">
								<fmt:formatDate value="${itm.at }" type="both" />
							</font>
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.et }" type="both" />
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td>${itm.cb }</td>
						<td>${itm.eb }</td>
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>
	</div>
</div>

</div>
<%-- "timerList_panel" --%>

</div>
<%-- "timerList" --%>

