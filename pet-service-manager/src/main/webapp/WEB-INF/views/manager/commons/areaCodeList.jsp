<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<form method="post" id="areaCodeAddForm" action="${ctx }/manager/commons/areaCodeSearch.html?fuid={father},grandsunid={grandsunid}" >
					<select class="combox" name="father" ref="w_combox_city" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
						<option value="">--请选择国家--</option>
						<c:forEach var="itr" items="${codes }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select> 
					<select class="combox" name="grandsunid" id="w_combox_city">
						<option value="">--请选择省份(市)--</option>
					</select> 
					
<%-- 					日期：<input id="queryDate" type="text" class="date" readonly="true" dateFmt="yyyy-MM-dd" value="${queryDate }" /> --%>
					<div class="buttonActive"> 
						<div class="buttonContent">	<button type="submit">搜索</button> 
					</div> 
				</div>
				</form>
				</td>
<!-- 				<td> -->
<!-- 					<div class="buttonActive"><div class="buttonContent"><button type="button"  onclick="defineAlert.queryAlertsByDate( $('#queryDate').val() )" >检索</button></div></div> -->
<!-- 				</td> -->
				
				
			</tr>
		</table>
	</div>

</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" href="${ctx }/manager/commons/areaCodeAdd.html" target="dialog" max="false" rel="areaCode_add_dialog" mask="true" title="添加" width="450" height="260" close="forum.refresh" >
					<span>添加</span>
				</a>				
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/commons/areaCodeAdd.html?id={id}" target="dialog" max="false" rel="areaCode_update_dialog" mask="true" title="修改" width="450" height="260" close="forum.refresh" >
					<span>修改</span>
				</a>				
			</li>

<!-- 			<li> -->
<%-- 				<a class="edit" href="${ctx }/manager/commons/areaCodeAdd.html?id={id}" target="dialog" max="false" rel="alert_dialog" mask="true" title="修改预警" width="450" height="260"  close="defineAlert.refresh" param="'${id }'"> --%>
<!-- 					<span>修改</span> -->
<!-- 				</a> -->
<!-- 			</li> -->
<!-- 			<li> -->
<%-- 				<a class="delete" onclick="defineAlert.deleteAlert('checkedAlert')" href="${ctx }/manager/commons/areaCodeDel.html?id={id}" param="'${id }'" title="确定要删除吗?" > --%>
<!-- 					<span>删除</span> -->
<!-- 				</a> -->
<!-- 			</li> -->
						<li>
				<a class="delete" href="${ctx }/manager/commons/areaCodeDel.html?id={id}">
					<span>删除</span>
				</a>				
			</li>
			
			<li class="line">line</li>
			
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="15" align="center"></th>
				<th width="200" align="center">父名称</th>
				<th width="200" align="center" >名称</th>
			</tr>
		</thead>
		<tbody id="areaCode_tbody" >
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>${itm.pname }</td>
					<td>${itm.name }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
		<tr>
			<td>当前页:${pageBean.pageNo}　</td>
			<a href="${ctx }/manager/commons/areaCodeList.html">上一页</a>　<a href="${ctx }/manager/commons/areaCodeList.html">下一页</a>
			<td>　共:${pageBean.totalPage}页
			<form method="post" id="areaCodeAddForm" action="${ctx }/manager/commons/areaCodeSearch.html?pageNo={topageNo}" >
			跳转到 <input type="text" style="width: 25px;" name="topageNo"/>页<input type="submit" value="跳转"/>
			</form>
      <input type="text" name="pageNo" value="${pageBean.pageNo}" />
      <input type="text" name="pageSize" value="${pageBean.pageSize}" />
    </td>
		</tr>
	
		
</div>

