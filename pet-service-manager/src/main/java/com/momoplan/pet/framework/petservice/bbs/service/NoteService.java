package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.CacheKeysConstance;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.base.vo.MgrTrustUserVo;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.petservice.bbs.controller.NoteAction;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteVo;

@Service
public class NoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteAction.class);
	
	private static Gson gson = MyGson.getInstance();

	@Autowired
	private NoteRepository noteResponse = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private StorePool storePool = null;
	@Autowired
	private NoteMapper noteMapper = null;
	@Autowired
	private MgrTrustUserMapper mgrTrustUserMapper = null;
	
	public List<MgrTrustUserVo> getMgrTrustUserList(String currentUserid) throws Exception {
		logger.debug("currentUserid="+currentUserid);
		MgrTrustUserCriteria mgrTrustUserCriteria = new MgrTrustUserCriteria();
		mgrTrustUserCriteria.createCriteria().andNrootIdEqualTo(currentUserid);
		List<MgrTrustUser> list = mgrTrustUserMapper.selectByExample(mgrTrustUserCriteria);
		List<MgrTrustUserVo> l = new ArrayList<MgrTrustUserVo>();
		if(list!=null&&list.size()>0){
			logger.debug("我管理的用户 >>>>>>>>");
			for(MgrTrustUser tu : list){
				SsoUser u = mapperOnCache.selectByPrimaryKey(SsoUser.class, tu.getUserId());
				MgrTrustUserVo v = new MgrTrustUserVo();
				BeanUtils.copyProperties(tu, v);
				v.setUserName(u.getUsername());
				v.setNickname(u.getNickname());
				l.add(v);
			}
			logger.debug("我管理的用户 <<<<<<<<<");
			return l;
		}else
			throw new Exception("无托管用户，请先创建托管用户。");
	}
	
	public Page<NoteVo> getNoteList(Page<NoteVo> page,NoteVo vo) throws Exception {
		logger.debug("getNoteList: "+gson.toJson(vo));
		
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andForumIdEqualTo(vo.getForumId());
		criteria.andIsDelEqualTo(false);
		if(!"ALL".equalsIgnoreCase(vo.getCondition_state())){
			logger.debug("条件-状态："+vo.getCondition_state());
			criteria.andStateEqualTo(vo.getCondition_state());
		}
		if(!"ALL".equalsIgnoreCase(vo.getCondition_isTop())){
			logger.debug("条件-置顶："+vo.getCondition_isTop());
			criteria.andIsTopEqualTo(Boolean.valueOf(vo.getCondition_isTop()));
		}
		if(!"ALL".equalsIgnoreCase(vo.getCondition_isEute())){
			logger.debug("条件-精华："+vo.getCondition_isEute());
			criteria.andIsEuteEqualTo(Boolean.valueOf(vo.getCondition_isEute()));
		}
		int totalCount = noteMapper.countByExample(noteCriteria);
		noteCriteria.setMysqlOffset(page.getPageNo());
		noteCriteria.setMysqlLength((page.getPageNo()+1)*page.getPageSize());
		noteCriteria.setOrderByClause("et desc");
		List<Note> list = noteMapper.selectByExample(noteCriteria);
		List<NoteVo> vol = new ArrayList<NoteVo>();
		if(totalCount>0){
			for(Note n : list){
				NoteVo v = new NoteVo();
				BeanUtils.copyProperties(n, v);
				try{
					SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, n.getUserId());
					v.setUserName(user.getUsername());
					v.setNickname(user.getNickname());
				}catch(Exception e){
					logger.debug(e.getMessage());
				}
				vol.add(v);
			}
		}
		page.setTotalCount(totalCount);
		page.setData(vol);
		return page;
		
	}

	public void saveNote(Note vo) throws Exception {
		filterTopNote(vo);
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新帖子 "+gson.toJson(vo));
			Date now = new Date();
			vo.setEt(now);
			if(vo.getIsTop()){
				logger.debug(vo.getName()+"--更新为置顶帖,修改日期:"+now);
			}
			logger.debug("//TODO 更新时要清空总数的缓存，否则总数对不上呢");
			String forumId = vo.getForumId();
			logger.debug("//clear cache 帖子总数列表");
			String totalCountKey = CacheKeysConstance.LIST_NOTE_TOTALCOUNT+forumId;
			logger.debug("//clear cache 当天帖子总数");
			String totalTodayKey = CacheKeysConstance.LIST_NOTE_TOTALTODAY+forumId+":"+DateUtils.getTodayStr();
			logger.debug("//totalCountKey="+totalCountKey);
			logger.debug("//totalTodayKey="+totalTodayKey);
			storePool.del(totalCountKey,totalTodayKey);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			logger.debug("新增帖子需要调接口的 "+gson.toJson(vo));
			String url = commonConfig.get("service.uri.pet_bbs");
			ClientRequest request = new ClientRequest();
			request.setMethod("sendNote");
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("userId", vo.getUserId());
			params.put("forumId", vo.getForumId());
			params.put("name", vo.getName());
			params.put("content", vo.getContent());
			request.setParams(params);
			String body = gson.toJson(request);
			logger.debug("url="+url);
			logger.debug("body="+body);
			String res = PostRequest.postText(url, "body", body);
			logger.debug("response="+res);
			JSONObject success = new JSONObject(res);
			if(!success.getBoolean("success")){
				throw new Exception(success.getString("entity"));
			}
		}
	}
	
	private void filterTopNote(Note vo) throws Exception{
		String id = vo.getId();
		Note n = null;
		if(StringUtils.isNotEmpty(id)){
			n = mapperOnCache.selectByPrimaryKey(Note.class,id );
			logger.debug("旧帖子 n="+n);
		}
		if(vo.getIsTop()||(n!=null&&n.getIsTop())){
			logger.debug("刷新置顶缓存");
			noteResponse.flushTopNoteByFid(vo.getForumId());
		}
// 	modify by liangc 131210 : 过滤置顶的任务交给前台运营人员吧暂时
//		logger.debug("过滤置顶帖子");
//		NoteCriteria noteCriteria = new NoteCriteria();
//		noteCriteria.createCriteria()
//			.andForumIdEqualTo(vo.getForumId())
//			.andIsTopEqualTo(true)
//			.andIsDelEqualTo(false);
//		noteCriteria.setOrderByClause("ct asc");
//		List<Note> list = noteMapper.selectByExample(noteCriteria);
//		if(list!=null&&list.size()>5){
//			Note n = list.get(0);
//			n.setIsTop(false);
//			mapperOnCache.updateByPrimaryKeySelective(n, n.getId());
//			logger.debug("置顶总数："+list.size()+" ; 过滤 id="+n.getId()); 
//			filterTopNote(vo);
//		}else{
//			logger.debug("结束过滤");
//		}
	}
	
}
