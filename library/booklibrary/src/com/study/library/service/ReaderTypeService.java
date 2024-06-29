package com.study.library.service;

import cn.hutool.core.util.StrUtil;
import com.study.library.dao.ReaderTypeDao;
import com.study.library.dto.PageRequest;
import com.study.library.dto.PageResponse;
import com.study.library.model.ReaderType;

import java.util.List;

/**
 * 读者类别管理
 */
public class ReaderTypeService {
    private static ReaderTypeDao readerTypeDao=new ReaderTypeDao();

    /**
     * 获取读者类别列表
     * @return
     */
    public static List<ReaderType> list(String rdName){
        PageRequest req=new PageRequest();
        if(StrUtil.isNotEmpty(rdName))
            req.getFilter().put("rdName",rdName);
        req.setSize(9999999);
        PageResponse response= readerTypeDao.list(req);
        return response.getList();
    }

    public static List<ReaderType> list(){
        return list(null);
    }

    /**
     * 查找读者类别
     * @param rdtype
     * @return
     */
    public static ReaderType find(Integer rdtype){
        return readerTypeDao.load(rdtype);
    }

    /**
     * 添加读者类别
     * @param bean
     * @return
     */
    public static String createReaderType(ReaderType bean) {
        ReaderType dbitem=readerTypeDao.load(bean.getRdType());
        if(dbitem!=null)
            return "读者类别编号已存在";
        dbitem=readerTypeDao.loadByTypeName(bean.getRdTypeName());
        if(dbitem!=null)
            return "读者类别名称已存在";
        readerTypeDao.insert(bean);
        return null;
    }

    /**
     * 更新读者类别
     * @param bean
     * @return
     */
    public static String updateReaderType(ReaderType bean){
        ReaderType dbitem=readerTypeDao.loadByTypeName(bean.getRdTypeName());
        if(dbitem!=null && !dbitem.getRdType().equals(bean.getRdType()))
            return "读者类别名称已存在";
        readerTypeDao.update(bean);
        return null;
    }

    /**
     * 删除读者类别
     * @param rdtype
     */
    public static void deleteReaderType(Integer rdtype){
        readerTypeDao.delete(rdtype);
    }
}
