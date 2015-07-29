/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.servlet;

import com.ic.service.AlterDataBase;
import com.ic.service.ModifyRecord;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(urlPatterns = {"/Alter.do"},
        initParams = {
            @WebInitParam(name = "USER_VIEW", value = "index2.jsp")}
)

public class AlterData extends HttpServlet {

    private String USER_VIEW;

    @Override
    public void init() throws ServletException {
        USER_VIEW = getServletConfig().getInitParameter("USER_VIEW");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(AlterData.class);
        HttpSession session = req.getSession(true);
        int user_id = -1;
        try {
            if (session.getAttribute("id") != null) {
                user_id = (int) session.getAttribute("id");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = req.getRemoteAddr();
        }

        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        int q_no = -1;
        String type_no = req.getParameter("type_no");
        if (req.getParameter("q_id") != null) {
            q_no = Integer.parseInt(req.getParameter("q_id"));
        }
        AlterDataBase adb = new AlterDataBase();
        ModifyRecord mr = new ModifyRecord();

        String alt_cata = req.getParameter("type_list");
        String alt_que = req.getParameter("que_list");
        String[] alt_ans = req.getParameterValues("ans_list");
        String[] alt_ans_id = req.getParameterValues("ans_id");
        String[] alt_iso = req.getParameterValues("iso_list");
        String[] alt_iso_id = req.getParameterValues("iso_id");

        if (alt_cata != null) {
            mr.appendMessage(user_id, "更新", " 類別", adb.updateCatagorize(alt_cata, Integer.parseInt(type_no)), 1, ipAddress);
        }

        if (alt_que != null) {
            mr.appendMessage(user_id, "更新", " 問題", adb.updateQuestion(alt_que, q_no), 1, ipAddress);
        }

        if (alt_ans != null && alt_ans_id != null) {
            mr.appendMessage(user_id, "更新", " 解答", adb.updateAnswer(alt_ans, alt_ans_id), alt_ans.length, ipAddress);
        }

        if (alt_iso != null && alt_iso_id != null) {
            mr.appendMessage(user_id, "更新", " iso", adb.updateIso(alt_iso, alt_iso_id), alt_iso.length, ipAddress);
        }
        //當sb的內容有東西印出來，沒有則執行else
        if (mr.getSuccess().length() > 0) {
            logger.info(user_id + ": Alter some data.");
            mr.record_modify();
            out.print("<script>alert('" + mr.getSuccess() + "');</script>");
            mr.setSuccess(null);
            mr.setModifylist(null);
            //modifylist.clear();
        } else {
            out.print("<script>alert('無資料被修改');</script>");
        }
        //將修改紀錄成功的list記錄到資料庫
        res.addHeader("refresh", "0;URL=Alter.do?typelist=" + type_no);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String user = (String) session.getAttribute("name");
        if (user == null && !"root".equals(user)) {
            req.getRequestDispatcher("").forward(req, resp);
        } else {
            req.getRequestDispatcher(USER_VIEW).forward(req, resp);
        }
    }
}
