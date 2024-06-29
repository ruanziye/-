package com.study.library.model;

import java.util.Date;

/**
 * 读者类型
 */
public class ReaderType {
    /**
     * 读者类别【主键】
     */
    private Integer rdType;

    /**
     * 读者类别名称【唯一、非空】
     */
    private String rdTypeName;

    /**
     * 可借书数量
     */
    private Integer canLendQty;

    /**
     * 可借书天数
     */
    private Integer canLendDay;

    /**
     * 可续借的次数
     */
    private Integer canContinueTimes;

    /**
     * 罚款率（元/天）
     */
    private Double punishRate;

    /**
     * 证书有效期（年）【非空，0表示永久有效】
     */
    private Integer dateValid;

    // 以下是对应的getter和setter方法，你可以根据需要添加其他业务逻辑方法。
    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getRdTypeName() {
        return rdTypeName;
    }

    public void setRdTypeName(String rdTypeName) {
        this.rdTypeName = rdTypeName;
    }

    public Integer getCanLendQty() {
        return canLendQty;
    }

    public void setCanLendQty(Integer canLendQty) {
        this.canLendQty = canLendQty;
    }

    public Integer getCanLendDay() {
        return canLendDay;
    }

    public void setCanLendDay(Integer canLendDay) {
        this.canLendDay = canLendDay;
    }

    public Integer getCanContinueTimes() {
        return canContinueTimes;
    }

    public void setCanContinueTimes(Integer canContinueTimes) {
        this.canContinueTimes = canContinueTimes;
    }

    public Double getPunishRate() {
        return punishRate;
    }

    public void setPunishRate(Double punishRate) {
        this.punishRate = punishRate;
    }

    public Integer getDateValid() {
        return dateValid;
    }

    public void setDateValid(Integer dateValid) {
        this.dateValid = dateValid;
    }
}
