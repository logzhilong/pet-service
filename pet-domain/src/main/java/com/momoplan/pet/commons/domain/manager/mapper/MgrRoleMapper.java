package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrRoleMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 17:44:09
*/
public interface MgrRoleMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrRoleCriteria mgrRoleCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrRoleCriteria mgrRoleCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrRole mgrRole);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrRole mgrRole);

    /**
     * 根据条件查询记录集
     */
    List<MgrRole> selectByExample(MgrRoleCriteria mgrRoleCriteria);

    /**
     * 根据主键查询记录
     */
    MgrRole selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrRole") MgrRole mgrRole, @Param("mgrRoleCriteria") MgrRoleCriteria mgrRoleCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrRole") MgrRole mgrRole, @Param("mgrRoleCriteria") MgrRoleCriteria mgrRoleCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrRole mgrRole);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrRole mgrRole);
}