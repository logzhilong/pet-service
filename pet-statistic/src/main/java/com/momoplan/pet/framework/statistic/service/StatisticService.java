package com.momoplan.pet.framework.statistic.service;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;

import com.momoplan.pet.commons.domain.statistic.po.DataUsers0;
import com.momoplan.pet.framework.statistic.vo.ClientRequest;

public interface StatisticService {

//	public void testInsert() throws Exception ;
	
	public DataUsers0 findDevice(ClientRequest clientRequest) throws Exception;
	
	public void persistDevice(ClientRequest clientRequest) throws Exception;
	
	public void mergeUsageState(ClientRequest clientRequest) throws Exception;

	public void persistRegisters(ClientRequest clientRequest,String ret) throws JsonProcessingException, IOException ;
	
	public void productDailyLivingTask() throws Exception;
	
	public void productDailyRegistorsTask() throws Exception;
	
	public void UpdateDataUsers1() throws Exception;
	

}
