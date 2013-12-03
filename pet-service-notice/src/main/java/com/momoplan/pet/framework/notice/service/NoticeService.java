package com.momoplan.pet.framework.notice.service;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.notice.mapper.NoticeMapper;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.commons.domain.notice.po.NoticeCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * @author liangc
 */
@Service
public class NoticeService {
	private static Logger logger = LoggerFactory.getLogger(NoticeService.class);
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private NoticeMapper noticeMapper = null;
	
	public Page<Notice> getNoticeList(Page<Notice> pages,String pid) throws Exception {
		logger.debug("pid="+pid);
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		NoticeCriteria noticeCriteria = new NoticeCriteria();
		NoticeCriteria.Criteria criteria = noticeCriteria.createCriteria();
		noticeCriteria.setOrderByClause("seq asc");
		if(StringUtils.isEmpty(pid)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(pid);
		}
		int totalCount = noticeMapper.countByExample(noticeCriteria);
		noticeCriteria.setMysqlOffset(pageNo * pageSize);
		noticeCriteria.setMysqlLength((pageNo+1)*pageSize);
		List<Notice> data = noticeMapper.selectByExample(noticeCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
}