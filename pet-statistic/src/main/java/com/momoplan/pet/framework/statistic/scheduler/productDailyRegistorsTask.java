package com.momoplan.pet.framework.statistic.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.framework.statistic.service.StatisticService;

public class productDailyRegistorsTask {
	@Autowired
	StatisticService statisticService = null;
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(productDailyRegistorsTask.class);
    
    /**
     * 产生当天的用户注册量
     * @throws Exception 
     */
    public void productDailyRegistors(){
    	logger.info("this is productDailyRegistors...");
    	try {
			statisticService.productDailyRegistorsTask();
		} catch (Exception e) {
			logger.debug(" producting DailyRegistorsTask error ");
			e.printStackTrace();
		}
    }
}
