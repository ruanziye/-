package com.study.library.dao;

import cn.hutool.core.util.StrUtil;
import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.ReaderType;
import com.study.library.util.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读者类别 -- 数据库操作
 */
public class ReaderTypeDao {

    /**
     * 执行分页查询
     * @param request
     * @return
     */
    public PageResponse<ReaderType> list(PageRequest request){
        PageResponse<ReaderType> response=new PageResponse<>();//分页查询结果

        StringBuilder sql=new StringBuilder();
        sql.append(" from tb_readertype where 1=1 ");

        List<Object> params=new ArrayList<>(); //sql查询参数
        {
            //构建筛选语句
            String rdName=request.getFilter().get("rdName");
            if(StrUtil.isNotEmpty(rdName)) {
                sql.append(" and rdTypeName like concat('%',?,'%')");
                params.add(rdName);
            }
        }

        //查询记录总数
        response.setTotal(JdbcUtils.count("select count(*) "+sql,params.toArray()));
        if(response.getTotal()>0) {
            int start = (request.getPage() - 1) * request.getSize();
            sql.append(" order by rdType desc ");
            sql.append("limit ").append(start).append(",").append(request.getSize());
            List<ReaderType> list=JdbcUtils.list("select * " + sql, new ReaderTypeConverter(), params.toArray());
            response.setList(list);
        }

        return response;
    }

    /**
     * 查询单个
     * @param rdType
     * @return
     */
    public ReaderType load(Integer rdType){
        return JdbcUtils.find("select * from tb_ReaderType where rdType=?", new ReaderTypeConverter(), rdType);
    }

    /**
     * 根据类型名称查询
     * @param rdTypeName
     * @return
     */
    public ReaderType loadByTypeName(String rdTypeName) {
        return JdbcUtils.find("select * from tb_readertype where rdTypeName=?",new ReaderTypeConverter(),rdTypeName);
    }

    /**
     * 添加一条读者类别
     * @param bean
     */
    public int insert(ReaderType bean) {
        String sql = "INSERT INTO tb_readertype (rdType, rdTypeName, CanLendQty, CanLendDay, CanContinueTimes, PunishRate, DateValid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtils.exec(sql, bean.getRdType(), bean.getRdTypeName(), bean.getCanLendQty(), bean.getCanLendDay(), bean.getCanContinueTimes(), bean.getPunishRate(), bean.getDateValid());
    }

    /**
     * 更新读者类别
     * @param bean
     * @return
     */
    public int update(ReaderType bean){
        String sql = "UPDATE tb_readertype SET rdTypeName = ?, CanLendQty = ?, CanLendDay = ?, CanContinueTimes = ?, PunishRate = ?, DateValid = ? WHERE rdType = ?";
        return JdbcUtils.exec(sql, bean.getRdTypeName(), bean.getCanLendQty(), bean.getCanLendDay(), bean.getCanContinueTimes(), bean.getPunishRate(), bean.getDateValid(), bean.getRdType());
    }

    /**
     * 删除读者类别
     * @param rdType
     * @return
     */
    public int delete(Integer rdType){
        String sql = "DELETE FROM tb_readertype WHERE rdType = ?";
        return JdbcUtils.exec(sql, rdType);
    }

    /**
     * 数据库映射
     */
    public static class ReaderTypeConverter implements JdbcUtils.Converter<ReaderType>{
        @Override
        public ReaderType convert(ResultSet rs) throws SQLException {
            ReaderType readerType = new ReaderType();
            // 从ResultSet中获取所有字段并设置到ReaderType对象中
            readerType.setRdType(rs.getInt("rdType"));
            readerType.setRdTypeName(rs.getString("rdTypeName"));
            readerType.setCanLendQty(rs.getInt("CanLendQty"));
            readerType.setCanLendDay(rs.getInt("CanLendDay"));
            readerType.setCanContinueTimes(rs.getInt("CanContinueTimes"));
            readerType.setPunishRate(rs.getDouble("PunishRate"));
            readerType.setDateValid(rs.getInt("DateValid"));
            return readerType;
        }
    }

}
