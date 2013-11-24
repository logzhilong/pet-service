select c2.cd, c2.channel as channel,'' as service,'' as method , sum(c2.counter) as totalCount /*pv*/
from biz_service_counter c2
where c2.channel='${channel}'
<#if cd??>
	and c2.cd like '${cd}%' 
</#if> 
group by c2.cd 
order by c2.cd desc