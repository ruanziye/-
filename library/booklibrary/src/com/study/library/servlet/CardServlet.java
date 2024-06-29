package com.study.library.servlet;

import com.study.library.model.Reader;
import com.study.library.model.ReaderType;
import com.study.library.service.ReaderService;
import com.study.library.service.ReaderTypeService;
import com.study.library.util.HttpUtils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * 借书证
 */
@WebServlet("/card")
@MultipartConfig
public class CardServlet extends BaseServlet {

    /**
     * 创建借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader searchBean = HttpUtils.getBean(req, Reader.class);
        boolean exist = ReaderService.existReader(searchBean);
        req.setAttribute("result", exist ? "1" : "0");
        req.getRequestDispatcher("/card/create_card.jsp").forward(req, resp);
    }

    /**
     * 保存借书证
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader reader = HttpUtils.getBean(req, Reader.class);
        Part part = req.getPart("rdPhoto");
        if (part != null) {
            InputStream in = new BufferedInputStream(part.getInputStream());
            byte[] filedata = new byte[(int) part.getSize()];
            in.read(filedata, 0, (int) part.getSize());
            in.close();
            reader.setRdPhoto(filedata);
        }
        ReaderService.createReader(reader);
        HttpUtils.redirectWithAlert(req, resp, "/card/create_card.jsp", "办理成功");
    }

    /**
     * 变更借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeChange(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        Reader reader = ReaderService.findReader(intRdID);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
        }
        req.getRequestDispatcher("/card/change_card.jsp").forward(req, resp);
    }

    /**
     * 变更借书证
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void change(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader reader = HttpUtils.getBean(req, Reader.class);
        Part part = req.getPart("rdPhoto");
        if (part != null) {
            InputStream in = new BufferedInputStream(part.getInputStream());
            byte[] filedata = new byte[(int) part.getSize()];
            in.read(filedata, 0, (int) part.getSize());
            in.close();
            reader.setRdPhoto(filedata);
        }
        ReaderService.changeReader(reader);
        HttpUtils.redirectWithAlert(req, resp, "/card/change_card.jsp", "变更成功");
    }

    /**
     * 挂失借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeLoss(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader searchBean = HttpUtils.getBean(req, Reader.class);
        Reader reader = ReaderService.findReader(searchBean);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
        }
        req.getRequestDispatcher("/card/loss_card.jsp").forward(req, resp);
    }

    /**
     * 挂失借书证
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void loss(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        ReaderService.lossReader(intRdID);
        HttpUtils.redirectWithAlert(req, resp, "/card/loss_card.jsp", "挂失成功");
    }

    /**
     * 解除挂失借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeRestore(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader searchBean = HttpUtils.getBean(req, Reader.class);
        Reader reader = ReaderService.findReader(searchBean);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
        }
        req.getRequestDispatcher("/card/restore_card.jsp").forward(req, resp);
    }

    /**
     * 解除挂失借书证
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void restore(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        ReaderService.restoreReader(intRdID);
        HttpUtils.redirectWithAlert(req, resp, "/card/restore_card.jsp", "解除挂失成功");
    }

    /**
     * 注销借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeCancel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Reader searchBean = HttpUtils.getBean(req, Reader.class);
        Reader reader = ReaderService.findReader(searchBean);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
        }
        req.getRequestDispatcher("/card/cancel_card.jsp").forward(req, resp);
    }

    /**
     * 注销借书证
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void cancel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        ReaderService.cancelReader(intRdID);
        HttpUtils.redirectWithAlert(req, resp, "/card/cancel_card.jsp", "注销成功");
    }

    /**
     * 添加读者类别
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void createReaderType(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ReaderType bean = HttpUtils.getBean(req, ReaderType.class);
        String msg = ReaderTypeService.createReaderType(bean);
        if (msg != null) {
            HttpUtils.forwardWithAlert(req, resp, "/card/type_list_add.jsp", msg);
        } else
            HttpUtils.redirectWithAlert(req, resp, "/card/type_list_add.jsp", "添加成功");
    }

    /**
     * 更新读者类别
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void updateReaderType(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ReaderType bean = HttpUtils.getBean(req, ReaderType.class);
        String msg = ReaderTypeService.updateReaderType(bean);
        if (msg != null) {
            HttpUtils.forwardWithAlert(req, resp, "/card/type_list_update.jsp", msg);
        } else
            HttpUtils.redirectWithAlert(req, resp, "/card/type_list.jsp", "更新成功");
    }

    /**
     * 删除读者类别
     * @param req
     * @param resp
     * @throws Exception
     */
    public void deleteReaderType(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        Integer intRDType = Integer.parseInt(req.getParameter("rdType"));
        String msg=null;
        try {
            ReaderTypeService.deleteReaderType(intRDType);
            msg="删除成功";
        }catch(Exception ex){
            msg="删除失败（该类别下已经存在关联的读者/借书证信息）";
        }
        HttpUtils.redirectWithAlert(req, resp, "/card/type_list.jsp", msg);
    }

    /**
     * 补办借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforeReapply(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        Reader reader = ReaderService.findReader(intRdID);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
        }
        req.getRequestDispatcher("/card/reapply_card.jsp").forward(req, resp);
    }

    /**
     * 补办借书证
     * @param req
     * @param resp
     */
    public void reapply(HttpServletRequest req,HttpServletResponse resp){
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        String msg=ReaderService.reapplyReader(intRdID);
        if(msg==null) msg="补办成功";
        HttpUtils.redirectWithAlert(req,resp,"/card/reapply_card.jsp",msg);
    }

    /**
     * 打印借书证前先执行查询
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void searchBeforePrint(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer intRdID = Integer.parseInt(req.getParameter("rdID"));
        Reader reader = ReaderService.findReader(intRdID);
        if (reader == null)
            req.setAttribute("result", "1");
        else {
            req.setAttribute("result", "0");
            req.setAttribute("reader", reader);
            req.setAttribute("readertype",ReaderTypeService.find(reader.getRdType()));
        }
        req.getRequestDispatcher("/card/print_card.jsp").forward(req, resp);
    }
}
