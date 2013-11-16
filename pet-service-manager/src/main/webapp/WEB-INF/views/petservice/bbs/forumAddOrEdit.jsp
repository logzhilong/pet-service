<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/bbs/forumSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="pid" value="${myForm.pid }" />
	
		<div class="pageFormContent" layoutH="58">
			
			<c:if test="${not empty myForm.pid}">
				<div class="unit">
					<label>父圈：</label>
					<input type="text" name="pname" size="30" value="${myForm.pname }" readonly="readonly" />
				</div>
			</c:if>
			
			<div class="unit">
				<label>序号：</label>
				<input type="text" name="seq" size="30" value="${myForm.seq }" />
			</div>
			
			<c:if test="${not empty myForm.pid}">
				<div class="unit">
					<label>类型：</label>
					<select class="combox"  ref="fforum" refUrl="${ctx }/manager/bbs/Toaddforum.html?fid={value}">
						<option value="all">--请选择--</option>
						<c:forEach var="itr" items="${xmllist }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select>
					<select class="combox" name="type" id="fforum" ref="fd">
						<option value="">--请选择--</option>
					</select>
				</div>
			</c:if>
			
			<div class="unit">
				<label>名称：</label>
				<input type="text" name="name" size="30" value="${myForm.name }" class="required"/>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
