<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent" selector="h1" layoutH="2">

<div id="ghostList">

<div class="panel" defH="600" id="ghostList_panel">
	<h1>
		${cuser.name }&nbsp;管理的客户
	</h1>
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/customer/ghostAddOrEdit.html" target="dialog" mask="true" title="添加" width="500" height="600" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/customer/ghostAddOrEdit.html?id={userId}" target="dialog" warn="请选择一个客户" width="500" height="600"><span>修改</span></a>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th width="50" >头像</th>
						<th width="150" >用户名</th>
						<th>昵称</th>
						<th width="150" >创建日期</th>
						<th width="150" >发帖量</th>
						<th width="150" >回帖量</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="userId" rel="${itm.userId }" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<img src="${itm.img }" height="50" width="50" />
						</td>
						<td>
							<a class="edit" href="${ctx }/petservice/customer/ghostAddOrEdit.html?id=${itm.userId }" target="dialog" width="500" height="600">
								${itm.userName }
							</a>
						</td>
						<td>
							<a class="edit" href="${ctx }/petservice/customer/ghostAddOrEdit.html?id=${itm.userId }" target="dialog" width="500" height="600">
								${itm.nickname }
							</a>
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td align="center">
							<font color="blue">
								${itm.totalNote }
							</font>
						</td>
						<td align="center">
							<font color="green">
								${itm.totalReply }
							</font>
						</td>
						
					</tr>
				</c:forEach>
				</tbody>
				
			</table>
		</div>	
	</div>
</div>

</div>
<%-- "ghostList_panel" --%>

</div>
<%-- "ghostList" --%>

