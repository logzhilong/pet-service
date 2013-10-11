package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.commons.domain.manager.po.MgrUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrUserMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 17:44:09
*/
public interface MgrUserMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrUserCriteria mgrUserCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrUserCriteria mgrUserCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrUser mgrUser);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrUser mgrUser);

    /**
     * 根据条件查询记录集
     */
    List<MgrUser> selectByExample(MgrUserCriteria mgrUserCriteria);

    /**
     * 根据主键查询记录
     */
    MgrUser selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrUser") MgrUser mgrUser, @Param("mgrUserCriteria") MgrUserCriteria mgrUserCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrUser") MgrUser mgrUser, @Param("mgrUserCriteria") MgrUserCriteria mgrUserCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrUser mgrUser);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrUser mgrUser);
}