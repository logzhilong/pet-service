package com.momoplan.pet.commons.domain.states.mapper;

import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReplyCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* StatesUserStatesReplyMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 17:03:14
*/
public interface StatesUserStatesReplyMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(StatesUserStatesReplyCriteria statesUserStatesReplyCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatesUserStatesReplyCriteria statesUserStatesReplyCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(StatesUserStatesReply statesUserStatesReply);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(StatesUserStatesReply statesUserStatesReply);

    /**
     * 根据条件查询记录集
     */
    List<StatesUserStatesReply> selectByExample(StatesUserStatesReplyCriteria statesUserStatesReplyCriteria);

    /**
     * 根据主键查询记录
     */
    StatesUserStatesReply selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("statesUserStatesReply") StatesUserStatesReply statesUserStatesReply, @Param("statesUserStatesReplyCriteria") StatesUserStatesReplyCriteria statesUserStatesReplyCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("statesUserStatesReply") StatesUserStatesReply statesUserStatesReply, @Param("statesUserStatesReplyCriteria") StatesUserStatesReplyCriteria statesUserStatesReplyCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(StatesUserStatesReply statesUserStatesReply);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatesUserStatesReply statesUserStatesReply);
}