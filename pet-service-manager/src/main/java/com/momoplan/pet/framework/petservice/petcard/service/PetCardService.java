package com.momoplan.pet.framework.petservice.petcard.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.user.mapper.PetCardMapper;
import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.commons.domain.user.po.PetCardCriteria;
import com.momoplan.pet.commons.qrcode.RoundLineQRCodeUtils;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;

@Controller
public class PetCardService extends BaseAction{
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(PetCardService.class);
	@Autowired
	private PetCardMapper petCardMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	
	public Page<PetCard> getPetCardList(Page<PetCard> pages)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		PetCardCriteria petCardCriteria = new PetCardCriteria();
		petCardCriteria.setOrderByClause("et desc");
		int totalCount = petCardMapper.countByExample(petCardCriteria);
		petCardCriteria.setMysqlOffset((pageNo-1) * pageSize);
		petCardCriteria.setMysqlLength(pageSize);
		List<PetCard> data = petCardMapper.selectByExample(petCardCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public void save(Integer number,String currentUser) throws Exception{
		for(int i=0;i<number;i++){
			String shortId = commonConfig.generateShortId();
			createQrcode(shortId);
			Date now = new Date();
			PetCard vo = new PetCard();
			vo.setId(shortId);
			vo.setCt(now);
			vo.setEt(now);
			vo.setCb(currentUser);
			vo.setEb(currentUser);
			logger.debug("新增 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
	private void createQrcode(String shortId){
		String qrcodeInfo = "http://52pet.net/share/"+shortId+"/p.html";
		ByteArrayOutputStream bos = null;
		try {
			logger.info("qrcodeInfo:::> "+qrcodeInfo);
			BufferedImage ii = new RoundLineQRCodeUtils(4,3).createImg(qrcodeInfo, Color.BLACK, Color.WHITE);
			bos = new ByteArrayOutputStream();
			ImageIO.write(ii, "png", bos);
			String uploadResponse = uploadFile.upload(bos.toByteArray(), shortId, "image/png", "NO", "OK",null,shortId);
			logger.debug("upload:::>"+uploadResponse);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {}
		}
	}
	
}
