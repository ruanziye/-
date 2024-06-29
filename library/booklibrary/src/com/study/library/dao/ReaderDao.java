package com.study.library.dao;

import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.Reader;
import com.study.library.util.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读者 -- 数据库操作
 */
public class ReaderDao {

    /**
     * 执行分页查询
     * @param request
     * @return
     */
    public PageResponse<Reader> list(PageRequest request){
        PageResponse<Reader> response=new PageResponse<>();//分页查询结果

        StringBuilder sql=new StringBuilder();
        sql.append(" from tb_reader ");

        List<Object> params=new ArrayList<>(); //sql查询参数
        {
            //构建筛选语句
        }

        //查询记录总数
        response.setTotal(JdbcUtils.count("select count(*) "+sql,params));
        if(response.getTotal()>0) {
            int start = (request.getPage() - 1) * request.getSize();
            sql.append("limit ").append(start).append(",").append(request.getSize());
            sql.append(" order by rdID desc ");
            List<Reader> list=JdbcUtils.list("select * " + sql, new ReaderConverter(), params);
            response.setList(list);
        }

        return response;
    }

    /**
     * 查询单个
     * @param rdID
     * @return
     */
    public Reader load(Integer rdID){
        return JdbcUtils.find("select * from tb_reader where rdID=?", new ReaderConverter(), rdID);
    }

    /**
     * 添加书籍
     * @param reader
     * @return
     */
    public int insert(Reader reader){
        String sql = "INSERT INTO tb_reader (rdID, rdName, rdSex, rdType, rdDept, rdPhone, rdEmail, rdDateReg, rdPhoto, rdStatus, rdBorrowQty, rdPwd, rdAdminRoles) VALUES ( ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtils.exec(sql,reader.getRdID(), reader.getRdName(), reader.getRdSex(), reader.getRdType(), reader.getRdDept(), reader.getRdPhone(), reader.getRdEmail(), reader.getRdDateReg(), reader.getRdPhoto(), reader.getRdStatus(), reader.getRdBorrowQty(), reader.getRdPwd(), reader.getRdAdminRoles());
    }

    /**
     * 更新数据
     * @param reader
     * @return
     */
    public int update(Reader reader){
        String sql = "UPDATE tb_reader SET rdName = ?, rdSex = ?, rdType = ?, rdDept = ?, rdPhone = ?, rdEmail = ?, rdDateReg = ?, rdPhoto = ?, rdStatus = ?, rdBorrowQty = ?, rdPwd = ?, rdAdminRoles = ? WHERE rdID = ?";
        return JdbcUtils.exec(sql, reader.getRdName(), reader.getRdSex(), reader.getRdType(), reader.getRdDept(), reader.getRdPhone(), reader.getRdEmail(), reader.getRdDateReg(), reader.getRdPhoto(), reader.getRdStatus(), reader.getRdBorrowQty(), reader.getRdPwd(), reader.getRdAdminRoles(),reader.getRdID());
    }

    /**
     * 删除
     * @param rdID
     * @return
     */
    public int delete(Integer rdID){
        return JdbcUtils.exec("delete from tb_reader where rdID=?",rdID);
    }

    /**
     * 判断读者是否存在
     * @param searchBean
     * @return
     */
    public int countByTypeAndDeptAndName(Reader searchBean) {
        return JdbcUtils.count("select count(*) from tb_reader where rdName=? and rdDept=? and rdName=?",searchBean.getRdName(),
                searchBean.getRdDept(),searchBean.getRdName());
    }

    /**
     * 根据读者类别、单位、姓名查询读者信息
     * @param searchBean
     * @return
     */
    public Reader loadByTypeAndDeptAndName(Reader searchBean) {
         return JdbcUtils.find("select * from tb_reader where rdName=? and rdDept=? and rdName=?",new ReaderConverter(), searchBean.getRdName(),
                searchBean.getRdDept(),searchBean.getRdName());
    }


    /**
     * 数据库映射
     */
    public static class ReaderConverter implements JdbcUtils.Converter<Reader>{

        @Override
        public Reader convert(ResultSet rs) throws SQLException {
            Reader reader = new Reader();

            // 获取ResultSet中的所有字段并设置到Reader对象中
            reader.setRdID(rs.getInt("rdID"));
            reader.setRdName(rs.getString("rdName"));
            reader.setRdSex(rs.getString("rdSex"));
            reader.setRdType(rs.getInt("rdType"));
            reader.setRdDept(rs.getString("rdDept"));
            reader.setRdPhone(rs.getString("rdPhone"));
            reader.setRdEmail(rs.getString("rdEmail"));
            reader.setRdDateReg(rs.getTimestamp("rdDateReg")); // 需要注意的是，这里使用了getTimestamp方法，因为datetime类型通常被存储为时间戳
            reader.setRdPhoto(getBytes(rs,"rdPhoto"));
            reader.setRdStatus(rs.getString("rdStatus"));
            reader.setRdBorrowQty(rs.getInt("rdBorrowQty"));
            reader.setRdPwd(rs.getString("rdPwd"));
            reader.setRdAdminRoles(rs.getInt("rdAdminRoles"));

            return reader;
        }
    }

}
