package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistorCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* BizDailyRegistorMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-11 17:28:39
*/
public interface BizDailyRegistorMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(BizDailyRegistorCriteria bizDailyRegistorCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(BizDailyRegistorCriteria bizDailyRegistorCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BizDailyRegistor bizDailyRegistor);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BizDailyRegistor bizDailyRegistor);

    /**
     * 根据条件查询记录集
     */
    List<BizDailyRegistor> selectByExample(BizDailyRegistorCriteria bizDailyRegistorCriteria);

    /**
     * 根据主键查询记录
     */
    BizDailyRegistor selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("bizDailyRegistor") BizDailyRegistor bizDailyRegistor, @Param("bizDailyRegistorCriteria") BizDailyRegistorCriteria bizDailyRegistorCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("bizDailyRegistor") BizDailyRegistor bizDailyRegistor, @Param("bizDailyRegistorCriteria") BizDailyRegistorCriteria bizDailyRegistorCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BizDailyRegistor bizDailyRegistor);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BizDailyRegistor bizDailyRegistor);
}