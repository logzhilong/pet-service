package com.momoplan.pet.framework.petservice.push.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrPushMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.manager.po.MgrPushCriteria;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.petservice.push.vo.PushState;

@Controller
public class PushService {
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(PushService.class);
	@Autowired
	private MgrPushMapper mgrPushMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public Page<MgrPush> getMgrPushList(Page<MgrPush> pages,MgrPush vo)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		MgrPushCriteria mgrPushCriteria = new MgrPushCriteria();
		mgrPushCriteria.setOrderByClause("et desc");
		int totalCount = mgrPushMapper.countByExample(mgrPushCriteria);
		mgrPushCriteria.setMysqlOffset(pageNo * pageSize);
		mgrPushCriteria.setMysqlLength((pageNo+1)*pageSize);
		List<MgrPush> data = mgrPushMapper.selectByExample(mgrPushCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public static void main(String[] args) {
		System.out.println(PushState.PUSHED.equals(PushState.valueOf("PUSHED")));
	}
	
	public void save(MgrPush vo) throws Exception{
		logger.debug("save : "+vo.toString());
		Date now = new Date();
		String id = vo.getId();
		String src = vo.getSrc();
		MgrPush p = mapperOnCache.selectByPrimaryKey(MgrPush.class, id);
		if(p!=null){
			logger.debug("更新 "+gson.toJson(vo));
			if(StringUtils.isNotEmpty(vo.getSrc())){
				throw new Exception("此记录已加入推送列表，不能重复添加");
			}
			logger.debug("state="+p.getState());
			if(PushState.PUSHED.equals(PushState.valueOf(p.getState()))){
				String e = DateUtils.formatDate(p.getEt());
				String n = DateUtils.formatDate(now);
				logger.debug("n="+n);
				logger.debug("e="+e);
				if(n.equals(e)){
					throw new Exception("此记录已推送，当天不能重复推送。");
				}
			}
			vo.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			String name = getNameBySrc(src,id);
			logger.debug("新增 "+gson.toJson(vo));
			vo.setName(name);
			vo.setCt(now);
			vo.setCb(vo.getEb());
			vo.setEt(now);
			vo.setState(PushState.NEW.getCode());
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
	private String getNameBySrc(String src,String id) throws Exception{
		String name = null;
		if("bbs_note".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Note.class, id).getName();
			logger.debug("帖子 : "+name);
		}else if("ency".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Ency.class, id).getName();
			logger.debug("百科 : "+name);
		}else if("exper".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Exper.class, id).getName();
			logger.debug("经验 : "+name);
		}else if("notice".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Notice.class, id).getName();
			logger.debug("通知 : "+name);
		}
		return name;
	}
	
}
