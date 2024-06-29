package com.study.library.servlet;

import com.study.library.dto.LoginUser;
import com.study.library.service.CommonService;
import com.study.library.util.HttpUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图层 -- 公共操作入口
 * 登录、注销
 */
@WebServlet("/index")
public class CommonServlet extends BaseServlet{

    /**
     * 登录验证
     * @param req
     * @param resp
     * @throws Exception
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws  Exception  {
        Integer id=null;
        String pwd=req.getParameter("pwd");
        try{
            id=Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException ex){
            HttpUtils.redirectWithAlert(req,resp,"login.jsp","用户号必须为数字");
            return;
        }
        LoginUser user= CommonService.login(id,pwd);
        if(user==null){
            HttpUtils.redirectWithAlert(req,resp,"login.jsp","用户号或密码输入错误");
            return;
        }
        if(!user.getStatus().equals("有效")){
            HttpUtils.redirectWithAlert(req,resp,"login.jsp","读者（借书证）当前状态为"+user.getStatus()+"，无法登录系统！");
            return;
        }
        CommonService.setLoginUserIntoSession(user,req); //存储当前登录用户
        HttpUtils.redirect(req,resp,"index.jsp"); //登录成功后，转向到后台首页
    }

    /**
     * 登录退出
     * @param req
     * @param resp
     * @throws Exception
     */
    public void logout(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        CommonService.removeLoginUserFromSession(req);
        HttpUtils.redirect(req,resp,"login.jsp");
    }

    /**
     * 修改密码
     * @param req
     * @param resp
     * @throws Exception
     */
    public void changePwd(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String oldPwd= req.getParameter("oldPwd");
        String newPwd=req.getParameter("newPwd");
        LoginUser user=CommonService.getLoginUserFromSession(req);
        String msg=CommonService.changePwd(user.getId(),oldPwd,newPwd);
        if(msg==null) msg="密码修改成功";
        HttpUtils.redirectWithAlert(req,resp,"change_pwd.jsp",msg);
    }
}
