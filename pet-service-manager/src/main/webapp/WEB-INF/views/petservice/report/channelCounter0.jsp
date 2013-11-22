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
					<th>
						<a href="#">
							今日新增(&nbsp;<font color='red'>${all.new_user }</font>&nbsp;)
						</a>
					</th>
					<th>
						<a href="#">
							今日注册(&nbsp;<font color='red'>${all.new_register }</font>&nbsp;)
						</a>				
					</th>
					<th>
						<a href="#">
							今日注册率(&nbsp;<font color='red'>${(all.new_register/all.new_user)*100}%</font>&nbsp;)
						</a>					
					</th>
					<th>
						<a href="#">
							总用户数(&nbsp;<font color='red'>${all.all_user }</font>&nbsp;)
						</a>					
					</th>
					<th>
						<a href="#">
							总注册数(&nbsp;<font color='red'>${all.all_register }</font>&nbsp;)
						</a>					
					</th>
					<th>
						<a href="#">
							总注册率(&nbsp;<font color='red'><fmt:formatNumber pattern="##.##" value="${(all.all_register/all.all_user)*100 }" />%</font>&nbsp;)
						</a>					
					</th>
					<th>
						<a href="#">
							今日PV(&nbsp;<font color='red'>${all.new_pv }</font>&nbsp;)
						</a>					
					</th>
					<th>
						<a href="#">
							总PV(&nbsp;<font color='red'>${all.all_pv }</font>&nbsp;)
						</a>					
					</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.data }" var="itm" varStatus="idx">
				<tr height="30" align="left" >
					<td align="center">${idx.index+1 }</td>
					<td>${itm.channelName }(${itm.channel })</td>
					<td>${itm.new_user }</td>
					<td>${itm.new_register }</td>
					<td>
						<fmt:formatNumber pattern="##.##" value="${itm.new_rate*100 }" />%
					</td>
					<td>${itm.all_user }</td>
					<td>${itm.all_register }</td>
					<td>
						<fmt:formatNumber pattern="##.##" value="${itm.all_rate*100 }" />%
					</td>
					<td>${itm.new_pv }</td>
					<td>${itm.all_pv }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>


</div>
</div>

