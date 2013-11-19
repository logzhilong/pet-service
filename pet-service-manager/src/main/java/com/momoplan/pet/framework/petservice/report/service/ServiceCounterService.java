package com.momoplan.pet.framework.petservice.report.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.framework.petservice.report.vo.ServiceCounterVo;

@Service
public class ServiceCounterService {

	private static Logger logger = LoggerFactory.getLogger(ServiceCounterService.class);
	private static Gson gson = MyGson.getInstance();

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Autowired
	public ServiceCounterService(ComboPooledDataSource statisticDataSource) {
		super();
		jdbcTemplate.setDataSource(statisticDataSource);
	}
	
	public List<ServiceCounterVo> selectServiceCounterVo(String sql,final int level)throws Exception{
		
		List<ServiceCounterVo> counter = jdbcTemplate.query(sql, new RowMapper<ServiceCounterVo>(){
			@Override
			public ServiceCounterVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ServiceCounterVo vo = new ServiceCounterVo();
				String service = rs.getString("service");
				String method = rs.getString("method");
				String totalCount = rs.getString("totalCount");
				vo.setService(service);
				vo.setMethod(method);
				vo.setTotalCount(totalCount);
				if(level==1){
					String channel = rs.getString("channel");
					vo.setChannel(channel);
				}
				if(level==2){
					String channel = rs.getString("channel");
					vo.setChannel(channel);
					String cd = rs.getString("cd");
					vo.setCd(cd);
				}
				logger.debug(gson.toJson(vo));
				return vo;
			}
		});
		
		return counter;
	}
	
}
