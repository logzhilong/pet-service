package com.momoplan.pet.framework.wordfilter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.states.mapper.CommonContentAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.po.CommonContentAudit;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.wordfilter.WordFilterUtil;
import com.momoplan.pet.commons.wordfilter.result.FilteredResult;
import com.momoplan.pet.framework.wordfilter.service.WordFilterService;

@Service
public class WordFilterServiceImpl implements WordFilterService {
	
	private Logger logger = LoggerFactory.getLogger(WordFilterServiceImpl.class);

	private StatesUserStatesMapper statesUserStatesMapper = null;
	private CommonContentAuditMapper commonContentAuditMapper = null;
	
	@Autowired
	public WordFilterServiceImpl(StatesUserStatesMapper statesUserStatesMapper, CommonContentAuditMapper commonContentAuditMapper) {
		super();
		this.statesUserStatesMapper = statesUserStatesMapper;
		this.commonContentAuditMapper = commonContentAuditMapper;
	}
	@Override
	public void doFilter(String biz, String id, String content) {
		if("user_states".equalsIgnoreCase(biz)){//用户动态内容过滤
			userStatesHandler(biz,id,content);
		}else{
			logger.info("[ERR:10001] 不能识别的过滤类型 biz="+biz);
			return;
		}
	}
	
	private void userStatesHandler(String biz, String bid, String content){
		//content_audit 审核任务
		//user_states 动态
		//state_type :0正常，1假动态，2置顶动态，3审核中，4未通过，5被举报
		//report_times :举报次数
		FilteredResult result = WordFilterUtil.filterText(content, '*');
		String badWords = result.getBadWords();//异常词
		if(badWords!=null&&badWords.trim().length()>0){
			logger.debug("用户动态-内容审核-不通过 badWords="+badWords);
			CommonContentAudit commonContentAudit = new CommonContentAudit();
			commonContentAudit.setBiz(biz);
			commonContentAudit.setBid(bid);
			commonContentAudit.setContent(badWords);
			commonContentAuditMapper.insertSelective(commonContentAudit);
			logger.debug(commonContentAudit.toString());
			StatesUserStates statesUserStates = statesUserStatesMapper.selectByPrimaryKey(Long.parseLong(bid));
			statesUserStates.setStateType("4");//未通过 TODO 放倒枚举类里
			statesUserStatesMapper.updateByPrimaryKeySelective(statesUserStates);
		}else{
			logger.debug("用户动态-内容审核-通过");
			StatesUserStates statesUserStates = statesUserStatesMapper.selectByPrimaryKey(Long.parseLong(bid));
			statesUserStates.setStateType("0");//未通过 TODO 放倒枚举类里
			statesUserStatesMapper.updateByPrimaryKeySelective(statesUserStates);
		}
	}
	
}
