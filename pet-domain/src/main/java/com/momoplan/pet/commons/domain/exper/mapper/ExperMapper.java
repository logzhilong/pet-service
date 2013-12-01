package com.momoplan.pet.commons.domain.exper.mapper;

import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.exper.po.ExperCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* ExperMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-12-01 13:00:14
*/
public interface ExperMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(ExperCriteria experCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(ExperCriteria experCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Exper exper);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Exper exper);

    /**
     * 根据条件查询记录集
     */
    List<Exper> selectByExampleWithBLOBs(ExperCriteria experCriteria);

    /**
     * 根据条件查询记录集
     */
    List<Exper> selectByExample(ExperCriteria experCriteria);

    /**
     * 根据主键查询记录
     */
    Exper selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("exper") Exper exper, @Param("experCriteria") ExperCriteria experCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(@Param("exper") Exper exper, @Param("experCriteria") ExperCriteria experCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("exper") Exper exper, @Param("experCriteria") ExperCriteria experCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Exper exper);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(Exper exper);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Exper exper);
}