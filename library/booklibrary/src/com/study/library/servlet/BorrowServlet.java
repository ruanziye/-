package com.study.library.servlet;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.study.library.dto.BookBorrow;
import com.study.library.model.Book;
import com.study.library.model.Borrow;
import com.study.library.model.Reader;
import com.study.library.service.*;
import com.study.library.util.HttpUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 借阅管理 -- 视图层
 */
@WebServlet("/borrow")
public class BorrowServlet extends BaseServlet{

    //----------------借阅 开始

    /**
     * 根据借书证号查找读者
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchReader(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        Integer rdID=Integer.valueOf(req.getParameter("rdID"));
        Reader reader= ReaderService.findReader(rdID);
        if(reader==null) {
            HttpUtils.redirectWithAlert(req, resp, "/borrow/borrow.jsp", "根据借书证号" + rdID + "没有查询到读者信息");
            return;
        }
        List<BookBorrow> borrowList= BorrowService.listNotReturned(reader.getRdID());
        req.setAttribute("reader",reader);
        req.setAttribute("borrowList",borrowList);
        HttpUtils.forwardWithAlert(req,resp,"/borrow/borrow.jsp",null);
    }

    /**
     * 根据图书书号查找图书
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBook(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        Book book= BookService.findByBkCode(req.getParameter("bkCode"));
        if(book==null){
            HttpUtils.forwardWithAlert(req, resp, "/borrow/borrow.jsp", "根据图书号" + req.getParameter("bkCode") + "没有查询到图书信息");
            return;
        }
        req.setAttribute("book",book);
        HttpUtils.forwardWithAlert(req,resp,"/borrow/borrow.jsp",null);
    }

    /**
     * 执行借阅操作
     * @param req
     * @param resp
     * @throws Exception
     */
    public void borrow(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        String msg=BorrowService.borrow(req.getParameter("bkCode"),req.getParameter("rdID"), CommonService.getLoginUserFromSession(req).getId());
        if(msg!=null){
            HttpUtils.forwardWithAlert(req,resp,"/borrow/borrow.jsp",msg);
            return;
        }
        HttpUtils.redirectWithAlert(req,resp,"/borrow/borrow.jsp","借阅成功");
    }
    //----------------借阅 结束

    //---------------续借 开始
    /**
     * 根据图书书号，查询读者信息、图书信息、未归还的借阅记录
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBorrow(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String bkCode=req.getParameter("bkCode");
        BookBorrow bookBorrow=BorrowService.findByBkCode(bkCode);
        String msg=null;
        if(bookBorrow.getBook()==null)
            msg="根据图书书号"+bkCode+"查询不到图书信息，不能续借";
        else if(bookBorrow.getBorrow()==null)
            msg="该图书没有未归还的借阅记录，不能续借";
        if(msg==null){
            Reader reader=ReaderService.findReader(bookBorrow.getBorrow().getRdID());//查询读者信息
            req.setAttribute("bookBorrow",bookBorrow);
            req.setAttribute("reader",reader);
        }

        HttpUtils.forwardWithAlert(req,resp,"/borrow/continue_borrow.jsp",msg);
    }

    /**
     * 续借
     * @param req
     * @param resp
     * @throws Exception
     */
    public void continueBorrow(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        Integer borrowID=Integer.valueOf(req.getParameter("borrowID"));
        String msg=BorrowService.continueBorrow(borrowID,CommonService.getLoginUserFromSession(req).getId());
        if(msg!=null) {
            String bkCode=req.getParameter("bkCode");
            BookBorrow bookBorrow=BorrowService.findByBkCode(bkCode);
            Reader reader=ReaderService.findReader(bookBorrow.getBorrow().getRdID());//查询读者信息
            req.setAttribute("bookBorrow",bookBorrow);
            req.setAttribute("reader",reader);
            HttpUtils.forwardWithAlert(req, resp, "/borrow/continue_borrow.jsp", msg);
            return;
        }
        HttpUtils.forwardWithAlert(req,resp,"/borrow/continue_borrow.jsp","续借成功");
    }
    //---------------续借 结束

    //---------------还书 开始
    /**
     * 根据图书书号，查询读者信息、图书信息、未归还的借阅记录
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBorrowForReturn(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String bkCode=req.getParameter("bkCode");
        BookBorrow bookBorrow=BorrowService.findByBkCode(bkCode);
        String msg=null;
        if(bookBorrow.getBook()==null)
            msg="根据图书书号"+bkCode+"查询不到图书信息，不能还书";
        else if(bookBorrow.getBorrow()==null)
            msg="该图书没有未归还的借阅记录，不能还书";
        if(msg==null){
            Reader reader=ReaderService.findReader(bookBorrow.getBorrow().getRdID());//查询读者信息
            req.setAttribute("bookBorrow",bookBorrow);
            req.setAttribute("reader",reader);
        }

        HttpUtils.forwardWithAlert(req,resp,"/borrow/return.jsp",msg);
    }

    /**
     * 还书
     * @param req
     * @param resp
     * @throws Exception
     */
    public void returnBook(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        Integer borrowID=Integer.parseInt(req.getParameter("borrowID"));
        Integer returnType=Integer.parseInt(req.getParameter("returnType"));
        String strPunishPrice=req.getParameter("punishPrice");
        Double punishPrice=null;
        if(StrUtil.isNotEmpty(strPunishPrice)){
            punishPrice=Double.valueOf(strPunishPrice);
        }
        String msg=BorrowService.returnBook(borrowID,returnType,punishPrice,CommonService.getLoginUserFromSession(req).getId());
        if(msg!=null)
            HttpUtils.forwardWithAlert(req,resp,"/borrow/return.jsp",msg);
        else
            HttpUtils.redirectWithAlert(req,resp,"/borrow/return.jsp","还书成功");
    }
    //---------------还书 结束
}
