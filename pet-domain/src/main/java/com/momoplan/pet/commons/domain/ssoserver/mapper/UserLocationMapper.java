package com.momoplan.pet.commons.domain.ssoserver.mapper;

import com.momoplan.pet.commons.domain.ssoserver.po.UserLocation;
import com.momoplan.pet.commons.domain.ssoserver.po.UserLocationCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* UserLocationMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 19:21:27
*/
public interface UserLocationMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(UserLocationCriteria userLocationCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserLocationCriteria userLocationCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long userid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserLocation userLocation);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserLocation userLocation);

    /**
     * 根据条件查询记录集
     */
    List<UserLocation> selectByExample(UserLocationCriteria userLocationCriteria);

    /**
     * 根据主键查询记录
     */
    UserLocation selectByPrimaryKey(Long userid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("userLocation") UserLocation userLocation, @Param("userLocationCriteria") UserLocationCriteria userLocationCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("userLocation") UserLocation userLocation, @Param("userLocationCriteria") UserLocationCriteria userLocationCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserLocation userLocation);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserLocation userLocation);
}