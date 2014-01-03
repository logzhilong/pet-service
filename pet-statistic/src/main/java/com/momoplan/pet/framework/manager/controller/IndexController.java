package com.momoplan.pet.framework.manager.controller;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.cookie.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.PetUtil;

@Controller
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private ComboPooledDataSource statisticDataSource = null;
	
	@RequestMapping("/yijifen.html")
	public void index(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject success = new JSONObject();
		success.put("success", true);
		Connection conn = null;
		try{
			logger.debug("wlcome to pet manager index......");
			String ct = request.getParameter("eventtime") ;
			String mac = request.getParameter("IDFV") ;
			String cd = DateUtils.formatDate(new Date());
			String callback = request.getParameter("callback_url");
			callback = URLDecoder.decode(callback, "UTF-8");
			
			conn = statisticDataSource.getConnection();
			conn.setAutoCommit(true);
			Statement stat = conn.createStatement();

			StringBuffer sb = new StringBuffer("insert into biz_yijifen ");
			sb.append("(id,cd,ct,callback)values(");
			sb.append("'"+mac+"','"+cd+"','"+ct+"','"+callback+"'");
			sb.append(")");
			String sql = sb.toString();
			logger.debug(sql);
			try{
				stat.execute(sql);
			}catch(Exception e){
				logger.debug(e.getMessage());
			}
			stat.close();
		}catch(Exception e){
			String id = UUID.randomUUID().toString();
			success.put("success", false);
			success.put("message", "id="+id+";err="+e.getMessage());
		}finally{
			if(conn!=null)
				conn.close();
			PetUtil.writeStringToResponse(success.toString(),response);
		}
	}
	
}