package com.momoplan.pet.commons.domain.manager.mapper;

import com.momoplan.pet.commons.domain.manager.po.MgrTimerTask;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTaskCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* MgrTimerTaskMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-12-20 12:15:06
*/
public interface MgrTimerTaskMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(MgrTimerTaskCriteria mgrTimerTaskCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MgrTimerTaskCriteria mgrTimerTaskCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(MgrTimerTask mgrTimerTask);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(MgrTimerTask mgrTimerTask);

    /**
     * 根据条件查询记录集
     */
    List<MgrTimerTask> selectByExample(MgrTimerTaskCriteria mgrTimerTaskCriteria);

    /**
     * 根据主键查询记录
     */
    MgrTimerTask selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("mgrTimerTask") MgrTimerTask mgrTimerTask, @Param("mgrTimerTaskCriteria") MgrTimerTaskCriteria mgrTimerTaskCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("mgrTimerTask") MgrTimerTask mgrTimerTask, @Param("mgrTimerTaskCriteria") MgrTimerTaskCriteria mgrTimerTaskCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(MgrTimerTask mgrTimerTask);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MgrTimerTask mgrTimerTask);
}