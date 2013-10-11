package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;


@Service
public class BBSManagerServiceImpl implements BBSManagerService {
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerServiceImpl.class);

	@Autowired
	private ForumMapper forumMapper = null;
	@Autowired
	private NoteMapper noteMapper=null;
	@Autowired
	CommonDataManagerService commonDataManagerService;
	@Override
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception {
		String id = vo.getId();
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		if(StringUtils.isEmpty(id)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(id);
		}
		if(StringUtils.isEmpty(vo.getName())){
				if("all".equals(vo.getAreaCode()) && "all".equals(vo.getAreaCode())){
					
				}
				else if("" !=vo.getAreaDesc() && null != vo.getAreaDesc()){
					criteria.andAreaCodeLike("%"+vo.getAreaDesc()+"%");
				}
				else if("" != vo.getAreaCode() && null != vo.getAreaCode()){
					criteria.andAreaCodeLike("%"+vo.getAreaCode()+"%");
				}
		}else {
			
			criteria.andNameLike("%"+vo.getName()+"%");
		}
		int totalCount = forumMapper.countByExample(forumCriteria);
		forumCriteria.setMysqlOffset((pageBean.getPageNo()-1)*pageBean.getPageSize());
		forumCriteria.setMysqlLength(pageBean.getPageSize());
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		pageBean.setData(list);
		pageBean.setTotalRecorde(totalCount);
		for(Forum forum:pageBean.getData()){
				int i=forum.getAreaCode().indexOf("-");
				if(i != -1){
					CommonAreaCode code2=new CommonAreaCode();
					String sheng=forum.getAreaCode().substring(0,forum.getAreaCode().indexOf("-"));
						code2.setId(sheng);
					String sheng1=commonDataManagerService.getCommonAreaCodeByid(code2).getName();
				
						if(forum.getAreaCode().indexOf("-") != forum.getAreaCode().lastIndexOf("-")){
							String shi=forum.getAreaCode().substring(forum.getAreaCode().indexOf("-")+1,forum.getAreaCode().lastIndexOf("-"));
							code2.setId(shi);
							String sheng2=commonDataManagerService.getCommonAreaCodeByid(code2).getName();
							
							String qu=forum.getAreaCode().substring(forum.getAreaCode().lastIndexOf("-")+1);
							code2.setId(qu);
							String sheng3=commonDataManagerService.getCommonAreaCodeByid(code2).getName();
							forum.setAreaCode(sheng1+"-"+sheng2+"-"+sheng3);
						}else{
							String qu=forum.getAreaCode().substring(forum.getAreaCode().lastIndexOf("-")+1);
							code2.setId(qu);
							String sheng3=commonDataManagerService.getCommonAreaCodeByid(code2).getName();
							forum.setAreaCode(sheng1+"-"+sheng3);
						}
						}else{
							CommonAreaCode code2=new CommonAreaCode();
							code2.setId(forum.getAreaCode());
							String ee=commonDataManagerService.getCommonAreaCodeByid(code2).getName();
							forum.setAreaCode(ee);
						}
			}
		return pageBean;
	}
	/**
	 * 增加圈子或者修改圈子
	 * @param forum
	 * @throws Exception
	 */
	public int addOrUpdateForum(Forum forum)throws Exception{
		String id = forum.getId();
		Forum fo = forumMapper.selectByPrimaryKey(id);
		if(fo!=null&&!"".equals(fo.getId())){
			logger.debug("selectByPK.po="+fo.toString());
			return forumMapper.updateByPrimaryKeySelective(forum);
		}else{
			forum.setId(IDCreater.uuid());
			forum.setCt(new Date());
			if("all".equals(forum.getPid())){
				forum.setPid(null);
			}
			return forumMapper.insertSelective(forum);
		}
	}
	/**
	 * 根据id删除圈子
	 * @param forum
	 * @throws Exception
	 */
	public void DelForum(Forum forum)throws Exception{
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidEqualTo(forum.getId());
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			if(forums.size()>0){
				for(Forum fo:forums){
					forumMapper.deleteByPrimaryKey(fo.getId());
				}
			}
			forumMapper.deleteByPrimaryKey(forum.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取圈子
	 * @param forum
	 * @throws Exception
	 */
	public Forum getForumbyid(Forum forum)throws Exception{
		try {
				
			return forumMapper.selectByPrimaryKey(forum.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取圈子集合(做级联)
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	public List<Forum> getForumlist()throws Exception{
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidIsNull();
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			return forums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据父id获取子级圈子集合
	 * @return
	 * @throws Exception
	 */
	public List<Forum> getSunForumListbyPid(Forum forum)throws Exception{
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidEqualTo(forum.getId());
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			return forums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	/**
	 * 根据圈子id获取帖子集合
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	public List<Note> getAllNotesByForumId(Forum forum)throws Exception{
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andForumIdEqualTo(forum.getId());
			List<Note> notes=noteMapper.selectByExample(noteCriteria);
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
