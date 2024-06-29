package com.study.library.servlet;

import com.study.library.service.CommonService;
import com.study.library.util.HttpUtils;
import org.apache.commons.beanutils.BeanUtils;
import sun.reflect.misc.ReflectUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 视图层请求入口基础类
 */
public abstract class BaseServlet extends HttpServlet {

    private static final String[] ACTIONS=new String[]{"login","logout"};

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action==null||action.equals("")) action="index";
        try {
            String finalAction = action;
            if(!Arrays.stream(ACTIONS).anyMatch(str-> finalAction.equals(str))){
                //如果不是登录、注销，其他任何操作，都需要检查是否已经登录
                if(CommonService.getLoginUserFromSession(req)==null){
                    HttpUtils.redirectWithAlert(req,resp,"/login.jsp","您还未登录或登录状态已失效，请重新登录");
                    return;
                }
            }
            Method method=this.getClass().getMethod(action,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void index(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        resp.getWriter().write(this.getServletName());
    }

}
