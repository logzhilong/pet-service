package com.momoplan.pet.framework.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;

@Controller
public class CommonDataManagerController {
	
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerController.class);

	@Autowired
	private CommonDataManagerService commonDataManagerService;
	
	
	/**
	 * 1:获取AreaList  
	 * 2:根据搜索条件查询AreaList
	 * @param pageBean
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/areaCodeList.html")
	public String areaCodeList(String father,String grandsunid,PageBean<CommonAreaCode> pageBean, CommonAreaCode myForm,Model model){
		logger.debug("wlcome to pet manager areaCodeManager......");
		try {
			pageBean = commonDataManagerService.listAreaCode(father,grandsunid,pageBean, myForm);
			model.addAttribute("pageBean",pageBean);
			//读取所有国家(查询级联)
			List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
			model.addAttribute("codes", codes);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/commons/areaCodeList";
	}
	
	/**
	 * To AddOrUpdateArea
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/areaCodeAdd.html")
	public String addOrEditAreaCode(CommonAreaCode myForm,Model model){
		try {
			if("".equals(myForm.getId()) || null==myForm.getId())
			{
				List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
				model.addAttribute("codes",codes);
				logger.debug("wlcome to pet manager addAreaCode......");
				return "/manager/commons/areaCodeAdd";
			}else{
				CommonAreaCode code=commonDataManagerService.getCommonAreaCodeByid(myForm);
				model.addAttribute("code",code);
				logger.debug("wlcome to pet manager EditAreaCode......");		
				return "/manager/commons/areaCodeUpdate";
			}
		} catch (Exception e) {
			logger.error("addOrEditAreaCode"+e);
			e.printStackTrace();
			return "/manager/commons/areaCodeList";
		}
	}
	/**
	 * 删除Area
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/areaCodeDel.html")
	public void areaCodeDel(String id,CommonAreaCode myForm,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0101");
		try {
				commonDataManagerService.areaCodeDelByid(myForm);
				logger.debug("wlcome to pet manager DelAreaCode......");
		} catch (Exception e) {
			logger.error("areaCodeDel"+e);
			e.printStackTrace();
		}
		logger.debug(json.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}
	/**
	 * 增加或者修改Area
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/alerts/areaCodeSaveOrUpdate.html")
	public void saveOrUpdateAreaCode(String father,CommonAreaCode myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0101");
		try{
			CommonAreaCode code=new CommonAreaCode();
			if("" == myForm.getPid() || null == myForm.getPid()){
				if("" != father){
					myForm.setPid(father);
				}
			}
			code.setId(myForm.getPid());
			myForm.setPname(commonDataManagerService.getCommonAreaCodeByid(code).getName());
			commonDataManagerService.insertOrUpdateAreaCode(myForm);
		}catch(Exception e){
			logger.error("saveOrUpdateAreaCode"+e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	
	
	/**
	 * 根据pid获取该AreaList(级联)
	 * @param areaCode
	 * @param model
	 * @param response
	 */
	@RequestMapping("/manager/commons/getConmonArealistBypid.html")
	public void getConmonArealistBypid(CommonAreaCode areaCode,HttpServletResponse response){
		try {
			logger.debug("地区:"+areaCode);
			List<CommonAreaCode> areaCodes=commonDataManagerService.getConmonArealistBypid(areaCode);
			//拼接字符串作为显示区域级联的效果
			StringBuffer sb = new StringBuffer("[");
			if(areaCodes.size()>0){
				int i=0;
				sb.append("[\"").append("").append("\",\"").append("--请选择--").append("\"]");
				sb.append(",");
				for(CommonAreaCode code :areaCodes ){
					if(i++>0){
						sb.append(",");
					}
					sb.append("[\"").append(code.getId()).append("\",\"").append(code.getName()).append("\"]");
				}
				sb.append("]");
			}
			else{
					sb.append("[\"").append("").append("\",\"").append("--请选择--").append("\"]");
					sb.append("]");
			}
			logger.debug(sb.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(sb.toString());
		} catch (Exception e) {
			logger.error("getConmonArealistBypid"+ e);
			e.printStackTrace();
		}
	}
	
	
	
}
