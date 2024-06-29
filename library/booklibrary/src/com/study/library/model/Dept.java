package com.study.library.model;

/**
 * 单位实体类
 */
public class Dept {

    private Integer dpID; // 单位编号（主键）
    private String dpName; // 单位名称

    public Integer getDpID() {
        return dpID;
    }

    public void setDpID(Integer dpID) {
        this.dpID = dpID;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }
}
