package com.momoplan.pet.commons.domain.user.mapper;

import com.momoplan.pet.commons.domain.user.po.SsoChatServer;
import com.momoplan.pet.commons.domain.user.po.SsoChatServerCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* SsoChatServerMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-17 11:06:40
*/
public interface SsoChatServerMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(SsoChatServerCriteria ssoChatServerCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SsoChatServerCriteria ssoChatServerCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SsoChatServer ssoChatServer);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SsoChatServer ssoChatServer);

    /**
     * 根据条件查询记录集
     */
    List<SsoChatServer> selectByExample(SsoChatServerCriteria ssoChatServerCriteria);

    /**
     * 根据主键查询记录
     */
    SsoChatServer selectByPrimaryKey(String id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ssoChatServer") SsoChatServer ssoChatServer, @Param("ssoChatServerCriteria") SsoChatServerCriteria ssoChatServerCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ssoChatServer") SsoChatServer ssoChatServer, @Param("ssoChatServerCriteria") SsoChatServerCriteria ssoChatServerCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SsoChatServer ssoChatServer);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SsoChatServer ssoChatServer);
}