package com.momoplan.pet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.momoplan.pet.domain.PetInfo;
import com.momoplan.pet.domain.PetUser;
import com.momoplan.pet.domain.UserFriendship;
import com.momoplan.pet.domain.UserZan;
import com.momoplan.pet.vo.PetInfoView;
import com.momoplan.pet.vo.PetUserWithPetInfoView;

@Component
public class PetUserDAO {
	@PersistenceContext
	transient EntityManager entityManager;
    public List<PetUser> selectUserByNameOrUserId(String userNameOrId) {
    	TypedQuery<PetUser> createQuery = entityManager.createQuery("SELECT o FROM PetUser o where o.id = :userId or o.username = :userName",PetUser.class);
    	boolean judge;
    	//try处理String与long转换问题
    	try {
			   Integer.parseInt(userNameOrId);
			   judge = true;
			} catch (NumberFormatException e) {
			   judge = false;
			  }
		if(judge == true){
			createQuery.setParameter("userId", Long.parseLong(userNameOrId));//能转换为long则传入参数
		}else{
			createQuery.setParameter("userId", Long.parseLong("-1"));//不能转换为long则传入-1
		}
        createQuery.setParameter("userName",userNameOrId);
		return createQuery.getResultList();
    }
    
	/**
	 * 获取用户列表
	 * @param petuser
	 * @param pageIndex
	 * @return
	 */
	public List<PetUserWithPetInfoView> getPetUserViews(PetUser petuser, int pageIndex) {
		List<PetUserWithPetInfoView> petUserViews = new ArrayList<PetUserWithPetInfoView>();
		String userName;
		String nickName;
		TypedQuery<PetUser> createQuery = entityManager.createQuery("SELECT o FROM PetUser o where o.username like :userName or o.nickname like :nickName",
						PetUser.class);
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		//处理like的非空字段
		if(null==petuser.getUsername()||""==petuser.getUsername()){
			userName = "";
			nickName = "";
		}else{
			userName = "%" + petuser.getUsername() + "%";
			nickName = "%" + petuser.getUsername() + "%";
		}
//		if(null==petuser.getNickname()||""==petuser.getNickname()){
//			nickName = "";
//		}else{
//			nickName = "%" + petuser.getNickname() + "%";
//		}
		createQuery.setParameter("userName", userName);
		createQuery.setParameter("nickName", nickName);
		List<PetUser> petUsers = createQuery.getResultList();
		for (PetUser aPetUser : petUsers) {
			PetUserWithPetInfoView petUserView = new PetUserWithPetInfoView(); 
			TypedQuery<PetInfo> petInfoQuery = entityManager.createQuery("SELECT o FROM PetInfo o WHERE o.userid = :userId ",PetInfo.class);
			long userId = aPetUser.getId();
			petInfoQuery.setParameter("userId", userId);
			List<PetInfoView> petInfoViews = getPetInfoView(userId);
			petUserView.setUserid(aPetUser.getId());
			petUserView.setUsername(aPetUser.getUsername());
			petUserView.setNickname(aPetUser.getNickname());
			petUserView.setSignature(aPetUser.getSignature());
			petUserView.setImg(aPetUser.getImg());
			petUserView.setBirthdate(aPetUser.getBirthdate());
			petUserView.setGender(aPetUser.getGender());
			petUserView.setHobby(aPetUser.getHobby());
			petUserView.setPetInfoViews(petInfoViews);
			petUserView.setPageIndex(pageIndex++);
			petUserViews.add(petUserView);
		}
		return petUserViews;
	}
	
