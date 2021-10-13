package com.heifeng.demo.security.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 基础业务接口
 * @author xlf
 */
public interface BaseService<T> {
    /**
     * 查询所有
     * @return 对象集合
     */
    @Deprecated
    List<T> findAll();

    /**
     *分页展示
     * @param page  当前页码（默认从1开始）
     * @param pageSize  每页展示条数
     * @return
     */
    PageInfo findAllByPage(Integer page, Integer pageSize);

    /**
     * 根据id查询
     * @param id    id
     * @return  对象
     */
    T findById(Serializable id);

    /**
     * 保存新增
     * @param t
     * @return 主键id
     */
    Long saveOrUpdate(T t);

    /**
     * 删除
     * @param id
     * @return 成功条数
     */
    Integer delete(Serializable id);

    /**
     * 批量删除
     * @param ids
     * @return 成功条数
     */
    Integer deleteByIds(List<Long> ids);

}
