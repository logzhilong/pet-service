package com.momoplan.pet.framework.statistic.controller;

import java.net.URLDecoder;
import java.util.Date;

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

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.statistic.mapper.BizYijifenMapper;
import com.momoplan.pet.commons.domain.statistic.po.BizYijifen;
import com.momoplan.pet.framework.statistic.jms.PetEventsListener;

@Controller
public class IndexController {
	
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private BizYijifenMapper bizYijifenMapper = null;
	
	@RequestMapping("/yijifen.html")
	public void index(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject success = new JSONObject();
		success.put("success", true);
		try{
			logger.debug("wlcome to pet manager index......");
			String ct = request.getParameter("eventtime") ;
			String mac = request.getParameter("IDFV") ;
			String cd = DateUtils.formatDate(new Date());
			String callback = request.getParameter("callback_url");
			callback = URLDecoder.decode(callback, "UTF-8");
			BizYijifen bizYijifen = new BizYijifen();
			bizYijifen.setId(mac);
			bizYijifen.setCd(cd);
			bizYijifen.setCt(ct);
			bizYijifen.setCallback(callback);
			try{
				bizYijifenMapper.insertSelective(bizYijifen);
				PetEventsListener.memCache.put(bizYijifen.getId(), bizYijifen.getCd());
			}catch(Exception e){
				logger.info(cd+";"+"重复:"+mac);
			}
			success.put("message", mac);
		}catch(Exception e){
			String id = IDCreater.uuid();
			success.put("success", false);
			success.put("message", "id="+id+";err="+e.getMessage());
		}finally{
			PetUtil.writeStringToResponse(success.toString(),response);
		}
	}
	
}