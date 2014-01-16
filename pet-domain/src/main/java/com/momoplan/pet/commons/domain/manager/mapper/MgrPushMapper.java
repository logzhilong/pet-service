package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.manager.po.MgrPushCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrPushMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-16 15:54:18
*/
public interface MgrPushMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrPushCriteria mgrPushCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrPushCriteria mgrPushCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrPush mgrPush);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrPush mgrPush);

    /**
     * 根据条件查询记录集
     */
    List<MgrPush> selectByExample(MgrPushCriteria mgrPushCriteria);

    /**
     * 根据主键查询记录
     */
    MgrPush selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrPush") MgrPush mgrPush, @Param("mgrPushCriteria") MgrPushCriteria mgrPushCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrPush") MgrPush mgrPush, @Param("mgrPushCriteria") MgrPushCriteria mgrPushCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrPush mgrPush);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrPush mgrPush);
}