package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrTrustUserMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-04 11:30:52
*/
public interface MgrTrustUserMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrTrustUserCriteria mgrTrustUserCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrTrustUserCriteria mgrTrustUserCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrTrustUser mgrTrustUser);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrTrustUser mgrTrustUser);

    /**
     * 根据条件查询记录集
     */
    List<MgrTrustUser> selectByExample(MgrTrustUserCriteria mgrTrustUserCriteria);

    /**
     * 根据主键查询记录
     */
    MgrTrustUser selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrTrustUser") MgrTrustUser mgrTrustUser, @Param("mgrTrustUserCriteria") MgrTrustUserCriteria mgrTrustUserCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrTrustUser") MgrTrustUser mgrTrustUser, @Param("mgrTrustUserCriteria") MgrTrustUserCriteria mgrTrustUserCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrTrustUser mgrTrustUser);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrTrustUser mgrTrustUser);
}