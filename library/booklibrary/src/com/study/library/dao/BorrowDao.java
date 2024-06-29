package com.study.library.dao;

import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.Borrow;
import com.study.library.util.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 借阅 -- 数据库操作
 */
public class BorrowDao {

    /**
     * 执行分页查询
     * @param request
     * @return
     */
    public PageResponse<Borrow> list(PageRequest request){
        PageResponse<Borrow> response=new PageResponse<>();//分页查询结果

        StringBuilder sql=new StringBuilder();
        sql.append(" from tb_borrow ");

        List<Object> params=new ArrayList<>(); //sql查询参数
        {
            //构建筛选语句
        }

        //查询记录总数
        response.setTotal(JdbcUtils.count("select count(*) "+sql,params));
        if(response.getTotal()>0) {
            int start = (request.getPage() - 1) * request.getSize();
            sql.append("limit ").append(start).append(",").append(request.getSize());
            sql.append(" order by BorrowID desc ");
            List<Borrow> list=JdbcUtils.list("select * " + sql, new BorrowConverter(), params);
            response.setList(list);
        }

        return response;
    }

    /**
     * 查询单个
     * @param BorrowID
     * @return
     */
    public Borrow load(Integer BorrowID){
        return JdbcUtils.find("select * from tb_borrow where BorrowID=?", new BorrowConverter(), BorrowID);
    }

    /**
     * 添加书籍
     * @param Borrow
     * @return
     */
    public int insert(Borrow Borrow){
        String sql = "INSERT INTO tb_borrow ( rdID, bkID, ldContinueTimes, ldDateOut, ldDateRetPlan, ldDateRetAct, ldOverDay, ldOverMoney, ldPunishMoney, lsHasReturn, OperatorBorrow, OperatorReturn) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtils.exec(sql,
                Borrow.getRdID(),
                Borrow.getBkID(),
                Borrow.getLdContinueTimes(),
                Borrow.getLdDateOut(),
                Borrow.getLdDateRetPlan(),
                Borrow.getLdDateRetAct(),
                Borrow.getLdOverDay(),
                Borrow.getLdOverMoney(),
                Borrow.getLdPunishMoney(),
                Borrow.getLsHasReturn(),
                Borrow.getOperatorBorrow(),
                Borrow.getOperatorReturn());
    }

    /**
     * 更新数据
     * @param Borrow
     * @return
     */
    public int update(Borrow Borrow){
        String sql = "UPDATE tb_borrow SET rdID = ?, bkID = ?, ldContinueTimes = ?, ldDateOut = ?, ldDateRetPlan = ?, ldDateRetAct = ?, ldOverDay = ?, ldOverMoney = ?, ldPunishMoney = ?, lsHasReturn = ?, OperatorBorrow = ?, OperatorReturn = ? WHERE BorrowID = ?";

        // 假设 Borrow 类有对应的 getter 方法来获取各个字段的值
        return JdbcUtils.exec(
                sql,
                Borrow.getRdID(),
                Borrow.getBkID(),
                Borrow.getLdContinueTimes(),
                Borrow.getLdDateOut(),
                Borrow.getLdDateRetPlan(),
                Borrow.getLdDateRetAct(),
                Borrow.getLdOverDay(),
                Borrow.getLdOverMoney(),
                Borrow.getLdPunishMoney(),
                Borrow.getLsHasReturn(),
                Borrow.getOperatorBorrow(),
                Borrow.getOperatorReturn(),
                Borrow.getBorrowID() // WHERE 子句的条件
        );
    }

    /**
     * 删除
     * @param BorrowID
     * @return
     */
    public int delete(Integer BorrowID){
        return JdbcUtils.exec("delete from tb_borrow where BorrowID=?",BorrowID);
    }

    /**
     * 获取未归还的借阅记录列表
     * @param rdID
     * @return
     */
    public List<Borrow> listNotReturned(Integer rdID) {
        String sql="select * from tb_borrow where lsHasReturn=0 and rdID=? order by BorrowID";
        return JdbcUtils.list(sql,new BorrowConverter(),rdID);
    }

    /**
     * 根据图书书号查询未归还的借阅记录
     * @param bkCode
     * @return
     */
    public Borrow queryByBkCode(String bkCode) {
        String sql="select * from tb_borrow where lsHasReturn=0 and bkID in (select bkID from tb_book where bkCode=?)";
        return JdbcUtils.find(sql,new BorrowConverter(),bkCode);
    }

    /**
     * 更新原借书证号关联的借阅记录，关联到新的借书证号上
     * @param oldRdID 原借书证号
     * @param newRdID 新借书证号
     */
    public void updateReader(Integer oldRdID, Integer newRdID) {
        String sql="update tb_borrow set rdID=? where rdID=?";
        JdbcUtils.exec(sql,newRdID,oldRdID);
    }

    /**
     * 数据库映射
     */
    public static class BorrowConverter implements JdbcUtils.Converter<Borrow>{

        @Override
        public Borrow convert(ResultSet rs) throws SQLException {
            Borrow Borrow = new Borrow();

            // 获取ResultSet中的数据并设置到Borrow对象中
            Borrow.setBorrowID(rs.getInt("BorrowID"));
            Borrow.setRdID(rs.getInt("rdID"));
            Borrow.setBkID(rs.getInt("bkID"));
            Borrow.setLdContinueTimes(rs.getInt("ldContinueTimes"));
            Borrow.setLdDateOut(rs.getDate("ldDateOut"));
            Borrow.setLdDateRetPlan(rs.getDate("ldDateRetPlan"));
            Borrow.setLdDateRetAct(rs.getDate("ldDateRetAct"));
            Borrow.setLdOverDay(rs.getInt("ldOverDay"));
            Borrow.setLdOverMoney(rs.getDouble("ldOverMoney"));
            Borrow.setLdPunishMoney(rs.getDouble("ldPunishMoney"));
            Borrow.setLsHasReturn(rs.getBoolean("lsHasReturn"));
            Borrow.setOperatorBorrow(rs.getString("OperatorBorrow"));
            Borrow.setOperatorReturn(rs.getString("OperatorReturn"));

            return Borrow;
        }
    }

}
