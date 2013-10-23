package com.momoplan.pet.commons.domain.user.mapper;

import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* SsoUserMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-23 15:26:24
*/
public interface SsoUserMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(SsoUserCriteria ssoUserCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SsoUserCriteria ssoUserCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SsoUser ssoUser);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SsoUser ssoUser);

    /**
     * 根据条件查询记录集
     */
    List<SsoUser> selectByExample(SsoUserCriteria ssoUserCriteria);

    /**
     * 根据主键查询记录
     */
    SsoUser selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ssoUser") SsoUser ssoUser, @Param("ssoUserCriteria") SsoUserCriteria ssoUserCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ssoUser") SsoUser ssoUser, @Param("ssoUserCriteria") SsoUserCriteria ssoUserCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SsoUser ssoUser);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SsoUser ssoUser);
}