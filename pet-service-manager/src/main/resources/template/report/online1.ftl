select c2.channel as channel,'' as service,'' as method , count(c2.token) as totalCount 
from biz_token c2
<#if cd??>
	where c2.cd='${cd}' 
</#if> 
group by c2.channel