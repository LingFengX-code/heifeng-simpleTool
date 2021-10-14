package com.heifeng.utils.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 为了分页展示
 *     其中页面数据在items里面（暂时废弃）
 * @author xlf
 */
@Data
@Deprecated
public class PageResult<T> {
    private Long total;// 总条数
    private Integer totalPage;// 总页数
    private List<T> items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}
