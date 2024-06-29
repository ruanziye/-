package com.study.library.model;

import java.util.Date;

/**
 * 图书信息Java Bean类
 */
public class Book {

    // 图书序号【自增，主键】
    private Integer bkID;

    // 图书编号或条码号（前文中的书号）
    private String bkCode;

    // 书名
    private String bkName;

    // 作者
    private String bkAuthor;

    // 出版社
    private String bkPress;

    // 出版日期
    private Date bkDatePress;

    // ISBN书号
    private String bkISBN;

    // 分类号（如：TP316-21/123）
    private String bkCatalog;

    // 语言：0-中文，1-英文，2-日文，3-俄文，4-德文，5-法文
    private Integer bkLanguage;

    // 页数
    private Integer bkPages;

    // 价格
    private Double bkPrice;

    // 入馆日期
    private Date bkDateIn;

    // 内容简介
    private String bkBrief;

    // 图书封面照片
    private byte[] bkCover;

    // 图书状态：在馆、借出、遗失、变卖、销毁
    private String bkStatus;

    public Integer getBkID() {
        return bkID;
    }

    public void setBkID(Integer bkID) {
        this.bkID = bkID;
    }

    public String getBkCode() {
        return bkCode;
    }

    public void setBkCode(String bkCode) {
        this.bkCode = bkCode;
    }

    public String getBkName() {
        return bkName;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public String getBkAuthor() {
        return bkAuthor;
    }

    public void setBkAuthor(String bkAuthor) {
        this.bkAuthor = bkAuthor;
    }

    public String getBkPress() {
        return bkPress;
    }

    public void setBkPress(String bkPress) {
        this.bkPress = bkPress;
    }

    public Date getBkDatePress() {
        return bkDatePress;
    }

    public void setBkDatePress(Date bkDatePress) {
        this.bkDatePress = bkDatePress;
    }

    public String getBkISBN() {
        return bkISBN;
    }

    public void setBkISBN(String bkISBN) {
        this.bkISBN = bkISBN;
    }

    public String getBkCatalog() {
        return bkCatalog;
    }

    public void setBkCatalog(String bkCatalog) {
        this.bkCatalog = bkCatalog;
    }

    public Integer getBkLanguage() {
        return bkLanguage;
    }

    public void setBkLanguage(Integer bkLanguage) {
        this.bkLanguage = bkLanguage;
    }

    public Integer getBkPages() {
        return bkPages;
    }

    public void setBkPages(Integer bkPages) {
        this.bkPages = bkPages;
    }

    public Double getBkPrice() {
        return bkPrice;
    }

    public void setBkPrice(Double bkPrice) {
        this.bkPrice = bkPrice;
    }

    public Date getBkDateIn() {
        return bkDateIn;
    }

    public void setBkDateIn(Date bkDateIn) {
        this.bkDateIn = bkDateIn;
    }

    public String getBkBrief() {
        return bkBrief;
    }

    public void setBkBrief(String bkBrief) {
        this.bkBrief = bkBrief;
    }

    public byte[] getBkCover() {
        return bkCover;
    }

    public void setBkCover(byte[] bkCover) {
        this.bkCover = bkCover;
    }

    public String getBkStatus() {
        return bkStatus;
    }

    public void setBkStatus(String bkStatus) {
        this.bkStatus = bkStatus;
    }
}
