package com.momoplan.pet.commons.domain.states.mapper;

import com.momoplan.pet.commons.domain.states.po.CommonContentAudit;
import com.momoplan.pet.commons.domain.states.po.CommonContentAuditCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* CommonContentAuditMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-09 15:38:15
*/
public interface CommonContentAuditMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(CommonContentAuditCriteria commonContentAuditCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(CommonContentAuditCriteria commonContentAuditCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(CommonContentAudit commonContentAudit);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(CommonContentAudit commonContentAudit);

    /**
     * 根据条件查询记录集
     */
    List<CommonContentAudit> selectByExample(CommonContentAuditCriteria commonContentAuditCriteria);

    /**
     * 根据主键查询记录
     */
    CommonContentAudit selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("commonContentAudit") CommonContentAudit commonContentAudit, @Param("commonContentAuditCriteria") CommonContentAuditCriteria commonContentAuditCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("commonContentAudit") CommonContentAudit commonContentAudit, @Param("commonContentAuditCriteria") CommonContentAuditCriteria commonContentAuditCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(CommonContentAudit commonContentAudit);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(CommonContentAudit commonContentAudit);
}