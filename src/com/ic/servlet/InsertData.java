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
@WebServlet(urlPatterns = {"/Insert.do"},
        initParams = {
            @WebInitParam(name = "USER_VIEW", value = "insert.jsp")}
)
public class InsertData extends HttpServlet {

    private String USER_VIEW;

    @Override
    public void init() throws ServletException {
        USER_VIEW = getServletConfig().getInitParameter("USER_VIEW");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(InsertData.class);
        HttpSession session = req.getSession(true);
        int user_id = -1;
        try {
            if (session.getAttribute("id") != null) {
                user_id = (int) session.getAttribute("id");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = req.getRemoteAddr();
        }

        String[] new_ans = req.getParameterValues("new_ans");
        String[] new_iso = req.getParameterValues("new_iso");
        String selectedtype = req.getParameter("selectedtype");
        int q_id = -1;
        if (req.getParameter("q_id") != null) {
            q_id = Integer.parseInt(req.getParameter("q_id"));
        }
        AlterDataBase adb = new AlterDataBase();
        ModifyRecord mr = new ModifyRecord();

        if (new_ans != null) {
            mr.appendMessage(user_id, "新增", " 解答", adb.insertAnswer(new_ans, q_id), new_ans.length, ipAddress);
        }

        if (new_iso != null) {
            mr.appendMessage(user_id, "新增", " Iso", adb.insertIso(new_iso, q_id), new_iso.length, ipAddress);
        }
        if (mr.getSuccess().length() > 0) {
            logger.info(user_id + ": Insert some data.");
            mr.record_modify();
            out.print("<script>alert('" + mr.getSuccess() + "');</script>");
        } else {
            out.print("<script>alert('無資料被新增');</script>");
        }
        res.addHeader("refresh", "0;URL=Insert.do?typelist=" + selectedtype);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if(session.getAttribute("name") == null){
            req.getRequestDispatcher("").forward(req, res);
        }else{
            req.getRequestDispatcher(USER_VIEW).forward(req, res);
        }
    }
}
