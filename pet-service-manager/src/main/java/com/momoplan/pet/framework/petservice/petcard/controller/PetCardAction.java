package com.momoplan.pet.framework.petservice.petcard.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.user.mapper.PetCardMapper;
import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.commons.domain.user.po.PetCardCriteria;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.petcard.service.PetCardService;

@Controller
public class PetCardAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(PetCardAction.class);
	@Autowired
	private PetCardService petCardService = null;
	@Autowired
	private PetCardMapper petCardMapper = null;

	@RequestMapping("/petservice/petcard/petcardMain.html")
	public String main(PetCard myForm,Page<PetCard> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/petcard/petcardMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			Page<PetCard> page = petCardService.getPetCardList(pages);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("PetCard error",e);
		}
		return "/petservice/petcard/petcardMain";
	}
	
	@RequestMapping("/petservice/petcard/petcardAddOrEdit.html")
	public String addOrEdit(PetCard myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		return "/petservice/petcard/petcardAddOrEdit";
	}

	@RequestMapping("/petservice/petcard/petcardSave.html")
	public void save(Integer number,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/petcard/petcardSave.html");
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0801");
		json.put("forwardUrl",ctx+"/petservice/petcard/petcardMain.html");
		String res = null;
		try {
			WebUser user = SessionManager.getCurrentUser(request);
			petCardService.save(number,user.getUsername());
		} catch (Exception e) {
			logger.error("save PetCard error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	@RequestMapping("/petservice/petcard/petcardDownload.html")
	public void download(Integer start,Integer length,HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(start+"_"+length+".zip", "UTF-8") );
		logger.debug("start="+start+" ; length="+length);
		PetCardCriteria petCardCriteria = new PetCardCriteria();
		petCardCriteria.createCriteria().andNumGreaterThanOrEqualTo(start);
		petCardCriteria.setOrderByClause("num asc");
		petCardCriteria.setMysqlOffset(0);
		petCardCriteria.setMysqlLength(length);
		List<PetCard> data = petCardMapper.selectByExample(petCardCriteria);
		OutputStream fos = response.getOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);
		for(PetCard p : data){
			String fileUrl = commonConfig.get("pet_file_server")+"/get/"+p.getId();
			try {
				URL u = new URL(fileUrl);
				URLConnection conn = u.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				try {
					String z = p.getNum()+".png";
					logger.debug("压缩："+z);
		            ZipEntry entry = new ZipEntry(z);
		            zos.putNextEntry(entry);
		            int count;
		            byte[] buff = new byte[256]; 
		            while ((count = is.read(buff, 0, buff.length)) != -1) {
		            	zos.write(buff, 0, count);
		            }
		            is.close();
		            zos.closeEntry();
		        } catch (Exception e) {  
		            throw new RuntimeException(e);  
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		zos.finish();
		fos.flush();
	}
	
}
