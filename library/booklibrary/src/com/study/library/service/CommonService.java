package com.study.library.service;

import com.study.library.dao.ReaderDao;
import com.study.library.dto.LoginUser;
import com.study.library.model.Reader;
import com.study.library.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 完成一些公共业务逻辑实现
 * 登录、注销
 */
public class CommonService {

    private final  static String LOGIN_KEY="loginUser";
    private static ReaderDao readerDao=new ReaderDao();


    /**
     * 登录验证
     * @param id
     * @param pwd
     * @return
     */
    public  static LoginUser login(Integer id, String pwd){
        Reader reader=readerDao.load(id);
        if(reader==null) return null;
        pwd=pwd.toLowerCase();
        pwd= HttpUtils.md5(pwd);
        if(!reader.getRdPwd().equals(pwd))
            return null;
        return new LoginUser(reader);
    }

    /**
     * 登录成功后，需要把登录用户信息存储到http session里，这样后台操作就知道当前登录用户是谁
     * @param user
     * @param request
     */
    public static void setLoginUserIntoSession(LoginUser user, HttpServletRequest request){
        request.getSession().setAttribute(LOGIN_KEY,user);
    }

    /**
     * 登录退出后，需要清除当前登录用户信息
     * @param request
     */
    public static  void removeLoginUserFromSession(HttpServletRequest request){
        request.getSession().removeAttribute(LOGIN_KEY);
    }

    /**
     * 从http session读取当前登录用户
     * @param request
     * @return
     */
    public static LoginUser getLoginUserFromSession(HttpServletRequest request){
        return (LoginUser) request.getSession().getAttribute(LOGIN_KEY);
    }

    /**
     * 修改密码
     * @param id
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public static String changePwd(Integer id, String oldPwd, String newPwd) {
        Reader reader=readerDao.load(id);
        if(reader==null) return "用户不存在";
        oldPwd=oldPwd.toLowerCase();
        oldPwd= HttpUtils.md5(oldPwd);
        if(!reader.getRdPwd().equals(oldPwd))
            return "旧密码错误";
        newPwd=newPwd.toLowerCase();
        newPwd= HttpUtils.md5(newPwd);
        reader.setRdPwd(newPwd);
        readerDao.update(reader);
        return null;
    }
}
