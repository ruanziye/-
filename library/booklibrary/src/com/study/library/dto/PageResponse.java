package com.study.library.dto;

import java.util.List;

/**
 * 执行分页的返回结果 ，基于JAVA泛型实现
 */
public class PageResponse<T> {

    /**
     * 当前显示的数据列表
     */
    private List<T> list;

    /**
     * 数据总记录数
     */
    private Integer total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
