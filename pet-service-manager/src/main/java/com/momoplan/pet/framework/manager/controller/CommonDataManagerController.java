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
	 * 获取AreaList
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
	 * 搜索区域查询
	 * @param pageBean
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/areaCodeSearch.html")
	public String areaCodeSearch(String father,String grandsunid,PageBean<CommonAreaCode> pageBean, CommonAreaCode myForm,Model model){
		try {
				pageBean.setPageNo(1);
				pageBean.setPageSize(4);
				pageBean = commonDataManagerService.listAreaCode(father,grandsunid,pageBean, myForm);
				model.addAttribute("pageBean",pageBean);
				List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
				model.addAttribute("codes", codes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/manager/commons/areaCodeList";
	}

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
			e.printStackTrace();
			return "/manager/commons/areaCodeAdd";
		}
	}
	/**
	 * 删除Area
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/areaCodeDel.html")
	public String areaCodeDel(CommonAreaCode myForm,Model model){
		try {
				commonDataManagerService.areaCodeDelByid(myForm);
				logger.debug("wlcome to pet manager EditAreaCode......");		
				return "${ctx }/manager/commons/areaCodeList.html";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 增加Area
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/alerts/areaCodeSaveOrUpdate.html")
	public void saveOrUpdateAreaCode(CommonAreaCode myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		long now = System.currentTimeMillis();
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0101");
/*		
		{
			“statusCode”:”200″,
			”message”:”添加成功”,
			“callbackType”:”closeCurrent”,
			”forwardUrl”:”",
			“navTabId”:”main”
		}
*/		
		try{
			CommonAreaCode code=new CommonAreaCode();
			code.setId(myForm.getPid());
			myForm.setPname(commonDataManagerService.getCommonAreaCodeByid(code).getName());
			commonDataManagerService.insertOrUpdateAreaCode(myForm);
		}catch(Exception e){
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
	public void getConmonArealistBypid(CommonAreaCode areaCode,Model model,HttpServletResponse response){
		try {
			logger.debug("地区:"+areaCode.toString());
			List<CommonAreaCode> areaCodes=commonDataManagerService.getConmonArealistBypid(areaCode);
			StringBuffer sb = new StringBuffer("[");
			if(areaCodes.size()>0){
				int i=0;
				for(CommonAreaCode code :areaCodes ){
					if(i++>0){
						sb.append(",");
					}
					sb.append("[\"").append(code.getId()).append("\",\"").append(code.getName()).append("\"]");
				}
				sb.append("]");
			}
			else{
					sb.append("[\"").append("0000000").append("\",\"").append("--请选择--").append("\"]");
					sb.append("]");
			}
			String res = sb.toString();
			logger.debug(res);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
