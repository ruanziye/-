package com.study.library.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库操作工具类
 */
public class JdbcUtils {

	private static Log log=LogFactory.getLog(JdbcUtils.class);
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

//  静态代码块
//  类一加载，就执行；
//  并且只执行一次
	static {
		try {
			driver = "com.mysql.cj.jdbc.Driver";
			url = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8&autoReconnect=true";
			user = "root";
			password = "123456";
			Class.forName(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}

//  1:获得连接
	public static Connection getConnection() {
//      注册驱动
		Connection con = null;
		try {
//          连接数据库
//          Statement createStatement()：创建一个 Statement 对象来将 SQL 语句发送到数据库。 
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			throw new RuntimeException("数据库链接失败",e);
		}
//      返回   连接对象
		return con;
	}

	public static <T> List<T> list(String sqlStr, Converter<T>  converter, Object... params) {
		log.info("执行SQL语句："+sqlStr);
		List<T> retList = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			stmt = conn.prepareStatement(sqlStr);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();

			while (rs.next()) {
				T item=converter.convert(rs);
				if(item!=null)
					retList.add(item);
			}
		} catch (SQLException e) {

			throw new RuntimeException("SQL执行失败",e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				rs = null;
				stmt = null;
				conn = null;
			} catch (SQLException e) {

				throw new RuntimeException("SQL执行失败",e);
			}
		}

		return retList;
	}


	/**
	 * 执行查询语句 返回列表集合
	 * @param sqlStr
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> list(String sqlStr, Object... params) {
		log.info("执行SQL语句："+sqlStr);
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			stmt = conn.prepareStatement(sqlStr);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();

			while (rs.next()) {
				Map<String,Object> item=new HashMap<>();
				for (int i = 1; i <= colNum; i++) {
					String label=rsmd.getColumnLabel(i);
					item.put(label,rs.getObject(i));
				}
				retList.add(item);
			}
		} catch (SQLException e) {
			
			throw new RuntimeException("SQL执行失败",e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				rs = null;
				stmt = null;
				conn = null;
			} catch (SQLException e) {
				
				throw new RuntimeException("SQL执行失败",e);
			}
		}

		return retList;
	}

	/**
	 * 执行insert、update、delete之类的操作语句
	 * @param sqlStr
	 * @param params
	 * @return
	 */
	public static int exec(String sqlStr, Object... params) {
		log.info("执行SQL语句："+sqlStr);
		int rows = 0;
		Connection conn = getConnection();

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sqlStr);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			
			throw new RuntimeException("SQL执行失败",e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				stmt = null;
				conn = null;
			} catch (SQLException e) {
				
				throw new RuntimeException("SQL执行失败",e);
			}
		}
		return rows;
	}

	/**
	 * 获取第一行第一个字段的数据
	 * @param sqlStr
	 * @param params
	 * @return
	 */
	public static String first(String sqlStr, Object... params) {
		
		Map<String,Object> item=find(sqlStr,params);
		if(item==null||item.size()==0) return null;
		Object ret=null;
		for(Object value : item.values()) {
			ret=value;break;
		}
		if(ret==null)
			return null;
		else
			return ret.toString();
	}
	
	/**
	 * 执行统计查询语句
	 * @param sqlStr
	 * @param params
	 * @return
	 */
	public static Integer count(String sqlStr,Object...params) {
		String str=first(sqlStr,params);
		if(StringUtils.isEmpty(str))
			return null;
		else
			return Integer.valueOf(str);
	}

	/**
	 * 获取第一行的数据
	 * @param sqlStr
	 * @param params
	 * @return
	 */
	public static Map<String,Object> find(String sqlStr, Object... params) {
		List<Map<String,Object>> list = list(sqlStr, params);
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}

	/**
	 * 获取一行数据，转换为bean对象
	 * @param sqlStr
	 * @param converter
	 * @param params
	 * @return
	 * @param <T>
	 */
	public static <T> T find(String sqlStr,Converter<T> converter, Object...params){
		List<T> list=list(sqlStr,converter,params);
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}

	/**
	 * 批量执行操作语句
	 * @param sqlStrArr
	 * @return
	 */
	public static void executeUpdateBatch(String[] sqlStrArr) {

		Connection conn = getConnection();
		Statement stmt = null;
		try {
			conn.setAutoCommit(false); //设置为手工提交事务模式
			stmt = conn.createStatement();
		} catch (SQLException e) {
			
			throw new RuntimeException("SQL执行失败",e);
		}

		for (int i = 0; i < sqlStrArr.length; i++) {
			String sqlStr = sqlStrArr[i];
			log.info("执行SQL语句："+sqlStr);
			try {
				stmt.executeUpdate(sqlStr);
			} catch (SQLException e) {
				try{ conn.rollback(); }catch(Exception e2) {}
				throw new RuntimeException("SQL执行失败",e);
			}
		}
		
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException("SQL执行失败",e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					throw new RuntimeException("SQL执行失败",e);
				}
			}
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException("SQL执行失败",e);
				}
			}
		}
		
	}


	/**
	 * 数据转换器
	 * @param <T>
	 */
	public interface  Converter<T>{
		T convert(ResultSet rs) throws SQLException;

		default byte[] getBytes(ResultSet rs,String name) throws SQLException {
			Blob blob=rs.getBlob(name);
			if(blob==null) return null;
			return blob.getBytes(1,(int)blob.length());
		}
	}
}
