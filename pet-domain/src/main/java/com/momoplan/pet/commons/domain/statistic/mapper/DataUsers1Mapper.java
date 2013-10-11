package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.DataUsers1;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers1Criteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* DataUsers1Mapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 12:03:37
*/
public interface DataUsers1Mapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(DataUsers1Criteria dataUsers1Criteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(DataUsers1Criteria dataUsers1Criteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(DataUsers1 dataUsers1);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(DataUsers1 dataUsers1);

    /**
     * 根据条件查询记录集
     */
    List<DataUsers1> selectByExample(DataUsers1Criteria dataUsers1Criteria);

    /**
     * 根据主键查询记录
     */
    DataUsers1 selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("dataUsers1") DataUsers1 dataUsers1, @Param("dataUsers1Criteria") DataUsers1Criteria dataUsers1Criteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("dataUsers1") DataUsers1 dataUsers1, @Param("dataUsers1Criteria") DataUsers1Criteria dataUsers1Criteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(DataUsers1 dataUsers1);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(DataUsers1 dataUsers1);
}