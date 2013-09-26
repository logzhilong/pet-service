package com.momoplan.pet.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;

@TransactionConfiguration(defaultRollback=true,transactionManager="bbsTransactionManager")
public class ForumMapperTest extends AbstractTest {
	
	@Resource
	ForumMapper forumMapper = null;
	
	public void insertTest() {
		String id = IDCreater.uuid();
		Forum forum = new Forum();
		forum.setId(id);
		forum.setCt(new Date());
		forum.setCb("admin");
		forum.setName("测试006");
		forumMapper.insertSelective(forum);
		System.out.println("OK...");
	}
	@Test
	public void selectTest(){
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andNameLike("%测试%");
		
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		System.out.println(list.size());
		for(Forum f : list){
			System.out.println(f.toString());
		}
		Assert.assertNotNull(list);
	}

	public void updateTest(){
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andNameLike("%测试%");
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		System.out.println(list.size());
		for(Forum forum : list){
			forum.setCt(new Date());
			forum.setCb("liangc-test");
			forum.setName("测试");
			forum.setDescript("描述：测试");
			forumMapper.updateByPrimaryKeySelective(forum);
		}
		System.out.println("OK..");
	}

	
	public void deleteTest(){
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andNameLike("%测试%");
		forumMapper.deleteByExample(forumCriteria);
		System.out.println("ok..");
	}
	
}
