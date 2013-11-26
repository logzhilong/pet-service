package com.momoplan.pet.framework.petservice.report.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ComparatorUtils;
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
			String scop_json = storePool.get("report_scope");//获取报表的最近取值范围,是一个日期，固定值
			logger.debug("scop_json="+scop_json);
			JSONObject scop_obj = null;
			if(StringUtils.isEmpty(scop_json)){
				scop_obj = new JSONObject();
				scop_obj.put("cd", cd);
			}else{
				scop_obj = new JSONObject(scop_json);
			}
			//报表的 “最近” 取值范围
			model.addAttribute("cd", scop_obj.get("cd"));
			
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
			sortList(data,myForm);//排序
			page.setData(data);
			model.addAttribute("page", page);
			model.addAttribute("all", all);
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("serviceCounter0",e);
			throw e;
		}
		return "/petservice/report/channelCounter0";
	}
	
	private void sortList(List<ChannelCounterVo> data ,ConditionBean myForm){
		final String field = myForm.getOrderField();
		final String desc = myForm.getOrderDirection();
		logger.debug("排序字段 field="+field);
		logger.debug("排序类型 "+desc);
		Collections.sort(data,new Comparator<ChannelCounterVo>(){
			private int compare(Integer o1, Integer o2) {
				if(o1>o2){
					return 1;
				}else if(o1<o2){
					return -1;
				}
				return 0;
			}
			private int compare(Float o1, Float o2) {
				if(o1>o2){
					return 1;
				}else if(o1<o2){
					return -1;
				}
				return 0;
			}
			@Override
			public int compare(ChannelCounterVo o1, ChannelCounterVo o2) {
				try {
					Field f1 = o1.getClass().getDeclaredField(field);
					f1.setAccessible(true);
					Object v1 = f1.get(o1);
					Field f2 = o2.getClass().getDeclaredField(field);
					f2.setAccessible(true);
					Object v2 = f2.get(o2);

					Object c1 = null;
					Object c2 = null;
					if("asc".equalsIgnoreCase(desc)){
						c1 = v1;
						c2 = v2;
					}else{
						c1 = v2;
						c2 = v1;
					}
					if(v1 instanceof java.lang.Integer){
						return compare((Integer)c1, (Integer)c2);
					}else if(v1 instanceof Float){
						return compare((Float)c1, (Float)c2);
					} 
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	
}
