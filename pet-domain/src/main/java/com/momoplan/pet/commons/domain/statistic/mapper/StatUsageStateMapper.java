package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.StatUsageState;
import com.momoplan.pet.commons.domain.statistic.po.StatUsageStateCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatUsageStateMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public interface StatUsageStateMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatUsageStateCriteria statUsageStateCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatUsageStateCriteria statUsageStateCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatUsageState statUsageState);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatUsageState statUsageState);

    /**
     * 根据条件查询记录集
     */
    List<StatUsageState> selectByExample(StatUsageStateCriteria statUsageStateCriteria);

    /**
     * 根据主键查询记录
     */
    StatUsageState selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statUsageState") StatUsageState statUsageState, @Param("statUsageStateCriteria") StatUsageStateCriteria statUsageStateCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statUsageState") StatUsageState statUsageState, @Param("statUsageStateCriteria") StatUsageStateCriteria statUsageStateCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatUsageState statUsageState);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatUsageState statUsageState);
}