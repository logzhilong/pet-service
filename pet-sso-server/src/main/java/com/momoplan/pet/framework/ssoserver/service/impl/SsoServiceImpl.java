package com.momoplan.pet.framework.ssoserver.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoAuthenticationTokenMapper;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoChatServerMapper;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoChatServer;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoChatServerCriteria;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUserCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.ssoserver.service.SsoService;
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;
/**
 * TODO 关于 token 相关的操作，应该在 redis 中完成，此处留一个作业
 * @author liangc
 */
@Service
public class SsoServiceImpl implements SsoService {

	private Logger logger = LoggerFactory.getLogger(SsoServiceImpl.class);
	
	private SsoUserMapper ssoUserMapper = null;
	private SsoAuthenticationTokenMapper ssoAuthenticationTokenMapper = null;
	private SsoChatServerMapper ssoChatServerMapper = null;
	private Gson gson = new Gson();
	private CommonConfig commonConfig = null;

	@Autowired
	public SsoServiceImpl(SsoUserMapper ssoUserMapper, SsoAuthenticationTokenMapper ssoAuthenticationTokenMapper, SsoChatServerMapper ssoChatServerMapper, CommonConfig commonConfig) {
		super();
		this.ssoUserMapper = ssoUserMapper;
		this.ssoAuthenticationTokenMapper = ssoAuthenticationTokenMapper;
		this.ssoChatServerMapper = ssoChatServerMapper;
		this.commonConfig = commonConfig;
	}

	@Override
	public SsoAuthenticationToken register(SsoUser user) throws Exception {
		if(getSsoUserByName(user.getUsername())!=null){
			throw new Exception("用户名 "+user.getUsername()+" 已存在");
		}
		ssoUserMapper.insert(user);
		user = getSsoUserByName(user.getUsername());
		logger.debug("register : "+user.toString());
		SsoAuthenticationToken token = createToken(user.getId());
		logger.debug("token : "+gson.toJson(token));
		return token;
	}
	
	/**
	 * 产生一个 token
	 * TODO 每个 TOKEN 都是临时的，要找到一个对策来清理过期不用的 TOKEN，等解耦以后再处理
	 * @param userId
	 * @return
	 */
	private SsoAuthenticationToken createToken(Long userId) {
		SsoAuthenticationToken authenticationToken = new SsoAuthenticationToken();
		authenticationToken.setExpire(-1L);
		authenticationToken.setToken(IDCreater.uuid());
		authenticationToken.setUserid(userId);
		authenticationToken.setCreateDate(new Date());
		ssoAuthenticationTokenMapper.insertSelective(authenticationToken);
		return authenticationToken;
	}

	@Override
	public LoginResponse login(SsoUser user) throws Exception {
		try{
			SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
			ssoUserCriteria.createCriteria().andUsernameEqualTo(user.getUsername());
			List<SsoUser> ssoUserList = ssoUserMapper.selectByExample(ssoUserCriteria);
			if(ssoUserList==null||ssoUserList.size()<=0){
				throw new Exception("用户不存在");
			}
			SsoUser u = ssoUserList.get(0);
			if(user.getPassword()==null||!user.getPassword().equals(u.getPassword())){
				throw new Exception("密码错误");
			}
			u.setDeviceToken(user.getDeviceToken());
			ssoUserMapper.updateByPrimaryKeySelective(u);
			SsoAuthenticationToken token = createToken(u.getId());
			
			String xmppDomain = commonConfig.get("xmpp.domain");
			SsoChatServerCriteria ssoChatServerCriteria = new SsoChatServerCriteria();
			ssoChatServerCriteria.createCriteria().andNameEqualTo(xmppDomain);
			List<SsoChatServer> ssoChatServerList = ssoChatServerMapper.selectByExample(ssoChatServerCriteria);
			SsoChatServer ssoChatServer = ssoChatServerList.get(0);
			LoginResponse response = new LoginResponse(token,ssoChatServer);
			return response;
		}catch(Exception e){
			throw new Exception("login error :",e);
		}
	}
	
	private SsoUser getSsoUserByName(String username){
		SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
		ssoUserCriteria.createCriteria().andUsernameEqualTo(username);
		List<SsoUser> ssoUserList = ssoUserMapper.selectByExample(ssoUserCriteria);
		if(ssoUserList!=null&&ssoUserList.size()>0)
			return ssoUserList.get(0);
		return null;
	}

	@Override
	public SsoAuthenticationToken getToken(String token) throws Exception {
		SsoAuthenticationToken obj = ssoAuthenticationTokenMapper.selectByPrimaryKey(token);
		if(obj==null)
			throw new Exception("TOKEN 无效或已过期");
		return obj;
	}

	@Override
	public void updatePassword(SsoUser user) throws Exception {
		SsoUser ssoUser = getSsoUserByName(user.getUsername());
		ssoUser.setPassword(user.getPassword());
		ssoUserMapper.updateByPrimaryKey(ssoUser);
	}

	@Override
	public void logout(String token) throws Exception {
		ssoAuthenticationTokenMapper.deleteByPrimaryKey(token);
	}
}
