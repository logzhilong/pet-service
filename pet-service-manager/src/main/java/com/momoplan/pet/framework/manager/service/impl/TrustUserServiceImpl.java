package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.service.TrustUserService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.Petuser;
import com.momoplan.pet.framework.manager.vo.WebUser;

@Service
public class TrustUserServiceImpl implements TrustUserService {
	Logger logger = LoggerFactory.getLogger(TrustUserServiceImpl.class);
	@Resource
	private MgrTrustUserMapper trustUserMapper = null;
	private CommonConfig commonConfig=new CommonConfig();
	@SuppressWarnings("static-access")
	@Override
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean,HttpServletRequest request)throws Exception {
		logger.debug("welcome to AllTrustUser.....................");
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria=trustUserCriteria.createCriteria();
		SessionManager manager = null;
		WebUser user1 = manager.getCurrentUser(request);
		criteria.andNrootIdEqualTo(user1.getId());
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
			if (object.getBoolean("success")) {
				if (res.indexOf("entity") >= 0) {
			JSONObject object1 = new JSONObject(object.getString("entity"));
			user.setNrootId(object1.getString("nickname"));
				}}
		}
		pageBean.setData(trustUserlist);
		pageBean.setTotalRecorde(totalCount);
		return pageBean;
	}

	@SuppressWarnings("static-access")
	@Override
	public void addOrUpdatetrust(Petuser petuser,HttpServletRequest request) throws Exception {
		logger.debug("welcome to addOrUpdatetrust.....................");
		if("".equals(petuser.getId()) || null==petuser.getId())
		{
			String img=petuser.getImg();
			if( null != img && "" != img){
			petuser.setImg(img.substring(img.indexOf("get")+4, 83));
			}
			String url = commonConfig.get("service.uri.pet_sso", null);
			String body = "{\"method\":\"register\",\"params\":{\"hobby\":\""+ petuser.getHobby() + "\",\"img\":\""+ petuser.getImg() + "\",\"signature\":\""+ petuser.getSignature() + "\",\"gender\":\""+ petuser.getGender() + "\",\"nickname\":\""+ petuser.getNickname() + "\",\"phonenumber\":\""+ petuser.getPhonenumber() + "\",\"password\":\""+ petuser.getPassword() + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			JSONObject object = new JSONObject(res);
			if (object.getBoolean("success")) {
				if (res.indexOf("entity") >= 0) {
			JSONObject object1 = new JSONObject(object.getString("entity"));
			String userid=object1.getString("userid");
			MgrTrustUser mgrTrustUser=new MgrTrustUser();
			mgrTrustUser.setId(IDCreater.uuid());
			SessionManager manager = null;
			WebUser user = manager.getCurrentUser(request);
			mgrTrustUser.setNrootId(user.getId());
			mgrTrustUser.setUserId(userid);
			trustUserMapper.insertSelective(mgrTrustUser);
				}
				}
		}
		else{
			String img=petuser.getImg();
			if( null != img && "" != img){
				petuser.setImg(img.substring(img.indexOf("get")+4, 83));
			}
			String url = commonConfig.get("service.uri.pet_user", null);
			String body = "{\"method\":\"updateUser\",\"params\":{\"userid\":\""+ petuser.getId() + "\",\"nickname\":\""+ petuser.getNickname() + "\",\"phonenumber\":\""+ petuser.getPhonenumber() + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			JSONObject object = new JSONObject(res);
			if(object.getString("entity").equals("OK")){
				logger.debug("修改用户信息成功!");
			}else {
				logger.debug("修改用户信息失败!");
			}
		}
		
	}

	@Override
	public Petuser getPetUserByid(Petuser petuser) throws Exception {
		logger.debug("welcome to getPetUserByid.....................");
		String url = commonConfig.get("service.uri.pet_user", null);
		String body = "{\"method\":\"getUserinfo\",\"params\":{\"userid\":\""+ petuser.getId() + "\"}}";
		String res = PostRequest.postText(url, "body", body.toString());
		JSONObject object = new JSONObject(res);
		if (object.getBoolean("success")) {
			if (res.indexOf("entity") >= 0) {
			JSONObject object1 = new JSONObject(object.getString("entity"));
				petuser.setNickname(object1.getString("nickname"));
				petuser.setPhonenumber(object1.getString("phoneNumber"));
				petuser.setId(object1.getString("id"));
				petuser.setCreatetime(object1.getString("createTime"));
				petuser.setHobby(object1.getString("hobby"));
				petuser.setGender(object1.getString("gender"));
				petuser.setSignature(object1.getString("signature"));
				petuser.setImg(object1.getString("img"));
			}
		}
		return petuser;
	}

	@Override
	public void delPetUser(Petuser petuser) throws Exception {
		logger.debug("welcome to delPetUser.....................");
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();getClass();
		MgrTrustUserCriteria.Criteria criteria=trustUserCriteria.createCriteria();
		criteria.andIdEqualTo(petuser.getId());
		trustUserMapper.deleteByExample(trustUserCriteria);
	}

}
