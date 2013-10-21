package com.momoplan.pet.commons.domain.states.mapper;

import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatesUserStatesMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 17:03:13
*/
public interface StatesUserStatesMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatesUserStatesCriteria statesUserStatesCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatesUserStatesCriteria statesUserStatesCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatesUserStates statesUserStates);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatesUserStates statesUserStates);

    /**
     * 根据条件查询记录集
     */
    List<StatesUserStates> selectByExample(StatesUserStatesCriteria statesUserStatesCriteria);

    /**
     * 根据主键查询记录
     */
    StatesUserStates selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statesUserStates") StatesUserStates statesUserStates, @Param("statesUserStatesCriteria") StatesUserStatesCriteria statesUserStatesCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statesUserStates") StatesUserStates statesUserStates, @Param("statesUserStatesCriteria") StatesUserStatesCriteria statesUserStatesCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatesUserStates statesUserStates);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatesUserStates statesUserStates);
}