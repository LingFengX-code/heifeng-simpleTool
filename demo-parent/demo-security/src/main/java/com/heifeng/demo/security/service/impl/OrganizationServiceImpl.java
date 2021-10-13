package com.heifeng.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heifeng.demo.security.entity.Organization;
import com.heifeng.demo.security.entity.User;
import com.heifeng.demo.security.mapper.OrganizationMapper;
import com.heifeng.demo.security.mapper.UserMapper;
import com.heifeng.demo.security.service.OrganizationService;
import com.heifeng.utils.common.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xlf
 * @Date: 2021/06/04/17:07
 * @Description: 组织的业务实现类
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private UserMapper userInfoMapper;

    /**
     * 查询所有 根据pid的树形结构
     *
     * @return 对象集合
     */
    @Override
    public List<Organization> findAll() {
        return organizationMapper.selectList(new QueryWrapper<Organization>().eq("is_deleted", 0));
    }

    /**
     * 分页展示 --
     * @param page     当前页码（默认从1开始）
     * @param pageSize 每页展示条数
     * @return
     */
    @Override
    @Deprecated
    public PageInfo findAllByPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return new PageInfo<Organization>(this.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 对象
     */
    @Override
    public Organization findById(Serializable id) {
        //根据id查找的时候，会把多对多关系表的数据写入List里面
        Organization organization = organizationMapper.selectById(id);
        if(organization.getIsDeleted() != 0){return null;}
        List<User> userList = userInfoMapper.findAllByOrganId(organization.getId());
        organization.setUserList(userList);
        return organization;
    }

    /**
     * 保存/新增
     *
     * @param organization
     * @return 主键id
     */
    @Override
    public Long saveOrUpdate(Organization organization) {
        if(null != organization.getId() && organization.getId()!=0L){
            return this.organizationMapper.updateById(organization) == 1 ? organization.getId() : null;
        }
        organization.setId(SnowFlakeUtil.nextId());
        return this.organizationMapper.insert(organization) == 1 ? organization.getId() : null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public Integer delete(Serializable id) {
        Organization organization = this.findById(id);
        organization.setIsDeleted(1);
        Long oldId = this.saveOrUpdate(organization);
        return (null!=oldId && oldId > 0) ? 1 : 0;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public Integer deleteByIds(List<Long> ids) {
        //用来进行成功执行数据的计数
        Integer count = 0;
        for(Long id : ids){
            this.delete(id);
            count++;
        }
        return count;
    }

    /**
     * 查询顶层组织部门
     *
     * @return
     */
    @Override
    public List<Organization> findTopOrganization() {
        List<Organization> organizationList = organizationMapper.findTopOrganization();
        if(null == organizationList || organizationList.size() < 1){return null;}
        organizationList.forEach(organization -> {
            organization.setIsMinOrg(this.judgeIsMinOrg(organization.getId()));
        });
        return organizationList;
    }


    /**
     * 根据pid拿到所有最底层的子组织id
     *
     * @param pid
     * @return
     */
    @Override
    public List<Long> getIdsByPid(Long pid,List<Long> ids) {
        //如果其他表的pid有查出来的id就继续递归
        List<Organization> orgList = this.findAllByPid(pid);
        for(int i=0;i < orgList.size();i++){
            List<Organization> teOrgList = this.findAllByPid(orgList.get(i).getId());
            if(null == teOrgList || teOrgList.size() < 1){
                ids.add(orgList.get(i).getId());
                continue;
            }
            getIdsByPid(orgList.get(i).getId(),ids);
        }
        return ids;
    }

    /**
     * 判断是否为底层组织
     *
     * @param id 组织id
     * @return true：即为底层组织
     */
    @Override
    public Boolean judgeIsMinOrg(Long id) {
        List<Organization> orgList = this.findAllByPid(id);
        return null == orgList || orgList.size() < 1;
    }

    /**
     * 根据pid查询子组织
     *
     * @param pid
     * @return
     */
    @Override
    public List<Organization> findAllByPid(Long pid) {
        List<Organization> organizationList = organizationMapper.selectList(new QueryWrapper<Organization>().eq("is_deleted", 0).eq("pid", pid));
        organizationList.forEach(organization -> {
            organization.setIsMinOrg(this.judgeIsMinOrg(organization.getId()));
        });
        return organizationList;
    }
}
