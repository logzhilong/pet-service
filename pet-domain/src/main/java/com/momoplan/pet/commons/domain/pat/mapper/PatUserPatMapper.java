package com.momoplan.pet.commons.domain.pat.mapper;

import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.pat.po.PatUserPatCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* PatUserPatMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 15:07:16
*/
public interface PatUserPatMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(PatUserPatCriteria patUserPatCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(PatUserPatCriteria patUserPatCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(PatUserPat patUserPat);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(PatUserPat patUserPat);

    /**
     * 根据条件查询记录集
     */
    List<PatUserPat> selectByExample(PatUserPatCriteria patUserPatCriteria);

    /**
     * 根据主键查询记录
     */
    PatUserPat selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("patUserPat") PatUserPat patUserPat, @Param("patUserPatCriteria") PatUserPatCriteria patUserPatCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("patUserPat") PatUserPat patUserPat, @Param("patUserPatCriteria") PatUserPatCriteria patUserPatCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(PatUserPat patUserPat);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(PatUserPat patUserPat);
}