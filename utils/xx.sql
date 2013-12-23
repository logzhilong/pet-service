select  r.channel , 
        r.new_user , 
        r.new_register , 
        r.all_register, 
        r.all_user , 
        r.new_pv , 
        r.all_pv ,
        (r.new_register/r.new_user) as new_rate/*今日注册率*/,
        (all_register/all_user) as all_rate/*总注册率*/
from  (
 select
    c0.channel/*渠道*/,
    (select count(id) from biz_imei c1 where c1.channel=c0.channel and c1.cd='2013-11-12' group by c1.channel) as new_user/*今日新增*/,
    (select sum(c2.counter)
      from biz_service_counter c2
      where c2.channel=c0.channel
        and c2.method='register'
        and c2.cd='2013-11-12' ) as new_register/*今日注册*/,
    (select sum(c3.counter)
      from biz_service_counter c3
      where c3.channel=c0.channel
        and c3.method='register' ) as all_register/*总注册数*/,
    (select sum(c4.counter)
      from biz_service_counter c4
      where c4.channel=c0.channel
        and c4.method='firstOpen' ) as all_user/*总用户数*/,
    (select sum(c5.counter)
      from biz_service_counter c5
      where c5.channel=c0.channel
        and c5.cd='2013-11-12' ) as new_pv/*今日PV*/,
    (select sum(c6.counter)
      from biz_service_counter c6
      where c6.channel=c0.channel ) as all_pv/*总PV*/
 from biz_service_counter c0
 group by c0.channel
)as r

