package com.momoplan.pet.framework.petservice.albums.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.albums.po.Photos;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.petservice.albums.service.AlbumsService;
import com.momoplan.pet.framework.petservice.albums.vo.PhotosVo;
import com.momoplan.pet.framework.petservice.customer.controller.GhostAction;

@Controller
public class AlbumsAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(GhostAction.class);
	
	@Autowired
	private AlbumsService albumsService = null;
	
	/**
	 * 获取公共相册列表
	 * @return
	 */
	@RequestMapping("/petservice/albums/publicAlbumsMain.html")
	public String main(Page<Photos> page,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/albums/publicAlbumsMain.html");
		try {
			page.setPageSize(400);
			page = albumsService.getPublicPhotos(page);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("publicAlbumsMain error",e);
		}
		return "/petservice/albums/publicAlbumsMain";
	}

	@RequestMapping("/petservice/albums/publicAlbumsAddOrEdit.html")
	public String addOrEdit(PhotosVo myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/albums/publicAlbumsAddOrEdit.html");
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				Photos po = mapperOnCache.selectByPrimaryKey(Photos.class, id);
				BeanUtils.copyProperties(po, myForm);
				myForm.setAction("EDIT");
			}
			model.addAttribute("myForm", myForm);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("publicAlbumsAddOrEdit error",e);
		}
		return "/petservice/albums/publicAlbumsAddOrEdit";
	}

	@RequestMapping("/petservice/albums/publicAlbumsSave.html")
	public void save(PhotosVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/albums/publicAlbumsSave.html");
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0401");
		json.put("forwardUrl",ctx+"/petservice/albums/publicAlbumsMain.html");
		try {
			logger.debug(myForm.toString());
			Photos po = new Photos();
			BeanUtils.copyProperties(myForm, po);
			if("ADD".equals(myForm.getAction())){
				po.setCt(new Date());
				po.setAlbumId("public");
				logger.debug("insert :::::: "+po.toString());
				mapperOnCache.insertSelective(po, po.getId());
			}else{
				logger.debug("update :::::: "+po.toString());
				mapperOnCache.updateByPrimaryKeySelective(po, po.getId());
			}
		} catch (Exception e) {
			logger.error("publicAlbumsSave error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		String res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

	@RequestMapping("/petservice/albums/publicAlbumsDelete.html")
	public void delete(PhotosVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/albums/publicAlbumsDelete.html");
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0401");
		json.put("forwardUrl",ctx+"/petservice/albums/publicAlbumsMain.html");
		try {
			logger.debug(myForm.toString());
			mapperOnCache.deleteByPrimaryKey(Photos.class, myForm.getId());
		} catch (Exception e) {
			logger.error("publicAlbumsDelete error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		String res = json.toString();
		logger.debug("delete_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	@RequestMapping("/petservice/albums/uploadFile.html")
	public void uploadFile(Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		JSONObject json = new JSONObject();
		try{
			for(Iterator<String> it = multipartRequest.getFileNames();it.hasNext();){
				String fileName = it.next();
				MultipartFile file = multipartRequest.getFile(fileName);
				logger.debug("-----------------------------------");
				logger.debug("file_key : "+fileName);
				logger.debug("name : "+file.getName());
				logger.debug("size : "+file.getSize());
				logger.debug("contentType : "+file.getContentType());
				logger.debug("originalFilename : "+file.getOriginalFilename());
				logger.debug("-----------------------------------");
				String type = null;
				try{
					String n = file.getOriginalFilename().toLowerCase().trim();
					if(n.endsWith("png")){
						type = "image/png";
					}else if(n.endsWith("jpg")){
						type = "image/jpg";
					}else if(n.endsWith("bmp")){
						type = "image/bmp";
					}else if(n.endsWith("jpeg")){
						type = "image/jpeg";
					}else if(n.endsWith("gif")){
						type = "image/gif";
					}
				}catch(Exception e){
					logger.debug("TTTTTTT : "+e.getMessage());
					type = "image/jpg";
				}
				ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
				BufferedImage bi = ImageIO.read(bis);
				double width = bi.getWidth();
				double height = bi.getHeight();
				bis.close();
				String imageWidth = "1024";
				String imageHeight = null;
				double bili = width/height;
				if(width<1024){
					imageWidth = ""+ new DecimalFormat("#").format(width);
					imageHeight = ""+new DecimalFormat("#").format(height);
				}else{
					imageHeight = ""+new DecimalFormat("#").format((double)1024/bili);
				}
				logger.debug("width="+width);
				logger.debug("height="+height);
				logger.debug("bili="+bili);
				logger.debug("imageWidth="+imageWidth);
				logger.debug("imageHeight="+imageHeight);
				String uploadResponse = uploadFile.upload(file.getBytes(), fileName, type, "NO", "OK",imageWidth);
				JSONObject success = new JSONObject(uploadResponse);
				String entity = success.getString("entity");
				if(success.getBoolean("success")){
					json.put("img", entity);//结果在这里返回给副文本编辑器
					json.put("imgUrl", uploadFile.getFileAsUrl(entity));//结果在这里返回给副文本编辑器
					json.put("width", imageWidth);
					json.put("height", imageHeight);
				}else{
					throw new Exception(entity);
				}
			}
		}catch(Exception e){
			logger.error("",e);
			json.put("error", e.getMessage());
		}
		String res = json.toString();
		logger.debug(res);
		PetUtil.writeStringToResponse(res, response);
	}
}
