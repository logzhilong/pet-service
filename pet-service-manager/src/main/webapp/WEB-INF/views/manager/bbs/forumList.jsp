<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<form onsubmit="return navTabSearch(this);" method="post" action="${ctx }/manager/bbs/forumList.html">
				<!-- 	
						<select class="combox" name="areaCode" ref="w_combox_city"
							refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
							<option value="all">--请选择国家--</option>
							<c:forEach var="itr" items="${codes }">
								<option value="${itr.id }">${itr.name }</option>
							</c:forEach>
						</select> 
						<select class="combox" name="areaDesc" id="w_combox_city">
							<option value="all">--请选择省份(市)--</option>
						</select>
				-->
						<dt>
							名称:<input type="text" name="name" value="${myForm.name }"/>
							
									<button type="submit">搜索</button>
								
						</dt>
					</form>
				</td>
			</tr>
		</table>
	</div>

</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"
				href="${ctx }/manager/bbs/ToaddOrUpdateForum.html" target="dialog"
				max="false" mask="true" title="添加"
				width="400" height="400" close="forum.refresh"> <span>添加</span>
				</a>
			</li>
			<li>
				<a class="edit" href="${ctx }/manager/bbs/ToaddOrUpdateForum.html?id={id}"
					target="dialog" max="false" mask="true"
					title="修改" width="450" height="350" close="forum.refresh"> <span>修改</span>
			</a></li>
			<li>
				<a class="delete" target="ajaxTodo" title="确定要删除吗?"
					href="${ctx }/manager/bbs/DelForum.html?id={id}"> <span>删除</span>
				</a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="15" align="center"></th>
				<th width="150" align="center">名称</th>
				<th width="200" align="center">描述</th>
				<th width="50" align="center">点击量</th>
				<th width="50" align="center" >回帖量</th>
			<!-- 
				<th width="100" align="center" >地区</th> 
				<th width="200" align="center" >详细地址</th>
			-->
				<th width="150" align="center" >创建时间</th>
				<th width="80" align="center" >创建人</th>
			</tr>
		</thead>
		<tbody id="alertListTbody" >
			<c:forEach items="${pageBean.data }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<td>
						<a  href="${ctx }/manager/bbs/forumManager.html?id=${itm.id }"  rel="jbsxBox3"   target="dialog" max="false" mask="true" title="帖子管理(${itm.name })" width="1000" height="600" close="forum.refresh">
							${itm.name }
						</a>
					</td>
					<td>${itm.descript }</td>
					<td>${itm.clientCount }</td>
					<td>${itm.replyCount }</td>
					<td><fmt:formatDate value="${itm.ct }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${itm.cb }</td>
<%-- 				<td>${itm.areaCode }</td>
					<td>${itm.areaDesc }</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</div>

