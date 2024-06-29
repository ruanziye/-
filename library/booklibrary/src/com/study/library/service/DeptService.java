package com.study.library.service;

import com.study.library.dao.DeptDao;
import com.study.library.model.Dept;

import java.util.List;

/**
 * 单位管理
 */
public class DeptService {
    private static DeptDao deptDao=new DeptDao();

    /**
     * 获取单位列表
     * @return
     */
    public static List<Dept> list(){
        return deptDao.list();
    }
}
