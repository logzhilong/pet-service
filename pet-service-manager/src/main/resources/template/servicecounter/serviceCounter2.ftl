<chart caption='${title}' showvalues='0' numberPrefix='' palette='1' baseFontSize='14' animation='0' xAxisName='日期' yAxisName='总数'>
	<#if dataList??>
		<#list dataList as itm>
		    <set label='${itm.cd}' value='${itm.totalCount}'/>
		</#list>
	</#if>
</chart>