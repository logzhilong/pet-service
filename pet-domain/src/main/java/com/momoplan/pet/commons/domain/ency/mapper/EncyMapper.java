package com.momoplan.pet.commons.domain.ency.mapper;

import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.ency.po.EncyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* EncyMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-30 14:52:59
*/
public interface EncyMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(EncyCriteria encyCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(EncyCriteria encyCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Ency ency);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Ency ency);

    /**
     * 根据条件查询记录集
     */
    List<Ency> selectByExample(EncyCriteria encyCriteria);

    /**
     * 根据主键查询记录
     */
    Ency selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ency") Ency ency, @Param("encyCriteria") EncyCriteria encyCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ency") Ency ency, @Param("encyCriteria") EncyCriteria encyCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Ency ency);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Ency ency);
}