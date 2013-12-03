package com.momoplan.pet.commons.domain.notice.mapper;

import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.commons.domain.notice.po.NoticeCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* NoticeMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-12-02 21:46:37
*/
public interface NoticeMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(NoticeCriteria noticeCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(NoticeCriteria noticeCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Notice notice);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Notice notice);

    /**
     * 根据条件查询记录集
     */
    List<Notice> selectByExampleWithBLOBs(NoticeCriteria noticeCriteria);

    /**
     * 根据条件查询记录集
     */
    List<Notice> selectByExample(NoticeCriteria noticeCriteria);

    /**
     * 根据主键查询记录
     */
    Notice selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("notice") Notice notice, @Param("noticeCriteria") NoticeCriteria noticeCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(@Param("notice") Notice notice, @Param("noticeCriteria") NoticeCriteria noticeCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("notice") Notice notice, @Param("noticeCriteria") NoticeCriteria noticeCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Notice notice);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(Notice notice);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Notice notice);
}