package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.BizYijifen;
import com.momoplan.pet.commons.domain.statistic.po.BizYijifenCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* BizYijifenMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-04 00:00:43
*/
public interface BizYijifenMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(BizYijifenCriteria bizYijifenCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(BizYijifenCriteria bizYijifenCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BizYijifen bizYijifen);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BizYijifen bizYijifen);

    /**
     * 根据条件查询记录集
     */
    List<BizYijifen> selectByExample(BizYijifenCriteria bizYijifenCriteria);

    /**
     * 根据主键查询记录
     */
    BizYijifen selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("bizYijifen") BizYijifen bizYijifen, @Param("bizYijifenCriteria") BizYijifenCriteria bizYijifenCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("bizYijifen") BizYijifen bizYijifen, @Param("bizYijifenCriteria") BizYijifenCriteria bizYijifenCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BizYijifen bizYijifen);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BizYijifen bizYijifen);
}