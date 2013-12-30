package com.momoplan.pet.commons.domain.notice.mapper;

import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.commons.domain.notice.po.AdsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* AdsMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-12-30 14:51:57
*/
public interface AdsMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(AdsCriteria adsCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AdsCriteria adsCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Ads ads);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Ads ads);

    /**
     * 根据条件查询记录集
     */
    List<Ads> selectByExampleWithBLOBs(AdsCriteria adsCriteria);

    /**
     * 根据条件查询记录集
     */
    List<Ads> selectByExample(AdsCriteria adsCriteria);

    /**
     * 根据主键查询记录
     */
    Ads selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ads") Ads ads, @Param("adsCriteria") AdsCriteria adsCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(@Param("ads") Ads ads, @Param("adsCriteria") AdsCriteria adsCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ads") Ads ads, @Param("adsCriteria") AdsCriteria adsCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Ads ads);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(Ads ads);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Ads ads);
}