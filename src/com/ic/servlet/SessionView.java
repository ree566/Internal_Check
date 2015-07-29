/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.servlet;

import com.ic.bean.IdentitBean;
import com.ic.entity.Identit;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wei.Cheng
 */

@WebServlet(urlPatterns = {"/SessionView.do"})

public class SessionView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        String id = req.getParameter("id");
        if (id == null) {
            res.sendRedirect("");
        } else {
            Cookie cookie[] = req.getCookies();
            for (Cookie c : cookie) {
                if (c.getName().equals(id)) {
                    IdentitBean iBean = new IdentitBean();
                    Identit i;
                    List l = iBean.getAllUsers();
                    Iterator it = l.iterator();
                    while (it.hasNext()) {
                        i = (Identit) it.next();
                        if (i.getPassword().trim().equals(c.getValue())) {
                            req.setAttribute("alter_psw", c.getValue());
                            req.getRequestDispatcher("alterpassword.jsp").forward(req, res);
                            break;
                        }
                    }
                }
            }
            res.getWriter().print("您的密碼更改郵件已經逾期，請重新操作。");
            res.getWriter().print("<a href='Mail.do'>回密碼更改頁</a>");
        }
    }
}
