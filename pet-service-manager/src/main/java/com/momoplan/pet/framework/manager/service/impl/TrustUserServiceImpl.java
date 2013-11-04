package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.service.TrustUserService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.Petuser;

@Service
public class TrustUserServiceImpl implements TrustUserService {
	Logger logger = LoggerFactory.getLogger(TrustUserServiceImpl.class);
	@Resource
	private MgrTrustUserMapper trustUserMapper = null;
	private CommonConfig commonConfig=new CommonConfig();
	@Override
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean)
			throws Exception {
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		int totalCount = trustUserMapper.countByExample(trustUserCriteria);
		trustUserCriteria.setMysqlOffset((pageBean.getPageNo() - 1)* pageBean.getPageSize());
		trustUserCriteria.setMysqlLength(pageBean.getPageSize());
		List<MgrTrustUser> trustUserlist = trustUserMapper.selectByExample(trustUserCriteria);
		String url = commonConfig.get("service.uri.pet_user", null);
		for (MgrTrustUser user : trustUserlist) {
			String uid = user.getUserId();
			String body = "{\"method\":\"getUserinfo\",\"params\":{\"userid\":\""+ uid + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			JSONObject object = new JSONObject(res);
			JSONObject object1 = new JSONObject(object.getString("entity"));
			user.setNrootId(object1.getString("nickname"));
		}
		pageBean.setData(trustUserlist);
		pageBean.setTotalRecorde(totalCount);
		return pageBean;
	}

	@Override
	public void addOrUpdatetrust(Petuser petuser) throws Exception {
		if("".equals(petuser.getId()) || null==petuser.getId())
		{
			String url = commonConfig.get("service.uri.pet_sso", null);
			String body = "{\"method\":\"register\",\"params\":{\"nickname\":\""+ petuser.getNickname() + "\",\"phonenumber\":\""+ petuser.getPhonenumber() + "\",\"password\":\""+ petuser.getPassword() + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			JSONObject object = new JSONObject(res);
			JSONObject object1 = new JSONObject(object.getString("entity"));
			String userid=object1.getString("userid");
			MgrTrustUser mgrTrustUser=new MgrTrustUser();
			mgrTrustUser.setId(IDCreater.uuid());
			mgrTrustUser.setNrootId("11");
			mgrTrustUser.setUserId(userid);
			trustUserMapper.insertSelective(mgrTrustUser);
		}
		else{
			
		}
		
	}

}
