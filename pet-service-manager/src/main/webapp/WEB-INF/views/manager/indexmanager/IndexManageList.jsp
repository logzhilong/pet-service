<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader"></div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li>
				<a class="add" href="" target="dialog" max="false" rel="areaCode_add_dialog" mask="true" title="添加" width="450" height="260" >
					<span>刷新</span>
				</a>				
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="25" align="left"></th>
				<th width="130" align="left">模块名称</th>
				<th width="130" align="left">注册名</th>
				<th width="180" align="left">接口名</th>
				<th width="200" align="left">接口功能</th>
				<th width="250" align="left">输入参数</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
				<tr>
					<td align="left">1</td>
					<td align="left">业务模块_用户中心</td>
					<td align="left">service.uri.pet_user</td>
					<td align="left">adminFlushUserPetTypeIndex</td>
					<td align="left">重建用户与宠物类型的索引，此功能仅限管理人员使用，不对客户端开放</td>
					<td align="left">{"uid":"all","pwd":""}</td>
				</tr>
				<tr>
					<td align="left">2</td>
					<td align="left">业务模块_用户中心</td>
					<td align="left">service.uri.pet_user</td>
					<td align="left">adminFlushFriendShipIndex</td>
					<td align="left">更新好友列表索引，此功能仅限管理人员使用，不对客户端开放</td>
					<td align="left">{"method":"adminFlushFriendShipIndex",params:{"pwd":""}}</td>
				</tr>
				<tr>
					<td align="left">3</td>
					<td align="left">业务模块_用户中心</td>
					<td align="left">service.uri.pet_user</td>
					<td align="left">adminFlushSearchUserIndex</td>
					<td align="left">更新搜索用户的索引，用户名+昵称 的索引</td>
					<td align="left">{"method":"adminFlushSearchUserIndex","params":{"uid":"all","pwd":""}}</td>
				</tr>
				<tr>
					<td align="left">4</td>
					<td align="left">业务模块_用户赞</td>
					<td align="left">service.uri.pet_pat</td>
					<td align="left">adminFlushUserPatIndex</td>
					<td align="left">刷新赞的缓存索引，只对服务端开放，不对客户端开放，慎用</td>
					<td align="left">{"pwd":""}</td>
				</tr>
		</tbody>
	</table>
</div>

