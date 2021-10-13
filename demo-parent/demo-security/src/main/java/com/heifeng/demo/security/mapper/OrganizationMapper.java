package com.heifeng.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heifeng.demo.security.entity.Organization;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author xlf
 */
public interface OrganizationMapper extends BaseMapper<Organization> {
    /**
     * 查询用户下所有的角色
     * @param userId
     * @return
     */
    @Select("select * from tb_organization where id in (" +
            " select organization_id from tb_organization_user where user_id = #{userId})")
    List<Organization> findAllByUserId(Long userId);

    /**
     * 查询顶层组织部门
     * @return
     */
    @Select("select * from tb_organization " +
            "where (is_deleted) = 0 and (pid is null)")
    List<Organization> findTopOrganization();

}
