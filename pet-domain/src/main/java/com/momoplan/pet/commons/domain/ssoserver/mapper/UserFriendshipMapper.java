package com.momoplan.pet.commons.domain.ssoserver.mapper;

import com.momoplan.pet.commons.domain.ssoserver.po.UserFriendship;
import com.momoplan.pet.commons.domain.ssoserver.po.UserFriendshipCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* UserFriendshipMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 19:21:27
*/
public interface UserFriendshipMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(UserFriendshipCriteria userFriendshipCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserFriendshipCriteria userFriendshipCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserFriendship userFriendship);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserFriendship userFriendship);

    /**
     * 根据条件查询记录集
     */
    List<UserFriendship> selectByExample(UserFriendshipCriteria userFriendshipCriteria);

    /**
     * 根据主键查询记录
     */
    UserFriendship selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("userFriendship") UserFriendship userFriendship, @Param("userFriendshipCriteria") UserFriendshipCriteria userFriendshipCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("userFriendship") UserFriendship userFriendship, @Param("userFriendshipCriteria") UserFriendshipCriteria userFriendshipCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserFriendship userFriendship);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserFriendship userFriendship);
}