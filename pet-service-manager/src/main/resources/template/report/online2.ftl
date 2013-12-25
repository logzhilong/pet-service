select c2.cd, c2.channel as channel,'' as service,'' as method , count(c2.token) as totalCount 
from biz_token c2
where c2.channel='${channel}'
<#if cd??>
	and c2.cd like '${cd}%' 
</#if> 
group by c2.cd 
order by c2.cd desc