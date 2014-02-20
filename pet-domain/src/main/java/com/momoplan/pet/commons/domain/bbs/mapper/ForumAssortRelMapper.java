package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRel;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRelCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* ForumAssortRelMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-02-20 10:40:08
*/
public interface ForumAssortRelMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(ForumAssortRelCriteria forumAssortRelCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(ForumAssortRelCriteria forumAssortRelCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ForumAssortRel forumAssortRel);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ForumAssortRel forumAssortRel);

    /**
     * 根据条件查询记录集
     */
    List<ForumAssortRel> selectByExample(ForumAssortRelCriteria forumAssortRelCriteria);

    /**
     * 根据主键查询记录
     */
    ForumAssortRel selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("forumAssortRel") ForumAssortRel forumAssortRel, @Param("forumAssortRelCriteria") ForumAssortRelCriteria forumAssortRelCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("forumAssortRel") ForumAssortRel forumAssortRel, @Param("forumAssortRelCriteria") ForumAssortRelCriteria forumAssortRelCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ForumAssortRel forumAssortRel);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ForumAssortRel forumAssortRel);
}