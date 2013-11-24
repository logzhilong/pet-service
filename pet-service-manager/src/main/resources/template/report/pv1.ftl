select c2.channel as channel,'' as service,'' as method , sum(c2.counter) as totalCount /*pv*/
from biz_service_counter c2
<#if min??>
	where c2.cd>='${min}' and c2.cd<='${max}' 
</#if> 
group by c2.channel
