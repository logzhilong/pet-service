package com.momoplan.pet.framework.petservice.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDict;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.petservice.report.service.ChannelDictService;
import com.momoplan.pet.framework.petservice.report.service.ServiceDictService;
import com.momoplan.pet.framework.petservice.report.vo.ChannelCounterVo;
import com.momoplan.pet.framework.petservice.report.vo.ConditionBean;

@Controller
public class ChannelCounterAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(ChannelCounterAction.class);
	@Autowired
	private ServiceDictService serviceDictService = null;
	@Autowired
	private ChannelDictService channelDictService = null;
	@Autowired
	private StorePool storePool = null;
	/**
	 * 渠道统计的首页数据，由分析程序每24小时刷新一次，跟日志文件切割时间相等
	 */
	private static String report_channel_counter_key = "report_channel_counter_key";
	
	/**
	 * 每个服务的计数器
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/report/channelCounter0.html")
	public String channelCounter0(ConditionBean myForm,Page<ChannelCounterVo> page,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/channelCounter0.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict c : channelDictList){
				channelDict.put(c.getCode(), c.getAlias());
			}
			List<String> list = storePool.lrange(report_channel_counter_key, 0, -1);
			List<ChannelCounterVo> data = new ArrayList<ChannelCounterVo>();
			for(String j : list){
				ChannelCounterVo vo = gson.fromJson(j, ChannelCounterVo.class);
				data.add(vo);
				String channelName = channelDict.get(vo.getChannel());
				if(StringUtils.isEmpty(channelName))
					channelName = "未知渠道";
				vo.setChannelName(channelName);
			}
			page.setData(data);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("serviceCounter0",e);
			throw e;
		}
		return "/petservice/report/channelCounter0";
	}
	
}
