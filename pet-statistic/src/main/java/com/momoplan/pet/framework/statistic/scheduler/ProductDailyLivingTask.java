package com.momoplan.pet.framework.statistic.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.framework.statistic.service.StatisticService;

public class ProductDailyLivingTask {
	@Autowired
	StatisticService statisticService = null;
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(ProductDailyLivingTask.class);
    
    /**
     * 产生当天的用户活跃量
     */
    public void productDailyLiving() {
    	logger.info("this is productDailyLiving...");
    	try {
			statisticService.productDailyLivingTask();
		} catch (Exception e) {
			logger.debug(" producting DailyLivingTask error ");
			e.printStackTrace();
		}
    }
}
