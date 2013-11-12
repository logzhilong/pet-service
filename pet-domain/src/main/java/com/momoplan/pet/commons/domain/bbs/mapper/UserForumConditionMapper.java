package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.commons.domain.bbs.po.UserForumConditionCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* UserForumConditionMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-12 11:25:18
*/
public interface UserForumConditionMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(UserForumConditionCriteria userForumConditionCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserForumConditionCriteria userForumConditionCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserForumCondition userForumCondition);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserForumCondition userForumCondition);

    /**
     * 根据条件查询记录集
     */
    List<UserForumCondition> selectByExample(UserForumConditionCriteria userForumConditionCriteria);

    /**
     * 根据主键查询记录
     */
    UserForumCondition selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("userForumCondition") UserForumCondition userForumCondition, @Param("userForumConditionCriteria") UserForumConditionCriteria userForumConditionCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("userForumCondition") UserForumCondition userForumCondition, @Param("userForumConditionCriteria") UserForumConditionCriteria userForumConditionCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserForumCondition userForumCondition);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserForumCondition userForumCondition);
}