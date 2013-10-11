package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.DataUsers0;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers0Criteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* DataUsers0Mapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 12:03:37
*/
public interface DataUsers0Mapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(DataUsers0Criteria dataUsers0Criteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(DataUsers0Criteria dataUsers0Criteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(DataUsers0 dataUsers0);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(DataUsers0 dataUsers0);

    /**
     * 根据条件查询记录集
     */
    List<DataUsers0> selectByExample(DataUsers0Criteria dataUsers0Criteria);

    /**
     * 根据主键查询记录
     */
    DataUsers0 selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("dataUsers0") DataUsers0 dataUsers0, @Param("dataUsers0Criteria") DataUsers0Criteria dataUsers0Criteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("dataUsers0") DataUsers0 dataUsers0, @Param("dataUsers0Criteria") DataUsers0Criteria dataUsers0Criteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(DataUsers0 dataUsers0);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(DataUsers0 dataUsers0);
}