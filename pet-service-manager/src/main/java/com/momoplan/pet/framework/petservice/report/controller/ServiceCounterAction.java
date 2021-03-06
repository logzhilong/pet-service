package com.momoplan.pet.framework.petservice.report.controller;

import java.text.DecimalFormat;
import java.util.Date;
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

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.FreeMarkerUtils;
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDict;
import com.momoplan.pet.commons.domain.manager.po.MgrServiceDict;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.petservice.report.service.ChannelDictService;
import com.momoplan.pet.framework.petservice.report.service.ServiceCounterService;
import com.momoplan.pet.framework.petservice.report.service.ServiceDictService;
import com.momoplan.pet.framework.petservice.report.vo.ConditionBean;
import com.momoplan.pet.framework.petservice.report.vo.ServiceCounterVo;

@Controller
public class ServiceCounterAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(ServiceCounterAction.class);
	@Autowired
	private ServiceDictService serviceDictService = null;
	@Autowired
	private ServiceCounterService serviceCounterService = null;
	@Autowired
	private ChannelDictService channelDictService = null;
	
	/**
	 * 每个服务的计数器
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/report/serviceCounter0.html")
	public String serviceCounter0(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/serviceCounter0.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			List<MgrServiceDict> list = serviceDictService.getServiceDictList();
			Map<String,String> map = new HashMap<String,String>();
			
			StringBuffer sb = new StringBuffer();
			sb.append("select service,method,sum(counter) as totalCount ");
			sb.append("from biz_service_counter ");
			int i=0;
			for(MgrServiceDict m : list){
				if(i==0)
					sb.append("where ");
				if(i++>0){
					sb.append("or ");
				}
				sb.append("( ");
				sb.append("service='").append(m.getService()).append("' ");
				sb.append("and ");
				sb.append("method='").append(m.getMethod()).append("' ");
				sb.append(") ");
				map.put(m.getService()+m.getMethod(), m.getAlias());
			}
			
			sb.append("group by service,method "); 
			sb.append("order by totalCount desc");
			String sql = sb.toString();
			logger.debug("SQL--0 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,0);
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(map.get(vo.getService()+vo.getMethod()));
			}
			
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("title", "服务使用率");
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter0.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("list", dataList);
		} catch (Exception e) {
			logger.error("serviceCounter0",e);
			throw e;
		}
		return "/petservice/report/serviceCounter0";
	}

	
	@RequestMapping("/petservice/report/serviceCounter1.html")
	public String serviceCounter1(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/serviceCounter1.html");
		logger.debug("input:"+gson.toJson(myForm));
		String[] sm = myForm.getServiceMethod().split(ConditionBean.serviceMethodSplit);
		String service = sm[0];
		String method = sm[1];
		String cd = myForm.getCd();
		String title = null;
		try {
			//service.uri.pet_sso__firstOpen
			if("firstOpen".equals(method)){
				logger.debug("//modify by liangc 131224 : 激活调整为用 IMEI 统计，废弃 firstOpen 方法");
				return serviceCounter1ByImei(myForm, model,1,null);
			}
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			
			MgrServiceDict mgrServiceDict = serviceDictService.getMgrServiceDict(service, method);
			String alias = mgrServiceDict.getAlias();
			
			title = "各渠道 "+alias+" 统计";
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select service,method,sum(counter) as totalCount ,channel ");
			sb.append(" from biz_service_counter ");
			sb.append(" where ");
			int i=0;
			for(MgrChannelDict m : channelDictList){
				if(i==0){
					sb.append("( ");
				}
				if(i++>0)
					sb.append(" or ");
				sb.append("channel='").append(m.getCode()).append("' ");
				channelDict.put(m.getCode(), m.getAlias());
			}
			if(i>0)
				sb.append(") and ");
			
			sb.append(" service='").append(service).append("' ");
			sb.append(" and ");
			sb.append(" method='").append(method).append("' ");

			if(StringUtils.isNotEmpty(cd)&&!cd.contains("__")){
				sb.append(" and ");
				sb.append(" cd='").append(cd).append("' ");
				title = title+" ("+cd+")";
			}
			sb.append(" group by service,method,channel ");
			sb.append(" order by totalCount desc ");
			String sql = sb.toString();
			logger.debug("SQL--1 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,1);
			
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(channelDict.get(vo.getChannel()));
			}
			
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("title", title );
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter1.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			
			logger.debug(xmlData);
			model.addAttribute("danwei", "次");
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
		} catch (Exception e) {
			logger.error("serviceCounter1",e);
			throw e;
		}
		return "/petservice/report/serviceCounter1";
	}
	
	public String serviceCounter1ByImei(ConditionBean myForm,Model model,int live,String channel)throws Exception{
		String cd = myForm.getCd();
		String year = myForm.getYear();
		String month = myForm.getMonth();
		String title = null;
		String template = "/template/servicecounter/serviceCounter1.ftl";
		String success = "/petservice/report/serviceCounter1";
		try {
			if(StringUtils.isEmpty(month)){
				String ym = DateUtils.formatDate(new Date()).substring(0, 7);
				String[] ymArr = ym.split("-");
				year = ymArr[0];
				month = ymArr[1];
			}
			//modify by liangc 131224 : 激活调整为用 IMEI 统计，废弃 firstOpen 方法
			//service.uri.pet_sso__firstOpen
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict m : channelDictList){
				channelDict.put(m.getCode(), m.getAlias());
			}
			
			String alias = "新增用户";
			title = "各渠道 "+alias+" 统计";
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(0) as totalCount ,channel ");
			 if(live==2){
					sb.append(" ,cd ");
			}
			sb.append(" from biz_imei ");
			sb.append(" where ");
			if(StringUtils.isNotEmpty(channel)){
				sb.append(" channel='").append(channel).append("' ");
			} else {
				sb.append(" 1=1 ");
			}
			if(StringUtils.isNotEmpty(cd)&&!cd.contains("__")){
				sb.append(" and cd='").append(cd).append("' ");
				title = title+" ("+cd+")";
			}
			if(live==1){
				sb.append(" group by channel ");
				sb.append(" order by totalCount desc ");
			}else if(live==2){
				sb.append(" and cd like '").append(year+"-"+month).append("%' ");
				sb.append(" group by channel,cd ");
				sb.append(" order by cd desc ");
				
				template = "/template/servicecounter/serviceCounter2.ftl";
				success = "/petservice/report/serviceCounter2";
			}
			
			String sql = sb.toString();
			logger.debug("SQL--imei--1 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVoByImei(sql, live);

			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(channelDict.get(vo.getChannel()));
			}
			
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("title", title );
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils(template,root).getText();
				
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			
			logger.debug(xmlData);
			model.addAttribute("danwei", "次");
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
			model.addAttribute("year", year);
			model.addAttribute("month", month);
		} catch (Exception e) {
			logger.error("serviceCounter1ByImei",e);
			throw e;
		}
		
		return success;
	}
	
	
	@RequestMapping("/petservice/report/serviceCounter2.html")
	public String serviceCounter2(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/serviceCounter1.html");
		logger.debug("input:"+gson.toJson(myForm));
		String[] sm = myForm.getServiceMethod().split(ConditionBean.serviceMethodSplit);
		String service = sm[0];
		String method = sm[1];
		String channel = sm[2];
		String year = myForm.getYear();
		String month = myForm.getMonth();
		try {
			//service.uri.pet_sso__firstOpen
			if("firstOpen".equals(method)){
				logger.debug("//modify by liangc 131224 : 激活调整为用 IMEI 统计，废弃 firstOpen 方法");
				return serviceCounter1ByImei(myForm, model,2,channel);
			}
			if(StringUtils.isEmpty(month)){
				String ym = DateUtils.formatDate(new Date()).substring(0, 7);
				String[] ymArr = ym.split("-");
				year = ymArr[0];
				month = ymArr[1];
			}
			MgrChannelDict mgrChannelDict =  channelDictService.getChannelDict(channel);
			if(mgrChannelDict==null)
				throw new Exception("channel ["+channel+"] Not define...");
			MgrServiceDict mgrServiceDict = serviceDictService.getMgrServiceDict(service, method);
			String alias = mgrServiceDict.getAlias();
			
			StringBuffer sb = new StringBuffer();
			sb.append(" select service,method,sum(counter) as totalCount ,channel ,cd ");
			sb.append(" from biz_service_counter ");
			sb.append(" where ");
			
			sb.append(" cd like '").append(year+"-"+month).append("%' ");
			sb.append(" and ");
			sb.append(" service='").append(service).append("' ");
			sb.append(" and ");
			sb.append(" method='").append(method).append("' ");
			sb.append(" and ");
			sb.append(" channel='").append(channel).append("' ");
			
			sb.append(" group by service,method,cd ");
			sb.append(" order by cd desc ");
			String sql = sb.toString();
			logger.debug("SQL--2 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,2);
			
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(mgrChannelDict.getAlias());
			}
			
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("title", mgrChannelDict.getAlias()+" 渠道 "+alias+" 服务统计（"+month+")");
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter2.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("xmlData", xmlData);
			
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
			model.addAttribute("myForm", myForm);
			model.addAttribute("year", year);
			model.addAttribute("month", month);
		} catch (Exception e) {
			logger.error("serviceCounter2",e);
			throw e;
		}
		return "/petservice/report/serviceCounter2";
	}

	
	/**
	 * 今日注册率
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/petservice/report/register_rate.html")
	public String registerRate(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/register_rate.html");
		logger.debug("input:"+gson.toJson(myForm));
		String cd = myForm.getCd();
		String title = null;
		String alias = "注册率";
		
		Map<String,Object> root = new HashMap<String,Object>();
		try {
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict m : channelDictList){
				channelDict.put(m.getCode(), m.getAlias());
			}
			title = "各渠道 "+alias+" 统计";
			/*
			if(StringUtils.isNotEmpty(cd)&&cd.contains("__")){
				String[] scop = cd.split(ConditionBean.serviceMethodSplit);
				String min = scop[0];
				String max = scop[1];
				root.put("min", min);
				root.put("max", max);
			}
			*/
			root.put("cd", cd);
			String sql = new FreeMarkerUtils("/template/report/register_rate.ftl",root).getText();
			logger.debug("SQL--rate :::: "+sql);
			
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,1);
			for(ServiceCounterVo vo : dataList){
				String totalCount = vo.getTotalCount();
				if(StringUtils.isEmpty(totalCount)){
					vo.setTotalCount("0");
				}else{
					Float f = Float.parseFloat(totalCount);
					String t = new DecimalFormat(".##").format(f*100);
					vo.setTotalCount(t);
				}
				vo.setAlias(alias);
				vo.setChannelName(channelDict.get(vo.getChannel()));
			}
			root.clear();
			root.put("title", title );
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter1.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("danwei", "%");
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
		} catch (Exception e) {
			logger.error("serviceCounter1",e);
			throw e;
		}
		return "/petservice/report/serviceCounter1";
	}
	
	@RequestMapping("/petservice/report/pv1.html")
	public String pv(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/pv1.html");
		logger.debug("input:"+gson.toJson(myForm));
		String cd = myForm.getCd();
		String title = null;
		String alias = "PV";
		
		Map<String,Object> root = new HashMap<String,Object>();
		try {
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict m : channelDictList){
				channelDict.put(m.getCode(), m.getAlias());
			}
			title = "各渠道 "+alias+" 统计";
			
			root.put("cd", cd);
			String sql = new FreeMarkerUtils("/template/report/pv1.ftl",root).getText();
			logger.debug("SQL--pv :::: "+sql);
			
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,1);
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(channelDict.get(vo.getChannel()));
			}
			root.clear();
			root.put("title", title );
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter1.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("danwei", "次");
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
		} catch (Exception e) {
			logger.error("serviceCounter1",e);
			throw e;
		}
		return "/petservice/report/serviceCounter1";
	}
	
	
	@RequestMapping("/petservice/report/pv2.html")
	public String pv2(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/pv2.html");
		logger.debug("input:"+gson.toJson(myForm));
		String year = myForm.getYear();
		String month = myForm.getMonth();
		String channel = myForm.getChannel();
		try {
			Map<String,Object> root = new HashMap<String,Object>();
			if(StringUtils.isEmpty(month)){
				String ym = DateUtils.formatDate(new Date()).substring(0, 7);
				String[] ymArr = ym.split("-");
				year = ymArr[0];
				month = ymArr[1];
			}
			
			MgrChannelDict mgrChannelDict =  channelDictService.getChannelDict(channel);
			if(mgrChannelDict==null)
				throw new Exception("channel ["+channel+"] Not define...");
			String alias = "PV";
			root.put("cd", year+"-"+month);
			root.put("channel", channel);
			String sql = new FreeMarkerUtils("/template/report/pv2.ftl",root).getText();
			logger.debug("SQL--2 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,2);
			
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(mgrChannelDict.getAlias());
			}
			
			root.clear();
			root.put("title", mgrChannelDict.getAlias()+" 渠道 "+alias+" 服务统计（"+year+"-"+month+")");
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter2.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("xmlData", xmlData);
			
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
			model.addAttribute("myForm", myForm);
			
			model.addAttribute("year", year);
			model.addAttribute("month", month);
		} catch (Exception e) {
			logger.error("serviceCounter2",e);
			throw e;
		}
		return "/petservice/report/serviceCounter2";
	}

	
	@RequestMapping("/petservice/report/online1.html")
	public String online1(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/online1.html");
		logger.debug("input:"+gson.toJson(myForm));
		String cd = myForm.getCd();
		String title = null;
		String alias = "在线用户";
		
		Map<String,Object> root = new HashMap<String,Object>();
		try {
			List<MgrChannelDict> channelDictList = channelDictService.getChannelDictList();
			Map<String,String> channelDict = new HashMap<String,String>();
			for(MgrChannelDict m : channelDictList){
				channelDict.put(m.getCode(), m.getAlias());
			}
			title = "各渠道 "+alias+" 统计";
			
			root.put("cd", cd);
			String sql = new FreeMarkerUtils("/template/report/online1.ftl",root).getText();
			logger.debug("SQL--pv :::: "+sql);
			
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,1);
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(channelDict.get(vo.getChannel()));
			}
			root.clear();
			root.put("title", title );
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter1.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("danwei", "次");
			model.addAttribute("xmlData", xmlData);
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
		} catch (Exception e) {
			logger.error("serviceCounter1",e);
			throw e;
		}
		return "/petservice/report/serviceCounter1";
	}
	
	
	@RequestMapping("/petservice/report/online2.html")
	public String online2(ConditionBean myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("/petservice/report/online2.html");
		logger.debug("input:"+gson.toJson(myForm));
		String year = myForm.getYear();
		String month = myForm.getMonth();
		String channel = myForm.getChannel();
		String alias = "在线用户";
		try {
			Map<String,Object> root = new HashMap<String,Object>();
			if(StringUtils.isEmpty(month)){
				String ym = DateUtils.formatDate(new Date()).substring(0, 7);
				String[] ymArr = ym.split("-");
				year = ymArr[0];
				month = ymArr[1];
			}
			MgrChannelDict mgrChannelDict =  channelDictService.getChannelDict(channel);
			if(mgrChannelDict==null)
				throw new Exception("channel ["+channel+"] Not define...");
			root.put("cd", year+"-"+month);
			root.put("channel", channel);
			String sql = new FreeMarkerUtils("/template/report/online2.ftl",root).getText();
			logger.debug("SQL--2 :::: "+sql);
			List<ServiceCounterVo> dataList = serviceCounterService.selectServiceCounterVo(sql,2);
			
			for(ServiceCounterVo vo : dataList){
				vo.setAlias(alias);
				vo.setChannelName(mgrChannelDict.getAlias());
			}
			
			root.clear();
			root.put("title", mgrChannelDict.getAlias()+" 渠道 "+alias+" 服务统计（"+year+"-"+month+")");
			root.put("dataList", dataList);
			String xmlData = new FreeMarkerUtils("/template/servicecounter/serviceCounter2.ftl",root).getText();
			xmlData = xmlData.replaceAll("\r", "");
			xmlData = xmlData.replaceAll("\n", "");
			xmlData = xmlData.replaceAll("  ", " ");
			logger.debug(xmlData);
			model.addAttribute("xmlData", xmlData);
			
			model.addAttribute("serviceName", alias);
			model.addAttribute("list", dataList);
			model.addAttribute("myForm", myForm);
			
			model.addAttribute("year", year);
			model.addAttribute("month", month);
		} catch (Exception e) {
			logger.error("serviceCounter2",e);
			throw e;
		}
		return "/petservice/report/serviceCounter2";
	}
}
