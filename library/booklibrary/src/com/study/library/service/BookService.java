package com.study.library.service;

import com.study.library.dao.BookDao;
import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.Book;

import java.util.HashMap;
import java.util.Map;

/**
 * 图书管理
 */
public class BookService {
    private static BookDao bookDao=new BookDao();
    private static long startBkCode=10000000000L;

    private static Map<String,String> bookLanguages;
    static{
        bookLanguages=new HashMap<>();
        bookLanguages.put("0", "中文");
        bookLanguages.put("1", "英文");
        bookLanguages.put("2", "日文");
        bookLanguages.put("3", "俄文");
        bookLanguages.put("4", "德文");
        bookLanguages.put("5", "法文");
    }

    /**
     * 获取图书语言文本
     * @param languageCode
     * @return
     */
    public static String getLanguage(Integer languageCode){
        return bookLanguages.get(languageCode.toString());
    }

    /**
     * 生成新的图书号
     * @return
     */
    public static long createBkCode(){
        String maxBkNo=bookDao.queryMaxBkCode();
        if(maxBkNo==null)
            return startBkCode;
        else
            return Long.parseLong(maxBkNo)+1;
    }

    /**
     * 新书入库
     * @param book
     * @return
     */
    public static String create(Book book) {
        Book dbBook=bookDao.queryByBkCode(book.getBkCode());
        if(dbBook!=null)
            return "相同的图书编号已经存在！";
        book.setBkStatus("在馆");
        bookDao.insert(book);
        return null;
    }

    /**
     * 分页查询图书列表
     * @param pageRequest
     * @return
     */
    public static PageResponse<Book> list(PageRequest pageRequest) {
        return bookDao.list(pageRequest);
    }

    /**
     * 查询图书详情
     * @param bkID
     * @return
     */
    public static Book find(Integer bkID) {
        return bookDao.load(bkID);
    }

    /**
     * 修改图书信息
     * @param book
     * @return
     */
    public static String update(Book book) {
        Book dbBook=bookDao.queryByBkCode(book.getBkCode());
        if(!dbBook.getBkID().equals(book.getBkID()))
            return "相同的图书编号已经存在！";
        book.setBkStatus(dbBook.getBkStatus());
        bookDao.update(book);
        return null;
    }

    /**
     * 删除图书信息
     * @param bkID
     */
    public static void delete(Integer bkID){
        bookDao.delete(bkID);
    }


    /**
     * 根据图书书号查找
     * @param bkCode
     * @return
     */
    public static Book findByBkCode(String bkCode) {
        return bookDao.queryByBkCode(bkCode);
    }


}
