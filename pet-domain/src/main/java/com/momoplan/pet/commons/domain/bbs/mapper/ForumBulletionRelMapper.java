package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.ForumBulletionRel;
import com.momoplan.pet.commons.domain.bbs.po.ForumBulletionRelCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* ForumBulletionRelMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-26 12:09:32
*/
public interface ForumBulletionRelMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(ForumBulletionRelCriteria forumBulletionRelCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(ForumBulletionRelCriteria forumBulletionRelCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ForumBulletionRel forumBulletionRel);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ForumBulletionRel forumBulletionRel);

    /**
     * 根据条件查询记录集
     */
    List<ForumBulletionRel> selectByExample(ForumBulletionRelCriteria forumBulletionRelCriteria);

    /**
     * 根据主键查询记录
     */
    ForumBulletionRel selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("forumBulletionRel") ForumBulletionRel forumBulletionRel, @Param("forumBulletionRelCriteria") ForumBulletionRelCriteria forumBulletionRelCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("forumBulletionRel") ForumBulletionRel forumBulletionRel, @Param("forumBulletionRelCriteria") ForumBulletionRelCriteria forumBulletionRelCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ForumBulletionRel forumBulletionRel);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ForumBulletionRel forumBulletionRel);
}