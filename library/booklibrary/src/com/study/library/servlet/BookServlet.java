package com.study.library.servlet;

import com.study.library.model.Book;
import com.study.library.service.BookService;
import com.study.library.util.HttpUtils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.InputStream;

@WebServlet("/book")
@MultipartConfig
public class BookServlet extends BaseServlet{

    /**
     * 新书入库
     * @param req
     * @param resp
     * @throws Exception
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Book book= HttpUtils.getBean(req,Book.class);
        Part part=req.getPart("bkCover");
        if(part!=null){
            InputStream in = new BufferedInputStream(part.getInputStream());
            byte[] filedata = new byte[(int) part.getSize()];
            in.read(filedata, 0, (int) part.getSize());
            in.close();
            book.setBkCover(filedata);
        }
        String msg=BookService.create(book);
        if(msg!=null)
            HttpUtils.forwardWithAlert(req,resp,"/book/book_create.jsp",msg);
        else
            HttpUtils.redirectWithAlert(req,resp,"/book/book_create.jsp","入库成功");
    }

    /**
     * 修改图书信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Book book= HttpUtils.getBean(req,Book.class);
        Part part=req.getPart("bkCover");
        if(part!=null){
            InputStream in = new BufferedInputStream(part.getInputStream());
            byte[] filedata = new byte[(int) part.getSize()];
            in.read(filedata, 0, (int) part.getSize());
            in.close();
            book.setBkCover(filedata);
        }
        String msg=BookService.update(book);
        if(msg!=null)
            HttpUtils.forwardWithAlert(req,resp,"/book/book_update.jsp",msg);
        else
            HttpUtils.redirectWithAlert(req,resp,"/book/book_list.jsp","图书修改成功");
    }

    /**
     * 删除图书信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void delete(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        Integer bkID=Integer.valueOf(req.getParameter("bkID"));
        try{
            BookService.delete(bkID);
            HttpUtils.redirectWithAlert(req,resp,"/book/book_list.jsp","图书删除成功");
        }catch(Exception ex){
            HttpUtils.redirectWithAlert(req,resp,"/book/book_list.jsp","图书删除失败（存在关联数据）");
        }
    }
}
