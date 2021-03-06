package com.momoplan.pet.framework.albums.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.albums.mapper.PhotosMapper;
import com.momoplan.pet.commons.domain.albums.po.Photos;
import com.momoplan.pet.commons.domain.albums.po.PhotosCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * @author liangc
 */
@Service
public class PhotoService {
	private static Logger logger = LoggerFactory.getLogger(PhotoService.class);
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private PhotosMapper photosMapper = null;
	
	public Page<Photos> getPublicPhotos(Page<Photos> pages) throws Exception {
		int pageNo = pages.getPageNo();
		int pageSize = pages.getPageSize();
		logger.debug("获取公共相册: pageNo="+pageNo+" ; pageSize="+pageSize);
		PhotosCriteria photosCriteria = new PhotosCriteria();
		PhotosCriteria.Criteria criteria = photosCriteria.createCriteria();
		criteria.andAlbumIdEqualTo("public");//公共相册
		int totalCount = photosMapper.countByExample(photosCriteria);
		photosCriteria.setOrderByClause("ct desc");
		photosCriteria.setMysqlOffset(pageNo * pageSize);
		photosCriteria.setMysqlLength(pageSize);
		List<Photos> data = photosMapper.selectByExample(photosCriteria);
		pages.setData(data);
		pages.setTotalCount(totalCount);
		return pages;
	}
	
}