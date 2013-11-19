-- service,method,

select service,method,sum(counter) as counter 
from biz_service_counter 
group by service,method 
order by method;


