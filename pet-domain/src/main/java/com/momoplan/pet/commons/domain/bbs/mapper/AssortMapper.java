package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.Assort;
import com.momoplan.pet.commons.domain.bbs.po.AssortCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* AssortMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-02-20 11:17:15
*/
public interface AssortMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(AssortCriteria assortCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AssortCriteria assortCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Assort assort);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Assort assort);

    /**
     * 根据条件查询记录集
     */
    List<Assort> selectByExample(AssortCriteria assortCriteria);

    /**
     * 根据主键查询记录
     */
    Assort selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("assort") Assort assort, @Param("assortCriteria") AssortCriteria assortCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("assort") Assort assort, @Param("assortCriteria") AssortCriteria assortCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Assort assort);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Assort assort);
}