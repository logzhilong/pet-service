package com.momoplan.pet.framework.user.handler.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoUserCriteria;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.service.UserService;

/**
 * {"method":"adminFlushSearchUserIndex","params":{"uid":"all","pwd":"abc123"}}
 * 更新用户索引，单条件搜索时的索引
 * @author liangc
 */
@Component("adminFlushSearchUserIndex")
public class AdminFlushSearchUserIndex extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AdminFlushSearchUserIndex.class);
	
	@Autowired
	private SsoUserMapper ssoUserMapper = null;
	@Autowired
	private StorePool storePool = null;
	private static String PWD = "abc123";
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String _uid = getParameter(clientRequest, "uid");
			String _pwd = getParameter(clientRequest, "pwd");
			String keys = UserService.SEARCH_USER_INDEX;
			if(!PWD.equals(_pwd)){
				String m = "错误的口令 pwd="+_pwd;
				logger.debug(m);
				throw new Exception(m);
			}
			SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
			if(_uid!=null&&!"all".equalsIgnoreCase(_uid)){
				ssoUserCriteria.createCriteria().andIdEqualTo(_uid);
				keys = keys+_uid;
			}
			List<SsoUser> list = ssoUserMapper.selectByExample(ssoUserCriteria);
			Set<String> keyset = storePool.keys(keys+"*");
			if(keyset!=null&&keyset.size()>0){
				String[] ka = keyset.toArray(new String[keyset.size()]);
				storePool.del(ka);
				logger.debug("清除用户索引: ka="+ka);
			}
			for(SsoUser u:list){
				String uid = u.getId();
				String name = u.getUsername();
				String nick = u.getNickname();
				String indexKey = keys+uid+":"+name+":"+nick;
				storePool.set(indexKey, uid);
				logger.debug("创建用户索引: key="+indexKey+" ; value="+uid);
			}
			rtn = new Success(sn,true,list.size()).toString();
			logger.debug("刷新索引 成功 ");
		}catch(Exception e){
			logger.debug("刷新索引 失败 ");
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
