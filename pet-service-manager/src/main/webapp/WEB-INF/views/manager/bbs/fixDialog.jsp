<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent">
	<div class="pageFormContent" layoutH="60">
		<form id="fixAlertForm">
			<input type="hidden" name="eid" value="${eid }"/>
			<input type="hidden" name="aid" value="${aid }"/>
			<textarea name="fixDetail" cols="60" rows="4" ></textarea>
		</form>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="defineAlert.fix();" >确认</button></div></div>
			</li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>