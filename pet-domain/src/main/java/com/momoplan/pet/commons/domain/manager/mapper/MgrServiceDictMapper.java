package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrServiceDict;
import com.momoplan.pet.commons.domain.manager.po.MgrServiceDictCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrServiceDictMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-19 14:45:08
*/
public interface MgrServiceDictMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrServiceDictCriteria mgrServiceDictCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrServiceDictCriteria mgrServiceDictCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrServiceDict mgrServiceDict);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrServiceDict mgrServiceDict);

    /**
     * 根据条件查询记录集
     */
    List<MgrServiceDict> selectByExample(MgrServiceDictCriteria mgrServiceDictCriteria);

    /**
     * 根据主键查询记录
     */
    MgrServiceDict selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrServiceDict") MgrServiceDict mgrServiceDict, @Param("mgrServiceDictCriteria") MgrServiceDictCriteria mgrServiceDictCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrServiceDict") MgrServiceDict mgrServiceDict, @Param("mgrServiceDictCriteria") MgrServiceDictCriteria mgrServiceDictCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrServiceDict mgrServiceDict);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrServiceDict mgrServiceDict);
}