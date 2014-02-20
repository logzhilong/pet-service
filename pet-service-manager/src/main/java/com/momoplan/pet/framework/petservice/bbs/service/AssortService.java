package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.AssortMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumAssortRelMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Assort;
import com.momoplan.pet.commons.domain.bbs.po.AssortCriteria;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRel;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRelCriteria;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;

@Service
public class AssortService {
	private static Logger logger = LoggerFactory.getLogger(AssortService.class);

	private static Gson gson = MyGson.getInstance();

	@Autowired
	private AssortMapper assortMapper = null;
	@Autowired
	private ForumMapper forumMapper = null;
	@Autowired
	private ForumAssortRelMapper forumAssortRelMapper = null;

	@Autowired
	private MapperOnCache mapperOnCache = null;

	public List<Forum> getForumListWithoutAssort(String assortId){
		ForumAssortRelCriteria forumAssortRelCriteria = new ForumAssortRelCriteria();
		if(StringUtils.isNotEmpty(assortId)){
			forumAssortRelCriteria.createCriteria().andAssortIdNotEqualTo(assortId);
		}
		List<ForumAssortRel> farl = forumAssortRelMapper.selectByExample(forumAssortRelCriteria);
		List<String> fids = new ArrayList<String>();
		for(ForumAssortRel far : farl){
			fids.add(far.getForumId());
		}
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andPidIsNotNull();
		if(fids.size()>0){
			criteria.andIdNotIn(fids);
		}
		forumCriteria.setOrderByClause("seq asc");
		List<Forum> fl = forumMapper.selectByExample(forumCriteria);
		return fl;
	}
	
	public Map<String,String> getForumListChecked(String assortId){
		ForumAssortRelCriteria forumAssortRelCriteria = new ForumAssortRelCriteria();
		ForumAssortRelCriteria.Criteria criteria = forumAssortRelCriteria.createCriteria();
		criteria.andAssortIdEqualTo(assortId);
		List<ForumAssortRel> farl = forumAssortRelMapper.selectByExample(forumAssortRelCriteria);
		Map<String,String> map = new HashMap<String,String>();
		for(ForumAssortRel far : farl){
			map.put(far.getForumId(), assortId);
		}
		logger.debug(map.toString());
		return map;
	}
	
	
	public Map<String,Assort> getAssortMap()throws Exception{
		List<Assort> list = assortMapper.selectByExample(new AssortCriteria());
		Map<String,Assort> m = new HashMap<String,Assort>();
		for(Assort f : list){
			m.put(f.getId(), f);
		}
		return m;
	}
	
	public List<Assort> getAssortList(Assort vo)throws Exception {
		logger.debug("查询圈子 "+gson.toJson(vo));
		AssortCriteria assortCriteria = new AssortCriteria();
		AssortCriteria.Criteria criteria = assortCriteria.createCriteria();
		assortCriteria.setOrderByClause("seq asc");
		List<Assort> list = assortMapper.selectByExample(assortCriteria);
		return list;
	}

	public void saveAssort(Assort vo,String[] forumIds) throws Exception {
		Date now = new Date();
		vo.setEt(now);
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新分类 "+gson.toJson(vo));
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setCb(vo.getEb());
			logger.debug("新增分类 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
		saveForumAssortRel(vo,forumIds);
	}
	
	private void saveForumAssortRel(Assort vo,String[] forumIds){
		//delete all of assort id
		ForumAssortRelCriteria forumAssortRelCriteria = new ForumAssortRelCriteria();
		forumAssortRelCriteria.createCriteria().andAssortIdEqualTo(vo.getId());
		forumAssortRelMapper.deleteByExample(forumAssortRelCriteria);
		logger.debug("S-清除圈子与分类关系 : "+vo.getId());
		//add news
		for(String fid : forumIds){
			ForumAssortRel far = new ForumAssortRel();
			far.setId(IDCreater.uuid());
			far.setAssortId(vo.getId());
			far.setForumId(fid);
			far.setCb(vo.getEb());
			far.setCt(new Date());
			forumAssortRelMapper.insertSelective(far);
		}
		logger.debug("SS-新建圈子与分类关系 : "+vo.getId());
	}
	
}
