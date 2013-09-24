package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* UserForumRelMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-24 11:11:36
*/
public interface UserForumRelMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(UserForumRelCriteria userForumRelCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserForumRelCriteria userForumRelCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserForumRel userForumRel);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserForumRel userForumRel);

    /**
     * 根据条件查询记录集
     */
    List<UserForumRel> selectByExample(UserForumRelCriteria userForumRelCriteria);

    /**
     * 根据主键查询记录
     */
    UserForumRel selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("userForumRel") UserForumRel userForumRel, @Param("userForumRelCriteria") UserForumRelCriteria userForumRelCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("userForumRel") UserForumRel userForumRel, @Param("userForumRelCriteria") UserForumRelCriteria userForumRelCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserForumRel userForumRel);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserForumRel userForumRel);
}