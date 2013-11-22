<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">

<div id="versionList">

<div class="panel" defH="600" id="versionList_panel">
	
	<h1>软件版本</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="edit" href="${ctx }/petservice/version/edit.html?id={id}" target="dialog" warn="请选择" width="500" height="300" rel="channelDictList"><span>修改</span></a>
				</li>
				<li class="line">line</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
					
						<th width="20" ></th>
						<th >终端类型</th>
						<th width="150" >版本</th>
						<th width="150" >修改日期</th>
						<th >IOSURL</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<a class="edit" href="${ctx }/petservice/version/edit.html?id=${itm.id}" target="dialog" width="500" height="300" rel="versionList">
								${itm.phoneType }
							</a>
						</td>
						<td>
							<a class="edit" href="${ctx }/petservice/version/edit.html?id=${itm.id}" target="dialog" width="500" height="300" rel="versionList">
								${itm.petVersion }
							</a>
						</td>
						<td align="center">
							<fmt:formatDate value="${itm.createDate }" type="both" />
						</td>
						<td>${itm.iosurl }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>	
	</div>
</div>



</div>
</div>

