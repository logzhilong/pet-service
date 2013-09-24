package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* ForumMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-24 11:11:36
*/
public interface ForumMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(ForumCriteria forumCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(ForumCriteria forumCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Forum forum);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Forum forum);

    /**
     * 根据条件查询记录集
     */
    List<Forum> selectByExample(ForumCriteria forumCriteria);

    /**
     * 根据主键查询记录
     */
    Forum selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("forum") Forum forum, @Param("forumCriteria") ForumCriteria forumCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("forum") Forum forum, @Param("forumCriteria") ForumCriteria forumCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Forum forum);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Forum forum);
}