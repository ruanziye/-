<%@ page import="com.study.library.service.CommonService" %>
<%@ page import="com.study.library.dto.LoginUser" %>
<%@ page import="com.study.library.util.HttpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  //登录状态检查
  LoginUser loginUser=CommonService.getLoginUserFromSession(request);
  request.setAttribute("ctxpath",request.getContextPath());
  if(loginUser==null){
    //没有登录
    HttpUtils.redirectWithAlert(request,response,"/login.jsp","您还未登录或登录状态已失效，请重新登录");
    return;
  }
%>
