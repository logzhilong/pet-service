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
	private CommonConfig commonConfig = new CommonConfig();

	@SuppressWarnings("static-access")
	@Override
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean,HttpServletRequest request) throws Exception {
		logger.debug("welcome to AllTrustUser....................."+pageBean.toString());
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria = trustUserCriteria.createCriteria();
		SessionManager manager = null;
		WebUser user1 = manager.getCurrentUser(request);
		logger.debug("获取当前用户:"+user1.toString());
		criteria.andNrootIdEqualTo(user1.getId());
		int totalCount = trustUserMapper.countByExample(trustUserCriteria);
		trustUserCriteria.setMysqlOffset((pageBean.getPageNo() - 1)* pageBean.getPageSize());
		trustUserCriteria.setMysqlLength(pageBean.getPageSize());
		List<MgrTrustUser> trustUserlist = trustUserMapper.selectByExample(trustUserCriteria);
		String url = commonConfig.get("service.uri.pet_user", null);
		for (MgrTrustUser user : trustUserlist) {
			String uid = user.getUserId();
			logger.debug("根据托管用户id....."+uid);
			String body = "{\"method\":\"getUserinfo\",\"params\":{\"userid\":\""
					+ uid + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			logger.debug("根据托管用户id获取托管用户信息"+res.toString());
			JSONObject object = new JSONObject(res);
			if (object.getBoolean("success")) {
				if (res.indexOf("entity") >= 0) {
					JSONObject object1 = new JSONObject(object.getString("entity"));
					try {
						user.setNrootId(object1.getString("nickname"));
					} catch (Exception e) {
						user.setNrootId(null);
					}
				}
			}else{
				logger.debug("获取托管用户信息失败!"+res);
			}
		}
		pageBean.setData(trustUserlist);
		pageBean.setTotalRecorde(totalCount);
		logger.debug("获取托管用户集合为........:"+pageBean.toString());
		return pageBean;
	}

	@Override
	public Petuser getPetUserByid(Petuser petuser) throws Exception {
		logger.debug("welcome to getPetUserByid....................."+petuser.toString());
		String url = commonConfig.get("service.uri.pet_user", null);
		String body = "{\"method\":\"getUserinfo\",\"params\":{\"userid\":\""+ petuser.getId() + "\"}}";
		String res = PostRequest.postText(url, "body", body.toString());
		logger.debug("根据id获取托管用户信息"+res.toString());
		JSONObject object = new JSONObject(res);
		if (object.getBoolean("success")) {
			if (res.indexOf("entity") >= 0) {
				JSONObject object1 = new JSONObject(object.getString("entity"));
				try {
					petuser.setNickname(object1.getString("nickname"));
					petuser.setPhonenumber(object1.getString("phoneNumber"));
					petuser.setId(object1.getString("id"));
					petuser.setCreatetime(object1.getString("createTime"));
					petuser.setHobby(object1.getString("hobby"));
					petuser.setGender(object1.getString("gender"));
					petuser.setSignature(object1.getString("signature"));
					petuser.setBirthdate(object1.getString("birthdate"));
					petuser.setCity(object1.getString("city"));
					petuser.setImg(object1.getString("img"));
				} catch (Exception e) {
					petuser.setNickname("此人信息不规则!请修改!");
				}
			}
		}else{
			logger.debug("获取托管用户信息失败"+res.toString());
		}
		return petuser;
	}

	@Override
	public void delPetUser(Petuser petuser) throws Exception {
		logger.debug("welcome to delPetUser.....................");
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria = trustUserCriteria.createCriteria();
		criteria.andIdEqualTo(petuser.getId());
		logger.debug("删除托管用户......"+trustUserCriteria.toString());
		trustUserMapper.deleteByExample(trustUserCriteria);
	}

	@SuppressWarnings("static-access")
	@Override
	public int addOrUpdatetrust(Petuser petuser, HttpServletRequest request)throws Exception {
		logger.debug("welcome to addOrUpdatetrust....................."+petuser);
		if ("".equals(petuser.getId()) || null == petuser.getId()) {
			logger.debug("增加托管用户....................."+petuser.toString());
			String img = petuser.getImg();
			if (null != img && "" != img) {
				petuser.setImg(img.substring(img.indexOf("get") + 4, 83) + "_"+ img.substring(img.indexOf("get") + 4, 83) + ",");
			} else {
				petuser.setImg("AD915C3A6B9F4043B9F60E3BB736C728_AD915C3A6B9F4043B9F60E3BB736C728,");
				logger.debug("增加托管用户时,未上传头像图片,暂时给默认头像:"+"AD915C3A6B9F4043B9F60E3BB736C728");
			}
			String url = commonConfig.get("service.uri.pet_sso", null);
			String body = "{\"method\":\"register\",\"params\":{\"birthdate\":\""+ petuser.getBirthdate() + "\",\"hobby\":\""+ petuser.getHobby() + "\",\"img\":\"" + petuser.getImg()+ "\",\"signature\":\"" + petuser.getSignature()+ "\",\"gender\":\"" + petuser.getGender()+ "\",\"nickname\":\"" + petuser.getNickname()+ "\",\"phonenumber\":\"" + petuser.getPhonenumber()+ "\",\"city\":\"" + petuser.getCity()+ "\",\"password\":\"" + petuser.getPassword() + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			logger.debug("增加托管用户返回值.....................:"+res);
			JSONObject object = new JSONObject(res);
			if (object.getBoolean("success")) {
				logger.debug("增加托管用户成功:"+res);
				if (res.indexOf("entity") >= 0) {
					JSONObject object1 = new JSONObject(object.getString("entity"));
					String userid;
					try {
						userid = object1.getString("userid");
					} catch (Exception e) {
						userid = "null";
					}
					MgrTrustUser mgrTrustUser = new MgrTrustUser();
					mgrTrustUser.setId(IDCreater.uuid());
					SessionManager manager = null;
					WebUser user = manager.getCurrentUser(request);
					mgrTrustUser.setNrootId(user.getId());
					mgrTrustUser.setUserId(userid);
					logger.debug("增加托管用户信息..............:"+mgrTrustUser);
					trustUserMapper.insertSelective(mgrTrustUser);
				}
				return 1;
			}
			else{
				return 0;
			}
		} else {
			
			String img = petuser.getImg();
			if (null != img && "" != img) {
				petuser.setImg(img.substring(img.indexOf("get") + 4, 83) + "_"+ img.substring(img.indexOf("get") + 4, 83) + ",");
			}else{
				petuser.setImg("AD915C3A6B9F4043B9F60E3BB736C728_AD915C3A6B9F4043B9F60E3BB736C728,");
				logger.debug("修改托管用户时,未上传头像图片,暂时给默认头像:"+"AD915C3A6B9F4043B9F60E3BB736C728");
			}
			logger.debug("修改托管用户信息.............."+"name"+petuser.getNickname()+"id"+petuser.getId()+"img"+petuser.getImg());
			String url = commonConfig.get("service.uri.pet_user", null);
			String body = "{\"method\":\"updateUser\",\"params\":{\"img\":\""+ petuser.getImg()+ "\",\"birthdate\":\""+ petuser.getBirthdate()+ "\",\"gender\":\""+ petuser.getGender()+ "\",\"userid\":\""+ petuser.getId()+ "\",\"nickname\":\""+ petuser.getNickname()+ "\",\"phonenumber\":\""+ petuser.getPhonenumber() + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			logger.debug("修改托管用户,返回值:"+res);
			JSONObject object = new JSONObject(res);
			if (object.getString("entity").equals("OK")) {
				logger.debug("修改用户信息成功!");
				return 1;
			} else {
				logger.debug("修改用户信息失败!");
				return 0;
			}
			
		}
	}

}
