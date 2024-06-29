package com.study.library.model;

import java.util.Date;

/**
 * 借阅
 */
public class Borrow {
    /**
     * 借阅顺序号【自增，主键】
     */
    private Integer borrowID;

    /**
     * 读者序号【外键TB_Reader.rdID】
     */
    private Integer rdID;

    /**
     * 图书序号【外键TB_Book.bkID】
     */
    private Integer bkID;

    /**
     * 续借次数（第一次借时，记为0），缺省为0
     */
    private Integer ldContinueTimes;

    /**
     * 借书日期
     */
    private Date ldDateOut;

    /**
     * 应还日期
     */
    private Date ldDateRetPlan;

    /**
     * 实际还书日期
     */
    private Date ldDateRetAct;

    /**
     * 超期天数，缺省为0
     */
    private Integer ldOverDay;

    /**
     * 超期金额（应罚款金额），缺省为0.00
     */
    private Double ldOverMoney;

    /**
     * 罚款金额，缺省为0.00
     */
    private Double ldPunishMoney;

    /**
     * 是否已经还书，缺省为0-未还
     */
    private Boolean lsHasReturn;

    /**
     * 借书操作员
     */
    private String operatorBorrow;

    /**
     * 还书操作员
     */
    private String operatorReturn;

    // 以下是对应的getter和setter方法，你可以根据需要添加其他业务逻辑方法。
    public Integer getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(Integer borrowID) {
        this.borrowID = borrowID;
    }

    public Integer getRdID() {
        return rdID;
    }

    public void setRdID(Integer rdID) {
        this.rdID = rdID;
    }

    public Integer getBkID() {
        return bkID;
    }

    public void setBkID(Integer bkID) {
        this.bkID = bkID;
    }

    public Integer getLdContinueTimes() {
        return ldContinueTimes;
    }

    public void setLdContinueTimes(Integer ldContinueTimes) {
        this.ldContinueTimes = ldContinueTimes;
    }

    public Date getLdDateOut() {
        return ldDateOut;
    }

    public void setLdDateOut(Date ldDateOut) {
        this.ldDateOut = ldDateOut;
    }

    public Date getLdDateRetPlan() {
        return ldDateRetPlan;
    }

    public void setLdDateRetPlan(Date ldDateRetPlan) {
        this.ldDateRetPlan = ldDateRetPlan;
    }

    public Date getLdDateRetAct() {
        return ldDateRetAct;
    }

    public void setLdDateRetAct(Date ldDateRetAct) {
        this.ldDateRetAct = ldDateRetAct;
    }

    public Integer getLdOverDay() {
        return ldOverDay;
    }

    public void setLdOverDay(Integer ldOverDay) {
        this.ldOverDay = ldOverDay;
    }

    public Double getLdOverMoney() {
        return ldOverMoney;
    }

    public void setLdOverMoney(Double ldOverMoney) {
        this.ldOverMoney = ldOverMoney;
    }

    public Double getLdPunishMoney() {
        return ldPunishMoney;
    }

    public void setLdPunishMoney(Double ldPunishMoney) {
        this.ldPunishMoney = ldPunishMoney;
    }

    public Boolean getLsHasReturn() {
        return lsHasReturn;
    }

    public void setLsHasReturn(Boolean lsHasReturn) {
        this.lsHasReturn = lsHasReturn;
    }

    public String getOperatorBorrow() {
        return operatorBorrow;
    }

    public void setOperatorBorrow(String operatorBorrow) {
        this.operatorBorrow = operatorBorrow;
    }

    public String getOperatorReturn() {
        return operatorReturn;
    }

    public void setOperatorReturn(String operatorReturn) {
        this.operatorReturn = operatorReturn;
    }
}