package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrUserReleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserReleRelCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrUserReleRelMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 16:53:46
*/
public interface MgrUserReleRelMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrUserReleRelCriteria mgrUserReleRelCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrUserReleRelCriteria mgrUserReleRelCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrUserReleRel mgrUserReleRel);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrUserReleRel mgrUserReleRel);

    /**
     * 根据条件查询记录集
     */
    List<MgrUserReleRel> selectByExample(MgrUserReleRelCriteria mgrUserReleRelCriteria);

    /**
     * 根据主键查询记录
     */
    MgrUserReleRel selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrUserReleRel") MgrUserReleRel mgrUserReleRel, @Param("mgrUserReleRelCriteria") MgrUserReleRelCriteria mgrUserReleRelCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrUserReleRel") MgrUserReleRel mgrUserReleRel, @Param("mgrUserReleRelCriteria") MgrUserReleRelCriteria mgrUserReleRelCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrUserReleRel mgrUserReleRel);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrUserReleRel mgrUserReleRel);
}