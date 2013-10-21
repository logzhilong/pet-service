package com.momoplan.pet.commons.domain.ssoserver.mapper;

import com.momoplan.pet.commons.domain.ssoserver.po.PetInfo;
import com.momoplan.pet.commons.domain.ssoserver.po.PetInfoCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* PetInfoMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 19:11:28
*/
public interface PetInfoMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(PetInfoCriteria petInfoCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(PetInfoCriteria petInfoCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(PetInfo petInfo);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(PetInfo petInfo);

    /**
     * 根据条件查询记录集
     */
    List<PetInfo> selectByExample(PetInfoCriteria petInfoCriteria);

    /**
     * 根据主键查询记录
     */
    PetInfo selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("petInfo") PetInfo petInfo, @Param("petInfoCriteria") PetInfoCriteria petInfoCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("petInfo") PetInfo petInfo, @Param("petInfoCriteria") PetInfoCriteria petInfoCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(PetInfo petInfo);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(PetInfo petInfo);
}