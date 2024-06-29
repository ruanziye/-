package com.study.library.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.study.library.dao.BookDao;
import com.study.library.dao.BorrowDao;
import com.study.library.dao.ReaderDao;
import com.study.library.dao.ReaderTypeDao;
import com.study.library.dto.BookBorrow;
import com.study.library.model.Book;
import com.study.library.model.Borrow;
import com.study.library.model.Reader;
import com.study.library.model.ReaderType;
import com.study.library.util.JdbcUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借阅管理 -- 业务
 */
public class BorrowService {
    private static BookDao bookDao=new BookDao();
    private static BorrowDao borrowDao=new BorrowDao();
    private static ReaderDao readerDao=new ReaderDao();
    private static ReaderTypeDao readerTypeDao=new ReaderTypeDao();

    /**
     * 查询读者关联的未归还的借阅记录信息
     * @param rdID
     * @return
     */
    public static List<BookBorrow> listNotReturned(Integer rdID) {
        List<Borrow> borrows=borrowDao.listNotReturned(rdID);
        List<BookBorrow> retlist=new ArrayList<>();
        for(Borrow borrow :borrows){
            BookBorrow bookBorrow=new BookBorrow();
            bookBorrow.setBorrow(borrow);
            bookBorrow.setBook(bookDao.load(borrow.getBkID()));
            retlist.add(bookBorrow);
        }
        return retlist;
    }

    /**
     * 借阅图书
     * @param bkCode
     * @param rdID
     * @param borrowOperatorId
     * @return
     */
    public static String borrow(String bkCode, String rdID, Integer borrowOperatorId) {
        Reader reader=ReaderService.findReader(Integer.valueOf(rdID));
        Book book=BookService.findByBkCode(bkCode);
        if(!reader.getRdStatus().equals("有效")){
            return "读者状态为"+reader.getRdStatus()+"，不能借阅";
        }
        Integer validYears=readerTypeDao.load(reader.getRdType()).getDateValid();
        if(validYears>0) {
            Date expireDate = DateUtil.offset(reader.getRdDateReg(), DateField.YEAR, validYears);
            expireDate=DateUtil.truncate(expireDate,DateField.DAY_OF_MONTH);
            Date now=DateUtil.truncate(new Date(),DateField.DAY_OF_MONTH);
            if(now.compareTo(expireDate)>0)
                return "借书证已过期，不能借阅";
        }
        if(reader.getRdBorrowQty()>= ReaderTypeService.find(reader.getRdType()).getCanLendQty()){
            return "读者"+reader.getRdName()+"借阅数量已达上限";
        }
        if(!book.getBkStatus().equals("在馆"))
            return "图书状态为"+book.getBkStatus()+"，不能借阅";
        List<BookBorrow> borrowList= BorrowService.listNotReturned(reader.getRdID());
        boolean isExpireFound=false;
        Date now= DateUtil.truncate(new Date(), DateField.DAY_OF_MONTH);
        for(BookBorrow bb : borrowList){
            Date returnDate= DateUtil.truncate(bb.getBorrow().getLdDateRetPlan(),DateField.DAY_OF_MONTH);
            if(DateUtil.compare(returnDate,now)<0){
                isExpireFound=true;
                break;
            }
        }
        if(isExpireFound)
            return "存在超期未归还的图书，不能借阅";

        Borrow borrow=new Borrow();
        borrow.setRdID(reader.getRdID());
        borrow.setBkID(book.getBkID());
        borrow.setLdContinueTimes(0);
        borrow.setLdDateOut(new Date());
        Date dateRePlan=DateUtil.offset(borrow.getLdDateOut(),DateField.DAY_OF_MONTH, ReaderTypeService.find(reader.getRdType()).getCanLendDay());
        borrow.setLdDateRetPlan(dateRePlan);
        borrow.setLdDateRetAct(null);
        borrow.setLdOverDay(0);
        borrow.setLdOverMoney(0.00);
        borrow.setLdPunishMoney(0.00);
        borrow.setLsHasReturn(false);
        borrow.setOperatorBorrow(borrowOperatorId+"");
        borrow.setOperatorReturn(null);
        borrowDao.insert(borrow);

        //更新读者已借阅数量
        reader.setRdBorrowQty(reader.getRdBorrowQty()+1);
        readerDao.update(reader);

        //更新图书为借出状态
        book.setBkStatus("借出");
        bookDao.update(book);

        return null;
    }

    /**
     * 借阅是否超期
     * @param borrow
     * @return
     */
    public static boolean isOverReturn(Borrow borrow){
        if(borrow.getLsHasReturn().booleanValue())
            return false;
        Date ldDateRetPlan= DateUtil.truncate(borrow.getLdDateRetPlan(), DateField.DAY_OF_MONTH);
        Date now=DateUtil.truncate(new Date(),DateField.DAY_OF_MONTH);
        if(DateUtil.compare(ldDateRetPlan,now)<0)
            return true;
        else
            return false;
    }

    /**
     * 计算超期天数，如果没有超期，则返回0
     * @param borrow
     * @return
     */
    public static int getOverReturnDays(Borrow borrow){
        Date now=DateUtil.truncate(new Date(),DateField.DAY_OF_MONTH);
        Date returnDate=DateUtil.truncate(borrow.getLdDateRetPlan(),DateField.DAY_OF_MONTH);
        int days=(int)DateUtil.betweenDay(returnDate,now,false);
        if(days<=0) return 0;
        return days;
    }

