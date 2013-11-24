package com.momoplan.pet.framework.petservice.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.DateUtils;
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
		String cd = DateUtils.formatDate(DateUtils.minusDays(DateUtils.now(), 1));
		try {
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict c : channelDictList){
				channelDict.put(c.getCode(), c.getAlias());
			}
			List<String> list = storePool.lrange(report_channel_counter_key, 0, -1);
			String scop_json = storePool.get("report_scope");//获取报表的最近取值范围
			logger.debug("scop_json="+scop_json);
			JSONObject scop_obj = null;
			if(StringUtils.isEmpty(scop_json)){
				scop_obj = new JSONObject();
				scop_obj.put("min", cd);
				scop_obj.put("max", cd);
			}else{
				scop_obj = new JSONObject(scop_json);
			}
			//报表的 “最近” 取值范围
			model.addAttribute("min", scop_obj.get("min"));
			model.addAttribute("max", scop_obj.get("max"));
			
			List<ChannelCounterVo> data = new ArrayList<ChannelCounterVo>();
			ChannelCounterVo all = new ChannelCounterVo();
			for(String j : list){
				ChannelCounterVo vo = gson.fromJson(j, ChannelCounterVo.class);
				data.add(vo);
				String channelName = channelDict.get(vo.getChannel());
				if(StringUtils.isEmpty(channelName)){
					channelName = "未注册此渠道";
					vo.setReg(false);//未注册这个渠道，不能查看明细
				}
				vo.setChannelName(channelName);
				//累加得出总数
				all.setNew_user(all.getNew_user()+vo.getNew_user());
				all.setNew_register(all.getNew_register()+vo.getNew_register());
				
				all.setAll_user(all.getAll_user()+vo.getAll_user());
				all.setAll_register(all.getAll_register()+vo.getAll_register());
				
				all.setNew_pv(all.getNew_pv()+vo.getNew_pv());
				all.setAll_pv(all.getAll_pv()+vo.getAll_pv());
			}
			page.setData(data);
			model.addAttribute("page", page);
			model.addAttribute("all", all);
		} catch (Exception e) {
			logger.error("serviceCounter0",e);
			throw e;
		}
		return "/petservice/report/channelCounter0";
	}
	
	public static void main(String[] args) {
	}
}
