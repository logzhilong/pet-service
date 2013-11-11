package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLiveCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* BizDailyLiveMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-11 17:28:39
*/
public interface BizDailyLiveMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(BizDailyLiveCriteria bizDailyLiveCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(BizDailyLiveCriteria bizDailyLiveCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BizDailyLive bizDailyLive);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BizDailyLive bizDailyLive);

    /**
     * 根据条件查询记录集
     */
    List<BizDailyLive> selectByExample(BizDailyLiveCriteria bizDailyLiveCriteria);

    /**
     * 根据主键查询记录
     */
    BizDailyLive selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("bizDailyLive") BizDailyLive bizDailyLive, @Param("bizDailyLiveCriteria") BizDailyLiveCriteria bizDailyLiveCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("bizDailyLive") BizDailyLive bizDailyLive, @Param("bizDailyLiveCriteria") BizDailyLiveCriteria bizDailyLiveCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BizDailyLive bizDailyLive);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BizDailyLive bizDailyLive);
}