package com.momoplan.pet.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.vo.ForumNode;

public class ForumServiceTest extends AbstractTest {

	@Autowired
	private ForumService forumService = null;
	
	@Test
	public void forumTreeTest() throws Exception{
		List<ForumNode> tree = forumService.getAllForumAsTree("");
		System.out.println(MyGson.getInstance().toJson(tree));
	}
}
