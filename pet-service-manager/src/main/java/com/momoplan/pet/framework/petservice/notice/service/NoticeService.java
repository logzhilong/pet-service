package com.momoplan.pet.framework.petservice.notice.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.notice.mapper.NoticeMapper;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.commons.domain.notice.po.NoticeCriteria;

@Controller
public class NoticeService {
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(NoticeService.class);
	@Autowired
	private NoticeMapper NoticeMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public Page<Notice> getNoticeList(Page<Notice> pages,Notice vo)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		NoticeCriteria NoticeCriteria = new NoticeCriteria();
		NoticeCriteria.setOrderByClause("et desc");
		int totalCount = NoticeMapper.countByExample(NoticeCriteria);
		NoticeCriteria.setMysqlOffset(pageNo * pageSize);
		NoticeCriteria.setMysqlLength((pageNo+1)*pageSize);
		List<Notice> data = NoticeMapper.selectByExample(NoticeCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public void save(Notice vo) throws Exception{
		Date now = new Date();
		if("".equals(vo.getPid()))
			vo.setPid(null);
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新 "+gson.toJson(vo));
			vo.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setCb(vo.getEb());
			vo.setEt(now);
			logger.debug("新增 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
}
