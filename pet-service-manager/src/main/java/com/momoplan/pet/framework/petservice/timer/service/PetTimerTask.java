package com.momoplan.pet.framework.petservice.timer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTimerTaskMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTask;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTaskCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteState;
import com.momoplan.pet.framework.petservice.bbs.service.NoteService;
import com.momoplan.pet.framework.petservice.push.service.PushService;
import com.momoplan.pet.framework.petservice.push.vo.PushState;
import com.momoplan.pet.framework.petservice.timer.vo.TimerState;

@Component
public class PetTimerTask extends TimerTask {
	
	private static Logger logger = LoggerFactory.getLogger(PetTimerTask.class);
	
	@Autowired
	private MgrTimerTaskMapper mgrTimerTaskMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private PushService pushService = null;
	@Autowired
	private NoteService noteService = null;
	
	private Map<String,Object> getTaskList(){
		MgrTimerTaskCriteria mgrTimerTaskCriteria = new MgrTimerTaskCriteria();
		MgrTimerTaskCriteria.Criteria criteria = mgrTimerTaskCriteria.createCriteria();
		criteria.andStateEqualTo(TimerState.NEW.getCode());//获取等待执行的任务
		criteria.andAtLessThanOrEqualTo(DateUtils.now());//获取当前可执行的任务
		List<MgrTimerTask> task = mgrTimerTaskMapper.selectByExample(mgrTimerTaskCriteria);
		int count = mgrTimerTaskMapper.countByExample(mgrTimerTaskCriteria);
		Map<String,Object> r = new HashMap<String,Object>();
		r.put("count", count+"");
		r.put("data", task);
		return r;
	}
	
	private void runTask(MgrTimerTask task){
		try{
			String id = task.getId();
			String src = task.getSrc();
			if("mgr_push".equalsIgnoreCase(src)){
				logger.info("推送定时任务 ID="+task.getId()+" ; NAME="+task.getName()); 
				MgrPush p = mapperOnCache.selectByPrimaryKey(MgrPush.class, id);
				p.setState(PushState.PUSHED.getCode());//改成已推送状态
				p.setSrc(null);//推送时，如果有这个参数，会认为是插入推送记录，而不是发送推送指令
				pushService.save(p, "OK");//推出去
			}else if("bbs_note".equalsIgnoreCase(src)){
				logger.info("发帖定时任务 ID="+task.getId()+" ; NAME="+task.getName()); 
				Note p = mapperOnCache.selectByPrimaryKey(Note.class, id);
				p.setState(NoteState.PASS.getCode());
				noteService.saveNote(p, null,null);
			}
			task.setEt(DateUtils.now());
			task.setState(TimerState.FINISHED.getCode());
			mapperOnCache.updateByPrimaryKeySelective(task, id);
			logger.info("定时任务执行完毕...");
		}catch(Exception e){
			logger.error("timer task error",e);
		}
	}
	
	@Override
	public void run() {
		Map<String,Object> tm = getTaskList();
		String count = (String)tm.get("count");
		@SuppressWarnings("unchecked")
		List<MgrTimerTask> list = (List<MgrTimerTask>)tm.get("data");
		logger.info("timer_task_count："+count);
		for(MgrTimerTask task : list){
			runTask(task);
		}
	}

}
