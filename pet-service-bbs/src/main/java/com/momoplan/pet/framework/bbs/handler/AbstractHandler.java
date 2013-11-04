package com.momoplan.pet.framework.bbs.handler;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.commons.web.BaseController;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
import com.momoplan.pet.framework.bbs.service.impl.ForumServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.NoteServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.NoteSubServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.UserForumRelServiceImpl;

public abstract class AbstractHandler extends BaseController implements RequestHandler {
	
	protected ForumService  forumService = Bootstrap.getBean(ForumServiceImpl.class);
	protected NoteService noteService= Bootstrap.getBean(NoteServiceImpl.class);
	protected NoteSubService noteSubService=Bootstrap.getBean(NoteSubServiceImpl.class);
	protected UserForumRelService userForumRelService =Bootstrap.getBean(UserForumRelServiceImpl.class);
	
	protected MapperOnCache mapperOnCache =Bootstrap.getBean(MapperOnCache.class);
	protected CommonConfig commonConfig =Bootstrap.getBean(CommonConfig.class);
	
}
