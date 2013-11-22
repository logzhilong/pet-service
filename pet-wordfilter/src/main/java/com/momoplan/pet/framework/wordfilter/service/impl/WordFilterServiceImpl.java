package com.momoplan.pet.framework.wordfilter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesAudit;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteState;
import com.momoplan.pet.commons.repository.states.StatesUserStatesRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.commons.wordfilter.WordFilterUtil;
import com.momoplan.pet.commons.wordfilter.result.FilteredResult;
import com.momoplan.pet.framework.wordfilter.service.WordFilterService;

@Service
public class WordFilterServiceImpl implements WordFilterService {

	private static Logger logger = LoggerFactory.getLogger(WordFilterServiceImpl.class);

	private StatesUserStatesAuditMapper statesUserStatesAuditMapper = null;
	private MapperOnCache mapperOnCache = null;
	private StatesUserStatesRepository statesUserStatesRepository = null;
	private NoteRepository noteRepository = null;
	private CommonConfig commonConfig = null;
	
	@Autowired
	public WordFilterServiceImpl(
			StatesUserStatesAuditMapper statesUserStatesAuditMapper,
			MapperOnCache mapperOnCache,
			StatesUserStatesRepository statesUserStatesRepository,
			NoteRepository noteRepository, CommonConfig commonConfig) {
		super();
		this.statesUserStatesAuditMapper = statesUserStatesAuditMapper;
		this.mapperOnCache = mapperOnCache;
		this.statesUserStatesRepository = statesUserStatesRepository;
		this.noteRepository = noteRepository;
		this.commonConfig = commonConfig;
	}

	@Override
	public void doFilter(String biz, String id, String content) {
		if ("user_states".equalsIgnoreCase(biz)) {// 用户动态内容过滤
			userStatesHandler(biz, id, content);
		} else if ("bbs_note".equalsIgnoreCase(biz)) {// 圈子，帖子过滤
			bbsNoteHandler(biz,id,content);
		} else {
			logger.info("[ERR:10001] 不能识别的过滤类型 biz=" + biz);
			return;
		}
	}

	private void userStatesHandler(String biz, String bid, String content) {
		String wordfilter = commonConfig.get("wordfilter","enable");
		logger.debug("biz="+biz+" ; bid="+bid+" ; wordfilter="+wordfilter);
		try {
			// content_audit 审核任务
			// user_states 动态
			// state_type :0正常，1假动态，2置顶动态，3审核中，4未通过，5被举报
			// report_times :举报次数
			StatesUserStates statesUserStates = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, bid);
			if("disable".equalsIgnoreCase(wordfilter)){
				logger.debug("后台已关闭审核--全部通过");
				statesUserStates.setState("0");// 通过 TODO 放倒枚举类里
				statesUserStatesRepository.updateSelective(statesUserStates);
				return ;
			}
			logger.debug("开始自动审核...");
			FilteredResult result = WordFilterUtil.filterText(content, '*');
			String badWords = result.getBadWords();// 异常词
			if (badWords != null && badWords.trim().length() > 0) {
				logger.debug("用户动态-内容审核-不通过 badWords=" + badWords);
				StatesUserStatesAudit commonContentAudit = new StatesUserStatesAudit();
				commonContentAudit.setId(IDCreater.uuid());
				commonContentAudit.setBiz(biz);
				commonContentAudit.setBid(bid);
				commonContentAudit.setContent(badWords);
				statesUserStatesAuditMapper.insertSelective(commonContentAudit);
				logger.debug(commonContentAudit.toString());
				statesUserStates.setState("4");// 未通过 TODO 放倒枚举类里
				logger.debug(statesUserStates.toString());
				statesUserStatesRepository.updateSelective(statesUserStates);
			} else {
				logger.debug("用户动态-内容审核-通过");
				statesUserStates.setState("0");// 通过 TODO 放倒枚举类里
				statesUserStatesRepository.updateSelective(statesUserStates);
			}
		} catch (Exception e) {
			logger.error("userStatesHandler",e);
		}
	}

	private void bbsNoteHandler(String biz, String bid, String content) {
		String wordfilter = commonConfig.get("wordfilter","enable");
		logger.debug("biz="+biz+" ; bid="+bid+" ; wordfilter="+wordfilter);
		try {
			// content_audit 审核任务
			// bbs_note 帖子
			// state : NoteState 枚举
			// report_times :举报次数
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, bid);
			if("disable".equalsIgnoreCase(wordfilter)){
				logger.debug("后台已关闭审核--全部通过");
				note.setState(NoteState.PASS.getCode());//审核通过
				noteRepository.insertOrUpdateSelective(note, NoteState.PASS);
				return;
			}
			logger.debug("开始自动审核...");
			FilteredResult result = WordFilterUtil.filterText(content, '*');
			String badWords = result.getBadWords();// 异常词
			if (badWords != null && badWords.trim().length() > 0) {
				logger.debug("内容审核-不通过 badWords=" + badWords);
				StatesUserStatesAudit commonContentAudit = new StatesUserStatesAudit();
				commonContentAudit.setId(IDCreater.uuid());
				commonContentAudit.setBiz(biz);
				commonContentAudit.setBid(bid);
				commonContentAudit.setContent(badWords);
				statesUserStatesAuditMapper.insertSelective(commonContentAudit);
				logger.debug(commonContentAudit.toString());
				note.setState(NoteState.REJECT.getCode());//审核拒绝
				logger.debug(note.toString());
				noteRepository.insertOrUpdateSelective(note, NoteState.REJECT);
			}else{
				logger.debug("审核通过");
				note.setState(NoteState.PASS.getCode());//审核通过
				noteRepository.insertOrUpdateSelective(note, NoteState.PASS);	
			}
		} catch (Exception e) {
			logger.error("userStatesHandler",e);
		}
	}
	
}
