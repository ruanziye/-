package com.study.library.service;

import com.study.library.dao.BorrowDao;
import com.study.library.dao.ReaderDao;
import com.study.library.model.Reader;
import com.study.library.util.DateUtils;
import com.study.library.util.HttpUtils;

import java.util.Date;

/**
 * 读者管理
 */
public class ReaderService {

    private static ReaderDao readerDao=new ReaderDao();
    private static BorrowDao borrowDao=new BorrowDao();

    /**
     * 根据读者类别、单位、姓名判断是否存在
     * @param searchBean
     * @return
     */
    public static boolean existReader(Reader searchBean) {
        return readerDao.countByTypeAndDeptAndName(searchBean)>0;
    }

    /**
     * 办理借书证
     * @param reader
     */
    public static void createReader(Reader reader) {
        reader.setRdID((int)new Date().getTime());
        reader.setRdDateReg(new Date());
        reader.setRdStatus("有效");
        reader.setRdBorrowQty(0);
        reader.setRdPwd(HttpUtils.md5("123"));
        reader.setRdAdminRoles(0);
        readerDao.insert(reader);
    }

    /**
     * 根据借书证号查询读者信息
     * @param intRdID
     * @return
     */
    public static Reader findReader(Integer intRdID) {
        return readerDao.load(intRdID);
    }

    /**
     * 根据借书证号 或 读者类别/单位/姓名 进行查询读者信息
     * @param searchBean
     * @return
     */
    public static Reader findReader(Reader searchBean){
        if(searchBean.getRdID()!=null)
            return readerDao.load(searchBean.getRdID());
        else
            return readerDao.loadByTypeAndDeptAndName(searchBean);
    }

    /**
     * 更新读者信息
     * @param reader
     */
    public static void changeReader(Reader reader) {
        Reader dbReader=readerDao.load(reader.getRdID());
        dbReader.setRdName(reader.getRdName());
        dbReader.setRdSex(reader.getRdSex());
        dbReader.setRdType(reader.getRdType());
        dbReader.setRdDept(reader.getRdDept());
        dbReader.setRdEmail(reader.getRdEmail());
        dbReader.setRdPhoto(reader.getRdPhoto());
        dbReader.setRdPhone(reader.getRdPhone());
        readerDao.update(dbReader);
    }

    /**
     * 挂失借书证
     * @param intRdID
     */
    public static void lossReader(Integer intRdID) {
        Reader dbReader=readerDao.load(intRdID);
        if(!dbReader.getRdStatus().equals("有效")) return;
        dbReader.setRdStatus("挂失");
        readerDao.update(dbReader);
    }

    /**
     * 解除挂失
     * @param intRdID
     */
    public static void restoreReader(Integer intRdID) {
        Reader dbReader=readerDao.load(intRdID);
        if(!dbReader.getRdStatus().equals("挂失")) return;
        dbReader.setRdStatus("有效");
        readerDao.update(dbReader);
    }

    /**
     * 注销借书证
     * @param intRdID
     */
    public static void cancelReader(Integer intRdID) {
        Reader dbReader=readerDao.load(intRdID);
        if(!dbReader.getRdStatus().equals("注销")) return;
        dbReader.setRdStatus("注销");
        readerDao.update(dbReader);
    }

    /**
     * 补办借书证
     * @param intRdID
     * @return
     */
    public static String reapplyReader(Integer intRdID) {
        Reader reader=readerDao.load(intRdID);
        if(reader==null) return "借书证不存在";
        if(reader.getRdStatus().equals("注销"))
            return "借书证已注销";

        //原借书证注销
        reader.setRdStatus("注销");
        readerDao.update(reader);

        //生成新的借书证
        Integer newRdID=(int)new Date().getTime();
        reader.setRdID(newRdID);
        reader.setRdStatus("有效");
        readerDao.insert(reader);

        //更改借阅记录关联的借书证号
        borrowDao.updateReader(intRdID,newRdID);
        return "补办成功，新的借书证号为"+newRdID;
    }
}
