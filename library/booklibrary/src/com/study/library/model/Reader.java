package com.study.library.model;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.util.Date;

/**
 * 读者
 */
public class Reader {
    /**
     * 读者编号/借书证号【主键】
     */
    private Integer rdID;

    /**
     * 读者姓名
     */
    private String rdName;

    /**
     * 性别：男/女
     */
    private String rdSex;

    /**
     * 读者类别【外键TB_ReaderType】【非空】
     */
    private Integer rdType;

    /**
     * 单位代码/单位名称
     */
    private String rdDept;

    /**
     * 电话号码
     */
    private String rdPhone;

    /**
     * 电子邮箱
     */
    private String rdEmail;

    /**
     * 读者登记日期/办证日期，使用java.util.Date表示。
     */
    private Date rdDateReg;

    /**
     * 读者照片，使用BLOB类型表示。
     */
    private byte[] rdPhoto;

    /**
     * 证件状态，3个：有效、挂失、注销。使用字符串表示。
     */
    private String rdStatus;

    /**
     * 已借书数量（缺省值0）。使用Integer类型表示。
     */
    private Integer rdBorrowQty;

    /**
     * 读者密码（初值123），可加密存储，使用字符串表示。
     */
    private String rdPwd;

    /**
     * 管理角色：0-读者、1-借书证管理、2-图书管理、4-借阅管理、8-系统管理，可组合，使用Integer类型表示。
     */
    private Integer rdAdminRoles;

    public Integer getRdID() {
        return rdID;
    }

    public void setRdID(Integer rdID) {
        this.rdID = rdID;
    }

    public String getRdName() {
        return rdName;
    }

    public void setRdName(String rdName) {
        this.rdName = rdName;
    }

    public String getRdSex() {
        return rdSex;
    }

    public void setRdSex(String rdSex) {
        this.rdSex = rdSex;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getRdDept() {
        return rdDept;
    }

    public void setRdDept(String rdDept) {
        this.rdDept = rdDept;
    }

    public String getRdPhone() {
        return rdPhone;
    }

    public void setRdPhone(String rdPhone) {
        this.rdPhone = rdPhone;
    }

    public String getRdEmail() {
        return rdEmail;
    }

    public void setRdEmail(String rdEmail) {
        this.rdEmail = rdEmail;
    }

    public Date getRdDateReg() {
        return rdDateReg;
    }

    public void setRdDateReg(Date rdDateReg) {
        this.rdDateReg = rdDateReg;
    }

    public byte[] getRdPhoto() {
        if(rdPhoto!=null && rdPhoto.length==0) return null;
        return rdPhoto;
    }

    public void setRdPhoto(byte[] rdPhoto) {
        this.rdPhoto = rdPhoto;
    }

    public String getRdStatus() {
        return rdStatus;
    }

    public void setRdStatus(String rdStatus) {
        this.rdStatus = rdStatus;
    }

    public Integer getRdBorrowQty() {
        return rdBorrowQty;
    }

    public void setRdBorrowQty(Integer rdBorrowQty) {
        this.rdBorrowQty = rdBorrowQty;
    }

    public String getRdPwd() {
        return rdPwd;
    }

    public void setRdPwd(String rdPwd) {
        this.rdPwd = rdPwd;
    }

    public Integer getRdAdminRoles() {
        return rdAdminRoles;
    }

    public void setRdAdminRoles(Integer rdAdminRoles) {
        this.rdAdminRoles = rdAdminRoles;
    }
}