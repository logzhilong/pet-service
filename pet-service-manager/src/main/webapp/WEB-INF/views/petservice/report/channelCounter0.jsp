<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%@page import="com.momoplan.pet.commons.DateUtils" %>

<form id="pagerForm" method="post" action="#rel#" >
	<input type="hidden" name="orderField" value="${myForm.orderField}" />
	<input type="hidden" name="orderDirection" value="${myForm.orderDirection}" />
</form>

<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx }/petservice/report/channelCounter0.html" method="post">

</form>

<div class="pageContent" selector="h1" layoutH="2">
<div>

	<div class="accountInfo">
		<p>
			<font color="red">
				当前日期：<%=DateUtils.formatDate(DateUtils.now()) %>
				&nbsp;&nbsp;&nbsp;&nbsp;
				统计日期：${cd }
			</font>
		</p>
		<br>
		排序:
		<select name="sort" >
			<option value="desc">从大到小</option>
			<option value="asc">从小到大</option>
		</select>
		-
		<button type="button" onclick="alert('开发中...');" >查询</button>
		
	</div>
	<div>
		<table width="100%" class="list" border="0" style="background-color: white;">
			<thead>
				<tr>
					<th width="20" ></th>
					<th>渠道(渠道号)</th>
					
					<th title="统计日期：${cd }" >
						<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=service.uri.pet_sso__firstOpen&cd=${cd}" title="今日新增-统计" target="navTab" rel="serviceCounter1Tab">
							今日新增(&nbsp;<font color='red'>${all.new_user }</font>&nbsp;)
						</a>
					</th>
					
					<th title="统计日期：${cd }">
						<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=service.uri.pet_sso__register&cd=${cd}" title="今日注册-统计" target="navTab" rel="serviceCounter1Tab">
							今日注册(&nbsp;<font color='red'>${all.new_register }</font>&nbsp;)
						</a>
					</th>
					
					<th title="今日注册率-统计(${cd })" >
						<%--
						<a href="${ctx }/petservice/report/register_rate.html?cd=${cd}" title="今日注册率-统计" target="navTab" rel="serviceCounter1Tab">
						--%>
						今日注册率
					</th>
					
					<th>
						<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=service.uri.pet_sso__firstOpen" title="总用户数-统计" target="navTab" rel="serviceCounter1Tab">
							总用户数(&nbsp;<font color='red'>${all.all_user }</font>&nbsp;)
						</a>
					</th>
					
					<th>
						<a href="${ctx }/petservice/report/serviceCounter1.html?serviceMethod=service.uri.pet_sso__register" title="总注册数-统计" target="navTab" rel="serviceCounter1Tab">
							总注册数(&nbsp;<font color='red'>${all.all_register }</font>&nbsp;)
						</a>
					</th>
					<th title="总注册率-统计(${cd })" >
						<%--
						<a href="${ctx }/petservice/report/register_rate.html" title="总注册率-统计" target="navTab" rel="serviceCounter1Tab">
						--%>
						总注册率
					</th>
					
					<th title="统计日期：${cd }">
						<a href="${ctx }/petservice/report/online1.html?cd=${cd}" title="今日在线-统计" target="navTab" rel="serviceCounter1Tab">
							今日在线(&nbsp;<font color='red'>${all.new_online }</font>&nbsp;)
						</a>
					</th>
					<th>
						<a href="${ctx }/petservice/report/online1.html" title="总在线-统计" target="navTab" rel="serviceCounter1Tab">
							总在线(&nbsp;<font color='red'>${all.all_online }</font>&nbsp;)
						</a>
					</th>
					
					<th title="统计日期：${cd }">
						<a href="${ctx }/petservice/report/pv1.html?cd=${cd}" title="今日PV-统计" target="navTab" rel="serviceCounter1Tab">
							今日PV(&nbsp;<font color='red'>${all.new_pv }</font>&nbsp;)
						</a>
					</th>
					<th>
						<a href="${ctx }/petservice/report/pv1.html" title="总PV-统计" target="navTab" rel="serviceCounter1Tab">
							总PV(&nbsp;<font color='red'>${all.all_pv }</font>&nbsp;)
						</a>
					</th>
					
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.data }" var="itm" varStatus="idx">
				<tr height="40" align="left" >
					<td align="center">${idx.index+1 }</td>
					<td>${itm.channelName }(${itm.channel })</td>
					
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/serviceCounter2.html?serviceMethod=service.uri.pet_sso__firstOpen__${itm.channel}" title="${itm.channelName }渠道-新增" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.new_user }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.new_user }
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/serviceCounter2.html?serviceMethod=service.uri.pet_sso__register__${itm.channel}" title="${itm.channelName }渠道-注册" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.new_register }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.new_register }
							</c:otherwise>
						</c:choose>
					</td>
					<td title="统计日期：${cd }">
						<fmt:formatNumber pattern="##.##" value="${itm.new_rate*100 }" />%
					</td>
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/serviceCounter2.html?serviceMethod=service.uri.pet_sso__firstOpen__${itm.channel}" title="${itm.channelName }渠道-新增" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.all_user }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.all_user }
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/serviceCounter2.html?serviceMethod=service.uri.pet_sso__register__${itm.channel}" title="${itm.channelName }渠道-注册" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.all_register }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.all_register }
							</c:otherwise>
						</c:choose>

					</td>
					<td title="总注册率">
						<fmt:formatNumber pattern="##.##" value="${itm.all_rate*100 }" />%
					</td>
					
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/online2.html?channel=${itm.channel}" title="${itm.channelName }渠道-在线" target="navTab" rel="serviceCounter2Tab_${itm.channel}">						
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.new_online }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.new_online }
							</c:otherwise>
						</c:choose>

					</td>
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/online2.html?channel=${itm.channel}" title="${itm.channelName }渠道-在线" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.all_online }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.all_online }
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/pv2.html?channel=${itm.channel}" title="${itm.channelName }渠道-PV" target="navTab" rel="serviceCounter2Tab_${itm.channel}">						
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.new_pv }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.new_pv }
							</c:otherwise>
						</c:choose>

					</td>
					<td>
						<c:choose>
							<c:when test="${itm.reg }">
								<a href="${ctx }/petservice/report/pv2.html?channel=${itm.channel}" title="${itm.channelName }渠道-PV" target="navTab" rel="serviceCounter2Tab_${itm.channel}">
								<img alt="" src="${ctx }/static/images/4.0/icons/search.png" width="15" >
								<font color="#000000">
									${itm.all_pv }
								</font></a>
							</c:when>
							<c:otherwise>
								${itm.all_pv }
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>


</div>
</div>