	/**
	 * 获取宠物的信息视图
	 * @param userid
	 * @return
	 */
	private List<PetInfoView> getPetInfoView(long userid) {
		List<PetInfo> petInfos = PetInfo.findPetInfoesByUserid(userid).getResultList();
		List<PetInfoView> petInfoViews  = new ArrayList<PetInfoView>();
		for (PetInfo petInfo : petInfos) {
			PetInfoView petInfoView = new PetInfoView();
			petInfoView.setBirthdate(petInfo.getBirthdate());
			petInfoView.setGender(petInfo.getGender());
			petInfoView.setImg(petInfo.getImg());
			petInfoView.setNickname(petInfo.getNickname());
			petInfoView.setTrait(petInfo.getTrait());
			petInfoView.setType(petInfo.getType());
			petInfoView.setUserid(petInfo.getUserid());
			petInfoView.setId(petInfo.getId());
			petInfoView.setCountZan(UserZan.countUserZans(userid,-1, -1,petInfo.getId(),"1"));
			petInfoView.setIfIZaned(UserZan.findUserZansByUseridAndUserStateidAndZanUserid(userid,-1, -1,petInfo.getId(),"1").getResultList().isEmpty() ? "0" : "1");
			petInfoViews.add(petInfoView);
		}
		return petInfoViews;
	}
	/**
	 * 获取好友信息
	 * @param petuser
	 * @param pageIndex
	 * @return
	 */
	public List<PetUserWithPetInfoView> getFriendsView(PetUser petUser, int pageIndex) {
		List<PetUserWithPetInfoView> petUserViews = new ArrayList<PetUserWithPetInfoView>();
		TypedQuery<PetUser> createQuery = entityManager.createQuery("select pu from PetUser pu where pu.id in (select uf.bId from UserFriendship uf where uf.aId = :aId and uf.verified = 0)",
						PetUser.class);
		createQuery.setFirstResult(pageIndex);
		createQuery.setMaxResults(20);
		createQuery.setParameter("aId",petUser.getId());
		List<PetUser> petUsers = createQuery.getResultList();
		for (PetUser aPetUser : petUsers) {
			PetUserWithPetInfoView petUserView = new PetUserWithPetInfoView(); 
			TypedQuery<PetInfo> petInfoQuery = entityManager.createQuery("SELECT o FROM PetInfo o WHERE o.userid = :userId ",PetInfo.class);
			long userId = aPetUser.getId();
			petInfoQuery.setParameter("userId", userId);
			List<PetInfoView> petInfoViews = getPetInfoView(userId);
			petUserView.setUserid(aPetUser.getId());
			petUserView.setUsername(aPetUser.getUsername());
			petUserView.setNickname(aPetUser.getNickname());
			petUserView.setSignature(aPetUser.getSignature());
			petUserView.setImg(aPetUser.getImg());
			petUserView.setBirthdate(aPetUser.getBirthdate());
			petUserView.setGender(aPetUser.getGender());
			petUserView.setHobby(aPetUser.getHobby());
			petUserView.setPetInfoViews(petInfoViews);
			petUserView.setPageIndex(pageIndex++);
			petUserViews.add(petUserView);
		}
		return petUserViews;
	}

	public List<PetUserWithPetInfoView> getOneFriend(PetUser petUser,Long userid) {
		List<PetUserWithPetInfoView> petUserViews = new ArrayList<PetUserWithPetInfoView>();
		TypedQuery<PetUser> createQuery = entityManager.createQuery("select pu from PetUser pu where pu.username = :userName ",PetUser.class);
		createQuery.setParameter("userName",petUser.getUsername());
		List<PetUser> petUsers = createQuery.getResultList();
		for (PetUser aPetUser : petUsers) {
			//判断此petUser是否与用户存在好友关系，如果不存在则return null
			TypedQuery<UserFriendship> ifFriendship = entityManager.createQuery("select uf from UserFriendship uf where (uf.aId = :userid and uf.bId = :id) or (uf.aId = :id and uf.bId = :userid) and uf.verified = 0 ",UserFriendship.class);
			ifFriendship.setParameter("userid", userid);
			ifFriendship.setParameter("id", aPetUser.getId());
			List<UserFriendship> userFriendships = ifFriendship.getResultList();
			if(userFriendships.isEmpty()){
				return null;
			}
			PetUserWithPetInfoView petUserView = new PetUserWithPetInfoView(); 
			TypedQuery<PetInfo> petInfoQuery = entityManager.createQuery("SELECT o FROM PetInfo o WHERE o.userid = :userId ",PetInfo.class);
			long userId = aPetUser.getId();
			petInfoQuery.setParameter("userId", userId);
			List<PetInfoView> petInfoViews = getPetInfoView(userId);
			petUserView.setUserid(aPetUser.getId());
			petUserView.setUsername(aPetUser.getUsername());
			petUserView.setNickname(aPetUser.getNickname());
			petUserView.setSignature(aPetUser.getSignature());
			petUserView.setImg(aPetUser.getImg());
			petUserView.setBirthdate(aPetUser.getBirthdate());
			petUserView.setGender(aPetUser.getGender());
			petUserView.setHobby(aPetUser.getHobby());
			petUserView.setPetInfoViews(petInfoViews);
			petUserViews.add(petUserView);
		}
		return petUserViews;
	}

	public List<PetUser> getOneFriend(long userid, String city) {
		TypedQuery<PetUser> createQuery = entityManager.createQuery("select pu from PetUser pu where pu.userid = :userid and pu.city = :city ",PetUser.class);
		createQuery.setParameter("userid",userid);
		createQuery.setParameter("city",city);
		List<PetUser> petUsers = createQuery.getResultList();
		return petUsers;
	}
}
