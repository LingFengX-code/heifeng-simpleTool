package com.heifeng.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heifeng.demo.security.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author xlf
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询组织下所有用户信息
     * @param organId 组织id
     * @return 该组织下的所有用户
     */
    @Select("select * from tb_user where id in (" +
            " select user_id from tb_organization_user where organization_id = #{organId})")
    List<User> findAllByOrganId(@Param("organId") Long organId);

    /**
     * 查询角色下所有用户信息
     * @param roleId
     * @return
     */
    @Select("select * from tb_user where id in (" +
            " select user_id from tb_user_role where role_id = #{roleId})")
    List<User> findAllByRoleId(Long roleId);

    /**
     * 查询所有组织下的用户信息
     * @param orgIds
     * @return
     */
    @Select({"<script> select * from tb_user where id in (" +
            " select user_id from tb_organization_user where organization_id in " +
            " <foreach collection='orgIds' open='(' separator = ',' close=')' item = 'orgId'>" +
            " #{orgId}" +
            " </foreach>" +
            ")" +
            "</script>"})
    List<User> findAllByOrganIds(@Param("orgIds") List<Long> orgIds);
}
