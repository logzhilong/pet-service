package com.momoplan.pet.commons.domain.user.mapper;

import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.commons.domain.user.po.PetCardCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* PetCardMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-02-13 14:16:41
*/
public interface PetCardMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(PetCardCriteria petCardCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(PetCardCriteria petCardCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(PetCard petCard);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(PetCard petCard);

    /**
     * 根据条件查询记录集
     */
    List<PetCard> selectByExample(PetCardCriteria petCardCriteria);

    /**
     * 根据主键查询记录
     */
    PetCard selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("petCard") PetCard petCard, @Param("petCardCriteria") PetCardCriteria petCardCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("petCard") PetCard petCard, @Param("petCardCriteria") PetCardCriteria petCardCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(PetCard petCard);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(PetCard petCard);
}