package com.study.library.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 */
public class PageRequest {

    /**
     * 页面传输过来的筛选条件
     */
    private Map<String,String> filter=new HashMap<>();

    /**
     * 当前显示页
     */
    private Integer page=new Integer(1);

    /**
     * 一页最多显示几条数据
     */
    private Integer size=new Integer(15);

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
