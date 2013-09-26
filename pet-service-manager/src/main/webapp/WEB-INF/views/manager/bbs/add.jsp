<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<form method="post" id="addAlertForm" >
<%-- 无论什么时候都会有 eid 出现 --%>
<input type="hidden" name="eid" value="${eid }" />
<%-- 编辑时，会有 aid 出现，提交后做更新操作 --%>
<input type="hidden" name="id" value="${id }" />
<%-- 预警的类型分为 资源预警 和 资源类型预警；0 按资源 --%>
<input type="hidden" name="type" value="0" />
<%-- 
	DEFAULT 0  暂时无用，当 frequency_type=2 时，会定义一个counter，那时才会用到此值 
	这样也确保了 trigger 每次都会出现
--%>
<input type="hidden" name="faceAlertTrigger[0].frequency" value="0" />
<input type="hidden" name="faceAlertTrigger[0].id" value="${definition.faceAlertTrigger[0].id }" />

<%--
	动作：edit、view
--%>
<input type="hidden" name="action" value="${action }" />

<%-- 是视图操作，那么所有内容都是只读的 --%>
<c:if test="${action eq 'view' }">
	<c:set var="readonly" value="readonly='readonly'" />
	<c:set var="disabled" value="disabled='disabled'" />
</c:if>

<div class="pageContent">

	<div class="pageFormContent" layoutH="60">
		<fieldset>
			<legend>基本信息</legend>
			<dl>
				<dt>优先级：</dt>
				<dd>
					
					<select class="combox" name="priority" >
						<option value="3" <c:if test="${definition.priority==3}">selected="selected"</c:if> >!!!-高</option>
						<option value="2" <c:if test="${empty definition.priority || definition.priority==2}">selected="selected"</c:if> >!!-中</option>
						<option value="1" <c:if test="${definition.priority==1}">selected="selected"</c:if> >!-低</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>有效：</dt>
				<dd>
					<select class="combox" name="active" ${disabled }>
						<option value="true" <c:if test="${ ( empty definition.active ) || definition.active }">selected="selected"</c:if> >-Yes-</option>
						<option value="false" <c:if test="${ (not empty definition.active ) && (not definition.active) }">selected="selected"</c:if> >-No-</option>
					</select>
				</dd>
			</dl>
			
			
			<dl class="nowrap">
				<dt>名称：</dt>
				<dd><input name="name" type="text" size="60" value="${definition.name }" ${readonly }/></dd>
			</dl>
			<dl class="nowrap">
				<dt>描述：</dt>
				<dd>
					<textarea name="description" cols="80" rows="2" ${readonly }>${definition.description }</textarea>
				</dd>
			</dl>
		</fieldset>
		
		<fieldset>
		<legend>预警条件</legend>
		<c:choose>
			<c:when test="${action eq 'view' }">
				<dl class="nowrap">
					<dt>条件:</dt>
					<dd>
						<input type="text" size="60" value="${definition.faceAlertCondition[0].name } ${definition.faceAlertCondition[0].comparator } ${definition.faceAlertCondition[0].threshold } " ${readonly }/>
					</dd>
				</dl>
			</c:when>
			<c:otherwise>
				<dl class="nowrap">
					<dt>
						<label><input type="radio" value="onMetric" checked="checked" />度量:</label>
					</dt>
					<dd>
						<input type="hidden" name="faceAlertCondition[0].type" value="0" /><!-- absolute = 0 -->
						
						<script type="text/javascript">
						var metricMap = {};
						function metricChange(mid){
							$("#_metricName").val(metricMap[mid]);
						}
						</script>
						<%-- TODO : 这个值得从后台取出 --%>
						<select class="combox" name="faceAlertCondition[0].mid" onchange="metricChange(this.value)">
							<option value="-1" >Select...</option>
							<c:forEach items="${metrics }" var="metric" >
								<option value="${metric.id }" <c:if test="${metric.id == definition.faceAlertCondition[0].mid }">selected="selected"</c:if> >${metric.template.name }</option>
								<script>
									metricMap[${metric.id}] = '${metric.template.name }';
									<c:if test="${metric.id == faceAlertCondition[0].mid }">
										metricChange(${metric.id});
									</c:if>
								</script>
							</c:forEach>
						</select>
						<input type="hidden" id="_metricName" name="faceAlertCondition[0].name" value="" />
						
						<select class="combox" name="faceAlertCondition[0].comparator" >
							<option value="gt" <c:if test="${definition.faceAlertCondition[0].comparator eq 'gt' }">selected="selected"</c:if> > 	大于 (Greater than)</option>
							<option value="eq" <c:if test="${definition.faceAlertCondition[0].comparator eq 'eq' }">selected="selected"</c:if> >		等于 (Equal to) </option>
							<option value="lt" <c:if test="${definition.faceAlertCondition[0].comparator eq 'lt' }">selected="selected"</c:if> > 	小于 (Less than)</option>
							<option value="noteq" <c:if test="${definition.faceAlertCondition[0].comparator eq 'noteq' }">selected="selected"</c:if> > 不等于 (Not Equal to)</option>
						</select>
						<input type="text" name="faceAlertCondition[0].thresholdValue" maxlength="15" size="8" value="${definition.faceAlertCondition[0].threshold }" ${readonly } />
						(请带上单位)
					</dd>
				</dl>
				
			</c:otherwise>
		</c:choose>
		</fieldset>
		
		<fieldset>
			<legend>提醒方式</legend>
			
			
			<dl class="nowrap">
				<dt>邮件：</dt>
				<dd>
					<textarea name="faceAlertAction[0].actionParam" cols="80" rows="2" ${readonly } >${emails }</textarea>(","分割)
					<input type="hidden" name="faceAlertAction[0].actionType" value="EMAIL" />
				</dd>
			</dl>
			
			<dl class="nowrap">
				<dt>短信：</dt>
				<dd>
					<textarea name="faceAlertAction[1].actionParam" cols="80" rows="2" ${readonly } >${smss }</textarea>(","分割)
					<input type="hidden" name="faceAlertAction[1].actionType" value="SMS" />
				</dd>
			</dl>
			
			<c:if test="${not ( action eq 'view' ) }">
				<dl class="nowrap">
					<dt>启用操作：</dt>
					<dd>
						<label style="width: 400px;"><input type="radio" name="frequencyType" value="0" checked="checked" />每次满足条件</label>(其他方式开发中...)
					</dd>
				</dl>
				
				<dl class="nowrap">
					<dt>&nbsp;</dt>
					<dd>
						<label style="width: 400px;"><input type="checkbox" name="willRecover" value="true" checked="checked" />生成一个警报,然后禁用警报定义直到被修复</label>
					</dd>
				</dl>
			</c:if>
		</fieldset>
	</div>

	<div class="formBar">
		<ul>
			<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
			<c:choose>
				<c:when test="${action eq 'view' }">
					<li>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="defineAlert.gotoEdit();" >编辑</button></div></div>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="defineAlert.addSubmit();" >保存</button></div></div>
					</li>
				</c:otherwise>
			</c:choose>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>
</form>
