package com.heifeng.demo.security.service;

import com.heifeng.demo.security.entity.Organization;

import java.util.List;

/**
 * @author xlf
 */
public interface OrganizationService extends BaseService<Organization>{

    /**
     * 查询顶层组织部门
     * @return
     */
    List<Organization> findTopOrganization();

    /**
     * 根据pid查询子组织
     * @param pid
     * @return
     */
    List<Organization> findAllByPid(Long pid);

    /**
     * 根据pid拿到所有最底层的子组织id
     * @param pid
     * @param ids 传入空的，最后返回的结果
     * @return
     */
    List<Long> getIdsByPid(Long pid, List<Long> ids);

    /**
     * 判断是否为底层组织
     * @param id 组织id
     * @return true：即为底层组织
     */
    Boolean judgeIsMinOrg(Long id);
}
