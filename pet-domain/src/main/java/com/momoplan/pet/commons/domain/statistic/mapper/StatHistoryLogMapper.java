package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.StatHistoryLog;
import com.momoplan.pet.commons.domain.statistic.po.StatHistoryLogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatHistoryLogMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public interface StatHistoryLogMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatHistoryLogCriteria statHistoryLogCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatHistoryLogCriteria statHistoryLogCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatHistoryLog statHistoryLog);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatHistoryLog statHistoryLog);

    /**
     * 根据条件查询记录集
     */
    List<StatHistoryLog> selectByExample(StatHistoryLogCriteria statHistoryLogCriteria);

    /**
     * 根据主键查询记录
     */
    StatHistoryLog selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statHistoryLog") StatHistoryLog statHistoryLog, @Param("statHistoryLogCriteria") StatHistoryLogCriteria statHistoryLogCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statHistoryLog") StatHistoryLog statHistoryLog, @Param("statHistoryLogCriteria") StatHistoryLogCriteria statHistoryLogCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatHistoryLog statHistoryLog);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatHistoryLog statHistoryLog);
}