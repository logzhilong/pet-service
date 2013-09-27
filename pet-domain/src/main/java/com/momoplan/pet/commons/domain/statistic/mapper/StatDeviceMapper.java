package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.StatDevice;
import com.momoplan.pet.commons.domain.statistic.po.StatDeviceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatDeviceMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public interface StatDeviceMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatDeviceCriteria statDeviceCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatDeviceCriteria statDeviceCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatDevice statDevice);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatDevice statDevice);

    /**
     * 根据条件查询记录集
     */
    List<StatDevice> selectByExample(StatDeviceCriteria statDeviceCriteria);

    /**
     * 根据主键查询记录
     */
    StatDevice selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statDevice") StatDevice statDevice, @Param("statDeviceCriteria") StatDeviceCriteria statDeviceCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statDevice") StatDevice statDevice, @Param("statDeviceCriteria") StatDeviceCriteria statDeviceCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatDevice statDevice);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatDevice statDevice);
}