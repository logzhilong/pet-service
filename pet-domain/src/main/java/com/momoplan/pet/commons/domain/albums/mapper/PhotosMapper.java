package com.momoplan.pet.commons.domain.albums.mapper;

import com.momoplan.pet.commons.domain.albums.po.Photos;
import com.momoplan.pet.commons.domain.albums.po.PhotosCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* PhotosMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-28 15:29:53
*/
public interface PhotosMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(PhotosCriteria photosCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(PhotosCriteria photosCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Photos photos);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Photos photos);

    /**
     * 根据条件查询记录集
     */
    List<Photos> selectByExample(PhotosCriteria photosCriteria);

    /**
     * 根据主键查询记录
     */
    Photos selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("photos") Photos photos, @Param("photosCriteria") PhotosCriteria photosCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("photos") Photos photos, @Param("photosCriteria") PhotosCriteria photosCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Photos photos);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Photos photos);
}