    /**
     * 计算应罚款金额
     * @param borrow
     * @param type 1 超期归还 ，2遗失归还
     * @return
     */
    public static double getShouldPunishAmount(Borrow borrow,int type){
        if(type==1){
            int days=getOverReturnDays(borrow);
            if(days==0) return 0.00;
            Reader reader=ReaderService.findReader(borrow.getRdID());
            ReaderType readerType=ReaderTypeService.find(reader.getRdType());
            return days*readerType.getPunishRate();
        }else if(type==2){
            Book book=BookService.find(borrow.getBkID());
            return book.getBkPrice()*3;
        }
        throw new IllegalArgumentException("归还类型不正确");
    }

    /**
     * 根据图书书号，查询借阅记录、图书信息
     * @param bkCode
     * @return
     */
    public static BookBorrow findByBkCode(String bkCode) {
        BookBorrow bookBorrow=new BookBorrow();
        bookBorrow.setBook(bookDao.queryByBkCode(bkCode));
        if(bookBorrow.getBook()==null)
            return bookBorrow; //图书信息不存在，直接返回
        bookBorrow.setBorrow(borrowDao.queryByBkCode(bkCode));
        return bookBorrow;
    }

    /**
     * 续借
     * @param borrowID
     * @return
     */
    public static String continueBorrow(Integer borrowID, Integer borrowOperatorId) {
        Borrow borrow=borrowDao.load(borrowID);
        Reader reader=readerDao.load(borrow.getRdID());
        if(!reader.getRdStatus().equals("有效")){
            return "借书证状态为"+reader.getRdStatus()+"，不能续借";
        }
        ReaderType readerType=readerTypeDao.load(reader.getRdType());
        Integer validYears=readerType.getDateValid();
        if(validYears>0) {
            Date expireDate = DateUtil.offset(reader.getRdDateReg(), DateField.YEAR, validYears);
            expireDate=DateUtil.truncate(expireDate,DateField.DAY_OF_MONTH);
            Date now=DateUtil.truncate(new Date(),DateField.DAY_OF_MONTH);
            if(now.compareTo(expireDate)>0)
                return "借书证已过期，不能续借";
        }
        if(borrow.getLdContinueTimes()>readerType.getCanLendQty())
            return "该图书续借次数已达上限，不能续借";
        if(isOverReturn(borrow))
            return "该图书超期未归还，不能续借";

        borrow.setLdContinueTimes(borrow.getLdContinueTimes()+1);
        Date dateRePlan=DateUtil.offset(borrow.getLdDateRetPlan(),DateField.DAY_OF_MONTH, readerType.getCanLendDay());
        borrow.setLdDateRetPlan(dateRePlan);
        borrow.setOperatorBorrow(borrowOperatorId.toString());
        borrowDao.update(borrow);

        return null;
    }

    /**
     * 还书
     * @param borrowID 借阅id
     * @param returnType （1正常或超期归还，2遗失归还）
     * @param punishPrice 罚款金额
     * @param loginOperatorId  当前用户id
     * @return
     */
    public static String returnBook(Integer borrowID, Integer returnType, Double punishPrice, Integer loginOperatorId) {
        //判断借阅状态
        Borrow borrow=borrowDao.load(borrowID);
        if(borrow==null) return "借阅记录不存在";
        if(borrow.getLsHasReturn().booleanValue()) return "该图书已经归还";
        int days=getOverReturnDays(borrow);//获取超期天数
        Double sholdPunishPrice=getShouldPunishAmount(borrow,returnType);//获取应付款金额
        if(returnType.equals(1)){
            //正常归还或超期归还
            if(days>0){
                if(punishPrice==null)
                    return "该图书已超期，需要缴纳罚款金额";
                if(punishPrice>sholdPunishPrice)
                    return "实际罚款金额不能超过应罚款金额";
                else if(punishPrice<0)
                    return "实际罚款金额大于0";
            }
        }else if(returnType.equals(2)){
            //遗失归还
            if(punishPrice==null)
                return "遗失图书，需要缴纳罚款金额";
            if(punishPrice>sholdPunishPrice)
                return "实际罚款金额不能超过应罚款金额";
            else if(punishPrice<0)
                return "实际罚款金额大于0";
        }else
            throw new IllegalArgumentException("归还类型错误");

        //开始更新借阅记录
        borrow.setLdDateRetAct(new Date()); //设置实际归还日期
        borrow.setLdOverDay(days);//超期天数
        borrow.setLdOverMoney(sholdPunishPrice);//应罚款金额
        borrow.setLdPunishMoney(punishPrice==null?0.00:punishPrice);
        borrow.setLsHasReturn(true);
        borrow.setOperatorReturn(loginOperatorId.toString());
        borrowDao.update(borrow);

        //更新读者已借阅数量 ，减1
        Reader reader=readerDao.load(borrow.getRdID());
        reader.setRdBorrowQty(reader.getRdBorrowQty()-1);
        readerDao.update(reader);

        //更新图书状态
        Book book=bookDao.load(borrow.getBkID());
        if(returnType.equals(1))
            book.setBkStatus("在馆");
        else if(returnType.equals(2))
            book.setBkStatus("遗失");
        bookDao.update(book);
        return null;
    }
}
