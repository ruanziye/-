package com.study.library.dao;

import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.Dept;
import com.study.library.util.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位 -- 数据库操作
 */
public class DeptDao {

    /**
     * 执行分页查询
     * @param request
     * @return
     */
    public List<Dept> list(){
        return JdbcUtils.list("select * from tb_dept order by dpID asc ",new DeptConverter());
    }

    /**
     * 查询单个
     * @param dpID
     * @return
     */
    public Dept load(Integer dpID){
        return JdbcUtils.find("select * from tb_dept where dpID=?", new DeptConverter(), dpID);
    }

    /**
     * 添加书籍
     * @param dept
     * @return
     */
    public int insert(Dept dept){
        String sql = "INSERT INTO tb_dept ( dpID,dpName) VALUES ( ?, ?)";
        return JdbcUtils.exec(sql, dept.getDpID(),dept.getDpName());
    }

    /**
     * 更新数据
     * @param dept
     * @return
     */
    public int update(Dept dept){
        String sql = "update tb_dept set dpName=? where dpID=?";
        return JdbcUtils.exec(sql, dept.getDpName(),dept.getDpID());
    }

    /**
     * 删除
     * @param dpID
     * @return
     */
    public int delete(Integer dpID){
        return JdbcUtils.exec("delete from tb_dept where dpID=?",dpID);
    }

    /**
     * 数据库映射
     */
    public static class DeptConverter implements JdbcUtils.Converter<Dept>{

        @Override
        public Dept convert(ResultSet rs) throws SQLException {
            Dept dept = new Dept();
            dept.setDpID(rs.getInt("dpID"));
            dept.setDpName(rs.getString("dpName"));
            return dept;
        }
    }

}
