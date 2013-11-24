<chart caption='${title}' showValues='0' baseFontSize='14' animation='0' >
	<#if dataList??>
		<#list dataList as itm>
		    <set label='${itm.channelName?default("N/A")}(${itm.totalCount?default("0")})' value='${itm.totalCount?default("0")}'/>
		</#list>
	</#if>
</chart>