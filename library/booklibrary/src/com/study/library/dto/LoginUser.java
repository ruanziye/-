package com.study.library.dto;

import com.study.library.model.Reader;

/**
 * 登录成功，登录用户信息保存到这个bean里
 * bean保存 http session里
 */
public class LoginUser {
    private Reader reader;
    public LoginUser(Reader reader){
        this.reader=reader;
    }

    /**
     * 获取读者ID（借书证号）
     * @return
     */
    public Integer getId(){
        return reader.getRdID();
    }

    /**
     * 获取读者姓名（管理员 姓名）
     * @return
     */
    public String getName(){
        return reader.getRdName();
    }

    /**
     * 获取读者（借书证）状态
     * @return
     */
    public String getStatus(){
        return reader.getRdStatus();
    }

    /**
     * 是否为借书证管理员
     * @return
     */
    public boolean isCardAdmin(){
        return (reader.getRdAdminRoles() & 1)!=0;
    }

    /**
     * 是否为图书管理员
     * @return
     */
    public boolean isBookAdmin(){
        return (reader.getRdAdminRoles() & 2)!=0;
    }

    /**
     * 是否为借阅管理员
     * @return
     */
    public boolean isBorrowingAdmin(){
        return (reader.getRdAdminRoles()&4)!=0;
    }

    /**
     * 是否为系统管理员
     * @return
     */
    public boolean  isSystemAdmin(){
        return (reader.getRdAdminRoles() & 8)!=0;
    }
}
