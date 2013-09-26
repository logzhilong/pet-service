package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCodeCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* CommonAreaCodeMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-26 15:24:20
*/
public interface CommonAreaCodeMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(CommonAreaCodeCriteria commonAreaCodeCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(CommonAreaCodeCriteria commonAreaCodeCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(CommonAreaCode commonAreaCode);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(CommonAreaCode commonAreaCode);

    /**
     * 根据条件查询记录集
     */
    List<CommonAreaCode> selectByExample(CommonAreaCodeCriteria commonAreaCodeCriteria);

    /**
     * 根据主键查询记录
     */
    CommonAreaCode selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("commonAreaCode") CommonAreaCode commonAreaCode, @Param("commonAreaCodeCriteria") CommonAreaCodeCriteria commonAreaCodeCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("commonAreaCode") CommonAreaCode commonAreaCode, @Param("commonAreaCodeCriteria") CommonAreaCodeCriteria commonAreaCodeCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(CommonAreaCode commonAreaCode);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(CommonAreaCode commonAreaCode);
}