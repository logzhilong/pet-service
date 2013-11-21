<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<%--
<div class="pageHeader" >
	<div class="searchBar">
			按总数排序:
			<select name="sort" >
				<option value="desc">从大到小</option>
				<option value="asc">从小到大</option>
			</select>
			-
			<button type="button" onclick="alert('开发中...');" >查询</button>
			<button type="button" class="close">关闭</button>
	</div>
</div>
--%>

<div class="pageContent" selector="h1" layoutH="2">
<div>

	<div class="accountInfo">
		<p>
			按总数排序:
			<select name="sort" >
				<option value="desc">从大到小</option>
				<option value="asc">从小到大</option>
			</select>
			-
			<button type="button" onclick="alert('开发中...');" >查询</button>
		</p>
	</div>

	<div>
		<table width="100%" class="list" border="0" style="background-color: white;">
			<thead>
				<tr>
					<th width="20" ></th>
					<th>渠道(渠道号)</th>
					<th>今日新增</th>
					<th>今日注册</th>
					<th>今日注册率</th>
					<th>总用户数</th>
					<th>总注册数</th>
					<th>总注册率</th>
					<th>今日PV</th>
					<th>总PV</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.data }" var="itm" varStatus="idx">
				<tr height="30" align="left" >
					<td align="center">${idx.index+1 }</td>
					<td>${itm.channel }(${itm.channelName })</td>
					<td>${itm.new_user }</td>
					<td>${itm.new_register }</td>
					<td>${itm.new_rate }</td>
					<td>${itm.all_user }</td>
					<td>${itm.all_register }</td>
					<td>${itm.all_rate }</td>
					<td>${itm.new_pv }</td>
					<td>${itm.all_pv }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>


</div>
</div>

