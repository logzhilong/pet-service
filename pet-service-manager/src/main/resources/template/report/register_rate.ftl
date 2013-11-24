select  r.channel as channel,'' as service,'' as method , (r.new_register/r.new_user) as totalCount /*今日注册率*/
from  (
 select
    c0.channel/*渠道*/,
    (select sum(c1.counter)
      from biz_service_counter c1
      where c1.channel=c0.channel
        <#if min??>
	        and ( c1.cd>='${min}' and c1.cd<='${max}' )
        </#if> 
        and c1.method='firstOpen') as new_user/*新增*/,
    (select sum(c2.counter)
      from biz_service_counter c2
      where c2.channel=c0.channel
        <#if min??>
        	and ( c2.cd>='${min}' and c2.cd<='${max}' )
        </#if> 
        and c2.method='register') as new_register/*注册*/
 from biz_service_counter c0
 group by c0.channel
)as r