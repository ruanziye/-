package com.study.library.util;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.img.ImgUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面帮助类
 */
public class HttpUtils {

	/**
	 * 页面重定向
	 * @param req
	 * @param res
	 * @param path
	 */
	public static void redirect(HttpServletRequest req, HttpServletResponse res,String path) {
		try {
			if(!path.startsWith("/")) 
				res.sendRedirect(path);
			else
				res.sendRedirect(req.getContextPath()+path);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 页面重定向，并且带错误提示
	 * @param req
	 * @param res
	 * @param path
	 * @param msg
	 */
	public static void redirectWithAlert(HttpServletRequest req, HttpServletResponse res,String path,String msg) {
		alert(req,msg);
		try {
			if(!path.startsWith("/")) 
				res.sendRedirect(path);
			else
				res.sendRedirect(req.getContextPath()+path);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 页面转向，带提示信息
	 * @param req
	 * @param resp
	 * @param path
	 * @param msg
	 */
	public static void forwardWithAlert(HttpServletRequest req, HttpServletResponse resp, String path, String msg) throws ServletException, IOException {
		alert(req,msg);
		//if(path.startsWith("/")) path=req.getContextPath()+path;
		req.getRequestDispatcher(path).forward(req,resp);
	}
	
	/**
	 * 用对话框显示消息信息
	 * @param req
	 * @param msg
	 */
	public static void alert(HttpServletRequest req,String msg) {
		req.getSession().setAttribute("__alert_info", msg);
	}

	/**
	 * 获取需要显示的消息，并从session里删除，防止重复提示
	 * @param req
	 * @return
	 */
	public static String getMessage(HttpServletRequest req){
		String msg=(String)req.getSession().getAttribute("__alert_info");
		if(msg!=null)
			req.getSession().removeAttribute("__alert_info");
		return msg;
	}

	/**
	 * md5加密
	 * @param input
	 * @return
	 */
	public static String md5(String input) {
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext.substring(8,28);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}


	/**
	 * 利用java反射机制 页面表单中相同名字的值设置到bean相同的字段名上
	 * @param req
	 * @return
	 * @param <T>
	 */
	public static <T> T getBean(HttpServletRequest req,Class<T> clazz) throws Exception {
   		Map<String,Object> params=new HashMap<>();
		Enumeration<String> names= req.getParameterNames();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			String value=req.getParameter(name);
			params.put(name,value);
		}

		T bean=clazz.newInstance();
		return BeanUtil.fillBeanWithMap(params,bean,true);
	}

	/**
	 * 获取图片 base64数据，用来显示在网页上
	 * @param img
	 * @return
	 */
	public static String getImageBase64(byte[] img){
		return ImgUtil.toBase64DataUri(ImgUtil.toImage(img),"png");
	}


}
