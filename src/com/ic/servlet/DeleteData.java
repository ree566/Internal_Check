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
@WebServlet(urlPatterns = {"/Delete.do"},
        initParams = {
            @WebInitParam(name = "USER_VIEW", value = "ddata.jsp")}
)
public class DeleteData extends HttpServlet {

    private String USER_VIEW;

    @Override
    public void init() throws ServletException {
        USER_VIEW = getServletConfig().getInitParameter("USER_VIEW");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(DeleteData.class);
        HttpSession session = req.getSession(true);
        int user_id = -1;
        try {
            if (session.getAttribute("id") != null) {
                user_id = (int) session.getAttribute("id");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        res.setContentType("text/html;charset=utf8");
        String selectedtype = req.getParameter("selectedtype");
        String types[] = req.getParameterValues("type");
        String[] qlist = req.getParameterValues("question");
        String[] anslist = req.getParameterValues("ans");
        String[] isolist = req.getParameterValues("iso");
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = req.getRemoteAddr();
        }

        AlterDataBase adb = new AlterDataBase();
        ModifyRecord mr = new ModifyRecord();
        PrintWriter out = res.getWriter();

        if (anslist != null) {
            mr.appendMessage(user_id, "刪除", " 解答", adb.deleteAnswer(anslist), anslist.length, ipAddress);
        }
        if (isolist != null) {
            mr.appendMessage(user_id, "刪除", " iso", adb.deleteIso(isolist), isolist.length, ipAddress);
        }
        if (qlist != null) {
            mr.appendMessage(user_id, "刪除", " 問題", adb.deleteQuestion(qlist), qlist.length, ipAddress);
        }
        if (types != null) {
            mr.appendMessage(user_id, "刪除", " 類別", adb.deleteCatagorized(types), types.length, ipAddress);
        }
        if (mr.getSuccess().length() > 0) {
            logger.info(user_id + ": Delete some data.");
            mr.record_modify();
            out.print("<script>alert('" + mr.getSuccess() + "');</script>");
        } else {
            out.print("<script>alert('無資料被刪除');</script>");
        }
        //req.getRequestDispatcher("ddata.jsp?typelist=" + selectedtype).forward(req, res);
        res.addHeader("refresh", "0;URL=Delete.do?typelist=" + selectedtype);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if (session.getAttribute("name") == null) {
            req.getRequestDispatcher("").forward(req, res);
        } else {
            req.getRequestDispatcher(USER_VIEW).forward(req, res);
        }
    }
}
