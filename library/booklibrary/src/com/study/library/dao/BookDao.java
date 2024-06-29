package com.study.library.dao;

import cn.hutool.core.util.StrUtil;
import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.Book;
import com.study.library.util.JdbcUtils;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图书 -- 数据库操作
 */
public class BookDao {

    /**
     * 执行分页查询
     * @param request
     * @return
     */
    public PageResponse<Book> list(PageRequest request){
        PageResponse<Book> response=new PageResponse<>();//分页查询结果

        StringBuilder sql=new StringBuilder();
        sql.append(" from tb_book where 1=1 ");

        List<Object> params=new ArrayList<>(); //sql查询参数
        {
            //构建筛选语句
            String keyword=request.getFilter().get("keyword");
            if(StrUtil.isNotEmpty(keyword)){
                sql.append(" and (");
                sql.append(" bkCode like concat('%',?,'%')");params.add(keyword);
                sql.append(" or bkName like concat('%',?,'%')");params.add(keyword);
                sql.append(" or bkISBN like concat('%',?,'%')");params.add(keyword);
                sql.append(" or bkCatalog like concat('%',?,'%')");params.add(keyword);
                sql.append(")");
            }
        }

        //查询记录总数
        response.setTotal(JdbcUtils.count("select count(*) "+sql,params.toArray()));
        if(response.getTotal()>0) {
            int start = (request.getPage() - 1) * request.getSize();
            sql.append(" order by bkID desc ");
            sql.append("limit ").append(start).append(",").append(request.getSize());
            List<Book> list=JdbcUtils.list("select * " + sql, new BookConverter(), params.toArray());
            response.setList(list);
        }

        return response;
    }

    /**
     * 查询单个
     * @param bkID
     * @return
     */
    public Book load(Integer bkID){
        return JdbcUtils.find("select * from tb_book where bkID=?", new BookConverter(), bkID);
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    public int insert(Book book){
        String sql = "INSERT INTO tb_book ( bkCode, bkName, bkAuthor, bkPress, bkDatePress, bkISBN, bkCatalog, bkLanguage, bkPages, bkPrice, bkDateIn, bkBrief, bkCover, bkStatus) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return JdbcUtils.exec(sql, book.getBkCode(), book.getBkName(), book.getBkAuthor(), book.getBkPress(), book.getBkDatePress(), book.getBkISBN(), book.getBkCatalog(), book.getBkLanguage(), book.getBkPages(), book.getBkPrice(), book.getBkDateIn(), book.getBkBrief(), book.getBkCover(), book.getBkStatus());
    }

    /**
     * 更新数据
     * @param book
     * @return
     */
    public int update(Book book){
        String sql = "update tb_book set bkCode=?, bkName=?, bkAuthor=?, bkPress=?, bkDatePress=?, bkISBN=?, bkCatalog=?, bkLanguage=?, bkPages=?, bkPrice=?, bkDateIn=?, bkBrief=?, bkCover=?, bkStatus=? where bkID=?";
        return JdbcUtils.exec(sql, book.getBkCode(), book.getBkName(), book.getBkAuthor(), book.getBkPress(), book.getBkDatePress(), book.getBkISBN(), book.getBkCatalog(), book.getBkLanguage(), book.getBkPages(), book.getBkPrice(), book.getBkDateIn(), book.getBkBrief(), book.getBkCover(), book.getBkStatus(), book.getBkID());
    }

    /**
     * 删除
     * @param bkID
     * @return
     */
    public int delete(Integer bkID){
        return JdbcUtils.exec("delete from tb_book where bkID=?",bkID);
    }

    /**
     * 查询数字最大的图书号
     * @return
     */
    public String queryMaxBkCode() {
        String sql="select bkCode from tb_book order by bkCode desc limit 1";
        Map<String,Object> ret=JdbcUtils.find(sql);
        if(ret==null) return null;
        return ret.get("bkCode").toString();
    }

    /**
     * 根据图书号查询
     * @param bkCode
     * @return
     */
    public Book queryByBkCode(String bkCode) {
        String sql="select * from tb_book where bkCode=?";
        return JdbcUtils.find(sql,new BookConverter(),bkCode);
    }

    /**
     * 数据库映射
     */
    public static class BookConverter implements JdbcUtils.Converter<Book>{

        @Override
        public Book convert(ResultSet rs) throws SQLException {
            Book book = new Book();
            book.setBkID(rs.getInt("bkID"));
            book.setBkCode(rs.getString("bkCode"));
            book.setBkName(rs.getString("bkName"));
            book.setBkAuthor(rs.getString("bkAuthor"));
            book.setBkPress(rs.getString("bkPress"));
            book.setBkDatePress(rs.getDate("bkDatePress"));
            book.setBkISBN(rs.getString("bkISBN"));
            book.setBkCatalog(rs.getString("bkCatalog"));
            book.setBkLanguage(rs.getInt("bkLanguage"));
            book.setBkPages(rs.getInt("bkPages"));
            book.setBkPrice(rs.getDouble("bkPrice"));
            book.setBkDateIn(rs.getDate("bkDateIn"));
            book.setBkBrief(rs.getString("bkBrief"));
            book.setBkCover(getBytes(rs,"bkCover"));
            book.setBkStatus(rs.getString("bkStatus"));
            return book;
        }
    }

}
