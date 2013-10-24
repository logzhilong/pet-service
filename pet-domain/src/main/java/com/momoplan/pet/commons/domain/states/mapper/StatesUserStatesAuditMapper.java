package com.momoplan.pet.commons.domain.states.mapper;

import com.momoplan.pet.commons.domain.states.po.StatesUserStatesAudit;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesAuditCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatesUserStatesAuditMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-24 15:35:10
*/
public interface StatesUserStatesAuditMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatesUserStatesAuditCriteria statesUserStatesAuditCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatesUserStatesAuditCriteria statesUserStatesAuditCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatesUserStatesAudit statesUserStatesAudit);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatesUserStatesAudit statesUserStatesAudit);

    /**
     * 根据条件查询记录集
     */
    List<StatesUserStatesAudit> selectByExample(StatesUserStatesAuditCriteria statesUserStatesAuditCriteria);

    /**
     * 根据主键查询记录
     */
    StatesUserStatesAudit selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statesUserStatesAudit") StatesUserStatesAudit statesUserStatesAudit, @Param("statesUserStatesAuditCriteria") StatesUserStatesAuditCriteria statesUserStatesAuditCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statesUserStatesAudit") StatesUserStatesAudit statesUserStatesAudit, @Param("statesUserStatesAuditCriteria") StatesUserStatesAuditCriteria statesUserStatesAuditCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatesUserStatesAudit statesUserStatesAudit);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatesUserStatesAudit statesUserStatesAudit);
}