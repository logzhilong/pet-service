<chart caption='${title}' showValues='0' baseFontSize='14' animation='0' >
	<#if dataList??>
		<#list dataList as itm>
		    <set label='${itm.alias}(${itm.totalCount} 次)' value='${itm.totalCount}'/>
		</#list>
	</#if>
</chart>