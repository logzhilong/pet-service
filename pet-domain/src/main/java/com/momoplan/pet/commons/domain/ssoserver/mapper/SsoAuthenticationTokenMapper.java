package com.momoplan.pet.commons.domain.ssoserver.mapper;

import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationTokenCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* SsoAuthenticationTokenMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-14 10:55:58
*/
public interface SsoAuthenticationTokenMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String token);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SsoAuthenticationToken ssoAuthenticationToken);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SsoAuthenticationToken ssoAuthenticationToken);

    /**
     * 根据条件查询记录集
     */
    List<SsoAuthenticationToken> selectByExample(SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria);

    /**
     * 根据主键查询记录
     */
    SsoAuthenticationToken selectByPrimaryKey(String token);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ssoAuthenticationToken") SsoAuthenticationToken ssoAuthenticationToken, @Param("ssoAuthenticationTokenCriteria") SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ssoAuthenticationToken") SsoAuthenticationToken ssoAuthenticationToken, @Param("ssoAuthenticationTokenCriteria") SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SsoAuthenticationToken ssoAuthenticationToken);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SsoAuthenticationToken ssoAuthenticationToken);
}