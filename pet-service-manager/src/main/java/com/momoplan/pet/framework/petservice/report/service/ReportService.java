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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.framework.petservice.report.controller.ServiceCounterAction;
import com.momoplan.pet.framework.petservice.report.vo.ServiceCounterVo;

@Service
public class ReportService {

	private static Logger logger = LoggerFactory.getLogger(ServiceCounterAction.class);
	
	@Autowired
	private ComboPooledDataSource statisticDataSource = null;
	
	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	public List<ServiceCounterVo> select(String sql)throws Exception{
		jdbcTemplate.setDataSource(statisticDataSource);
		List<ServiceCounterVo> counter = jdbcTemplate.query(sql, new RowMapper<ServiceCounterVo>(){
			@Override
			public ServiceCounterVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return null;
			}
		});
		return null;
	}
	
	
/*
Connection conn = null;
Statement statement = null;
try{
	conn = statisticDataSource.getConnection();
	statement = conn.createStatement();
	ResultSet result = statement.executeQuery(sql);
	ResultSetMetaData metaData = result.getMetaData();
	while(result.next()){
		for(int i=0;i<metaData.getColumnCount();i++){
			String c = metaData.getColumnName(i);
			result.get
		}
	}
	
}catch(Exception e){
	
}finally{
	conn.close();
}
*/
	
}
