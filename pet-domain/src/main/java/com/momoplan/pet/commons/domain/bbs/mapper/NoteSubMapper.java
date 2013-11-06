package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* NoteSubMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-06 13:53:10
*/
public interface NoteSubMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(NoteSubCriteria noteSubCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(NoteSubCriteria noteSubCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(NoteSub noteSub);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(NoteSub noteSub);

    /**
     * 根据条件查询记录集
     */
    List<NoteSub> selectByExampleWithBLOBs(NoteSubCriteria noteSubCriteria);

    /**
     * 根据条件查询记录集
     */
    List<NoteSub> selectByExample(NoteSubCriteria noteSubCriteria);

    /**
     * 根据主键查询记录
     */
    NoteSub selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("noteSub") NoteSub noteSub, @Param("noteSubCriteria") NoteSubCriteria noteSubCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(@Param("noteSub") NoteSub noteSub, @Param("noteSubCriteria") NoteSubCriteria noteSubCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("noteSub") NoteSub noteSub, @Param("noteSubCriteria") NoteSubCriteria noteSubCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(NoteSub noteSub);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(NoteSub noteSub);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(NoteSub noteSub);
}