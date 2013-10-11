package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRelCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrUserRoleRelMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 17:44:09
*/
public interface MgrUserRoleRelMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrUserRoleRelCriteria mgrUserRoleRelCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrUserRoleRelCriteria mgrUserRoleRelCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrUserRoleRel mgrUserRoleRel);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrUserRoleRel mgrUserRoleRel);

    /**
     * 根据条件查询记录集
     */
    List<MgrUserRoleRel> selectByExample(MgrUserRoleRelCriteria mgrUserRoleRelCriteria);

    /**
     * 根据主键查询记录
     */
    MgrUserRoleRel selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrUserRoleRel") MgrUserRoleRel mgrUserRoleRel, @Param("mgrUserRoleRelCriteria") MgrUserRoleRelCriteria mgrUserRoleRelCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrUserRoleRel") MgrUserRoleRel mgrUserRoleRel, @Param("mgrUserRoleRelCriteria") MgrUserRoleRelCriteria mgrUserRoleRelCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrUserRoleRel mgrUserRoleRel);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrUserRoleRel mgrUserRoleRel);
}