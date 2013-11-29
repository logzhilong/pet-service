package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrPublishedApk;
import com.momoplan.pet.commons.domain.manager.po.MgrPublishedApkCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrPublishedApkMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-29 15:20:32
*/
public interface MgrPublishedApkMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrPublishedApkCriteria mgrPublishedApkCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrPublishedApkCriteria mgrPublishedApkCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrPublishedApk mgrPublishedApk);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrPublishedApk mgrPublishedApk);

    /**
     * 根据条件查询记录集
     */
    List<MgrPublishedApk> selectByExample(MgrPublishedApkCriteria mgrPublishedApkCriteria);

    /**
     * 根据主键查询记录
     */
    MgrPublishedApk selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrPublishedApk") MgrPublishedApk mgrPublishedApk, @Param("mgrPublishedApkCriteria") MgrPublishedApkCriteria mgrPublishedApkCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrPublishedApk") MgrPublishedApk mgrPublishedApk, @Param("mgrPublishedApkCriteria") MgrPublishedApkCriteria mgrPublishedApkCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrPublishedApk mgrPublishedApk);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrPublishedApk mgrPublishedApk);
}