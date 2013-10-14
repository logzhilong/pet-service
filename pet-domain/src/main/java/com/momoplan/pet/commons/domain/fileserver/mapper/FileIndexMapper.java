package com.momoplan.pet.commons.domain.fileserver.mapper;

import com.momoplan.pet.commons.domain.fileserver.po.FileIndex;
import com.momoplan.pet.commons.domain.fileserver.po.FileIndexCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* FileIndexMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-12 17:27:19
*/
public interface FileIndexMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(FileIndexCriteria fileIndexCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(FileIndexCriteria fileIndexCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(FileIndex fileIndex);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(FileIndex fileIndex);

    /**
     * 根据条件查询记录集
     */
    List<FileIndex> selectByExample(FileIndexCriteria fileIndexCriteria);

    /**
     * 根据主键查询记录
     */
    FileIndex selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("fileIndex") FileIndex fileIndex, @Param("fileIndexCriteria") FileIndexCriteria fileIndexCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("fileIndex") FileIndex fileIndex, @Param("fileIndexCriteria") FileIndexCriteria fileIndexCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(FileIndex fileIndex);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(FileIndex fileIndex);
}