<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/alerts/add.html?eid=${eid}" target="dialog" max="false" rel="alert_dialog" mask="true" title="添加预警" width="800" height="550" close="defineAlert.refresh" param="'${eid }'" >
					<span>添加</span>
				</a>
			</li>
			<li>
				<a class="edit" href="${ctx }/alerts/viewOrEdit.html?eid=${eid}&aid={aid}&action=edit" target="dialog" max="false" rel="alert_dialog" mask="true" title="修改预警" width="800" height="550" close="defineAlert.refresh" param="'${eid }'" >
					<span>修改</span>
				</a>
			</li>
			<li>
				<a class="delete" onclick="defineAlert.deleteAlert('checkedAlert')" href="javascript:void" title="确定要删除吗?" >
					<span>删除</span>
				</a>
			</li>
			
			<li class="line">line</li>
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="120">
		<thead>
			<tr>
				<th width="20">
					<input type="checkbox" id="checkedAll" onclick="defineAlert.checkAll('checkedAll','checkedAlert');" />
				</th>
				<th width="200">名称</th>
				<th width="350">描述</th>
				<th width="100" align="center" >创建时间</th>
				<th width="100" align="center" >修改时间</th>
				<th width="80" align="center" >有效（Active）</th>
				<th width="80" align="center" >启用（Enable）</th>
			</tr>
		</thead>
		<tbody id="alertDefineTbody" >
			<c:forEach items="${pageBean.data }" var="itm" >
				<tr target="aid" rel="${itm.id }">
		 			<td width="20">
						<input type="checkbox" name="checkedAlert" id="checkedAlert_${itm.id }" value="${itm.id }" />
					</td>
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
						<a href="${ctx }/alerts/viewOrEdit.html?eid=${eid}&aid=${itm.id }&action=view" target="dialog" max="false" rel="alert_dialog" mask="true" title="查看/编辑" width="800" height="550" close="defineAlert.refresh" param="'${eid }'" >
							${itm.name }
						</a>
					</td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
					
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >${itm.description }</td>
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
						<fmt:formatNumber value="${itm.ctime/1000 }" type="NUMBER" pattern="0000000000.000" var="ctime" />
						${ctime }
					</td>
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
						<fmt:formatNumber value="${itm.mtime/1000 }" type="NUMBER" pattern="0000000000.000" var="mtime" />
						${mtime }
					</td>
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
						<c:choose>
							<c:when test="${itm.active }">
								<c:out value="是" />
							</c:when>
							<c:otherwise>
								<c:out value="否" />
							</c:otherwise>
						</c:choose>
					</td>
					<td onclick="defineAlert.checkRow('checkedAlert_${itm.id }');" >
						<c:choose>
							<c:when test="${itm.enabled }">
								<c:out value="是" />
							</c:when>
							<c:otherwise>
								<c:out value="否" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close" style="width: 150px;">&nbsp;关&nbsp;闭&nbsp;窗&nbsp;口&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;</div></div>
			</li>
		</ul>
	</div>
		
</div>
