package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubjectCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* SpecialSubjectMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-07 14:11:09
*/
public interface SpecialSubjectMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(SpecialSubjectCriteria specialSubjectCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SpecialSubjectCriteria specialSubjectCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SpecialSubject specialSubject);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SpecialSubject specialSubject);

    /**
     * 根据条件查询记录集
     */
    List<SpecialSubject> selectByExample(SpecialSubjectCriteria specialSubjectCriteria);

    /**
     * 根据主键查询记录
     */
    SpecialSubject selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("specialSubject") SpecialSubject specialSubject, @Param("specialSubjectCriteria") SpecialSubjectCriteria specialSubjectCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("specialSubject") SpecialSubject specialSubject, @Param("specialSubjectCriteria") SpecialSubjectCriteria specialSubjectCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SpecialSubject specialSubject);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SpecialSubject specialSubject);
}