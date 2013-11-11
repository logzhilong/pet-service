package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethodCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* BizDailyMethodMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-11 17:28:39
*/
public interface BizDailyMethodMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(BizDailyMethodCriteria bizDailyMethodCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(BizDailyMethodCriteria bizDailyMethodCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BizDailyMethod bizDailyMethod);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BizDailyMethod bizDailyMethod);

    /**
     * 根据条件查询记录集
     */
    List<BizDailyMethod> selectByExample(BizDailyMethodCriteria bizDailyMethodCriteria);

    /**
     * 根据主键查询记录
     */
    BizDailyMethod selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("bizDailyMethod") BizDailyMethod bizDailyMethod, @Param("bizDailyMethodCriteria") BizDailyMethodCriteria bizDailyMethodCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("bizDailyMethod") BizDailyMethod bizDailyMethod, @Param("bizDailyMethodCriteria") BizDailyMethodCriteria bizDailyMethodCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BizDailyMethod bizDailyMethod);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BizDailyMethod bizDailyMethod);
}