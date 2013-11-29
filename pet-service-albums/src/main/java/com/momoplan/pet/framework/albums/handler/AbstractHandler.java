package com.momoplan.pet.framework.albums.handler;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.commons.web.BaseController;

public abstract class AbstractHandler extends BaseController implements RequestHandler {
	
	protected Gson gson = MyGson.getInstance();
	
	protected CommonConfig commonConfig = Bootstrap.getBean(CommonConfig.class);

	protected MapperOnCache mapperOnCache = Bootstrap.getBean(MapperOnCache.class);

}
