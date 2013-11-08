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
import com.momoplan.pet.commons.domain.user.mapper.UserFriendshipMapper;
import com.momoplan.pet.commons.domain.user.po.UserFriendship;
import com.momoplan.pet.commons.domain.user.po.UserFriendshipCriteria;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.service.UserService;

/**
 * adminFlushFriendShipIndex
 * {"method":"adminFlushFriendShipIndex",params:{"pwd":"abc123"}}
 * 更新好友列表索引
 * @author liangc
 */
@Component("adminFlushFriendShipIndex")
public class AdminFlushFriendShipIndex extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AdminFlushFriendShipIndex.class);
	
	@Autowired
	private UserFriendshipMapper userFriendshipMapper = null;
	@Autowired
	private StorePool storePool = null;
	private static String PWD = "abc123";
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String _pwd = getParameter(clientRequest, "pwd");
			String keys = UserService.FRIEND_KEY;
			
			if(!PWD.equals(_pwd)){
				String m = "错误的口令 pwd="+_pwd;
				logger.debug(m);
				throw new Exception(m);
			}

			UserFriendshipCriteria userFriendshipCriteria = new UserFriendshipCriteria();
			List<UserFriendship> list = userFriendshipMapper.selectByExample(userFriendshipCriteria);
			
			Set<String> keyset = storePool.keys(keys+"*");
			if(keyset!=null&&keyset.size()>0){
				String[] ka = keyset.toArray(new String[keyset.size()]);
				storePool.del(ka);
			}
			
			for(UserFriendship po:list){
				String id = po.getId();
				String aid = po.getaId();
				String bid = po.getbId();
				String indexKey = UserService.FRIEND_KEY+id+":"+aid+bid;
				po.setRemark(null);
				po.setVerified(null);
				String indexValue = gson.toJson(po);
				storePool.set(indexKey, indexValue);
				logger.debug("好友列表索引[更新]:key="+indexKey+" ; value="+indexValue);
			}
			
			rtn = new Success(true,list.size()).toString();
			logger.debug("刷新索引 成功 ");
		}catch(Exception e){
			logger.debug("刷新索引 失败 ");
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
