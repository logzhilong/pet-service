package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrChannelDict;
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDictCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrChannelDictMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-19 14:45:08
*/
public interface MgrChannelDictMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrChannelDictCriteria mgrChannelDictCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrChannelDictCriteria mgrChannelDictCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrChannelDict mgrChannelDict);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrChannelDict mgrChannelDict);

    /**
     * 根据条件查询记录集
     */
    List<MgrChannelDict> selectByExample(MgrChannelDictCriteria mgrChannelDictCriteria);

    /**
     * 根据主键查询记录
     */
    MgrChannelDict selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrChannelDict") MgrChannelDict mgrChannelDict, @Param("mgrChannelDictCriteria") MgrChannelDictCriteria mgrChannelDictCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrChannelDict") MgrChannelDict mgrChannelDict, @Param("mgrChannelDictCriteria") MgrChannelDictCriteria mgrChannelDictCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrChannelDict mgrChannelDict);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrChannelDict mgrChannelDict);
}