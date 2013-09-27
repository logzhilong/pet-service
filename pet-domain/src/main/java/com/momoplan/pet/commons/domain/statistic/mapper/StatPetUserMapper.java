package com.momoplan.pet.commons.domain.statistic.mapper;

import com.momoplan.pet.commons.domain.statistic.po.StatPetUser;
import com.momoplan.pet.commons.domain.statistic.po.StatPetUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatPetUserMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public interface StatPetUserMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatPetUserCriteria statPetUserCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatPetUserCriteria statPetUserCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatPetUser statPetUser);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatPetUser statPetUser);

    /**
     * 根据条件查询记录集
     */
    List<StatPetUser> selectByExample(StatPetUserCriteria statPetUserCriteria);

    /**
     * 根据主键查询记录
     */
    StatPetUser selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statPetUser") StatPetUser statPetUser, @Param("statPetUserCriteria") StatPetUserCriteria statPetUserCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statPetUser") StatPetUser statPetUser, @Param("statPetUserCriteria") StatPetUserCriteria statPetUserCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatPetUser statPetUser);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatPetUser statPetUser);
}