/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.servlet;

import com.ic.bean.IdentitBean;
import com.ic.entity.Identit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
@WebServlet(urlPatterns = {"/AlterMember.do"},
        initParams = {
            @WebInitParam(name = "USER_VIEW", value = "list_user.jsp")}
)

public class AlterMember extends HttpServlet {

    private String USER_VIEW;

    @Override
    public void init() throws ServletException {
        USER_VIEW = getServletConfig().getInitParameter("USER_VIEW");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(AlterMember.class);
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String[] user_id = req.getParameterValues("user_id");
        String[] user_name = req.getParameterValues("user_name");
        String job_number = req.getParameter("job_number");
        String[] password = req.getParameterValues("user_psw");
        String[] user_email = req.getParameterValues("user_email");
        String delete_id = req.getParameter("delete");

        String new_uid = req.getParameter("new_user_name");
        String new_uacc = req.getParameter("new_user_account");
        String new_upsw = req.getParameter("new_user_password");
        String new_uemail = req.getParameter("new_user_email");

        IdentitBean iBean = new IdentitBean();

        String psw = req.getParameter("psw");
        String old_psw = req.getParameter("old_psw");

        String test1 = "";
        String test2 = "";
        
        boolean flag = false;

        if (user_id != null && user_name != null && user_email != null) {
            List identitlist = new ArrayList();
            Identit identit = new Identit();
            identit.setId(Integer.parseInt(user_id[0]));
            identit.setName(user_name[0]);
            identit.setJobnumber(job_number);
            if (password != null) {
                identit.setPassword(password[0]);
            } else {
                identit.setPassword("");
            }
            identit.setEmail(user_email[0]);
            identitlist.add(identit);
            if (iBean.alterUserInfo(identitlist)) {
                logger.info("root Alter some UserInfo");
                out.print("<script>alert('修改成功');</script>");
                flag = true;
            } else {
                out.print("<script>alert('修改失敗');</script>");
            }
        }
        if (new_uid != null && !new_uid.trim().equals("")) {
            String[] s = new String[1];
            s[0] = new_uacc;
            if (checkUser(s)) {
                Identit identit = new Identit();
                List identitlist = new ArrayList();
                identit.setJobnumber(new_uacc);
                identit.setName(new_uid);
                identit.setPassword(new_upsw);
                identit.setEmail(new_uemail);
                identitlist.add(identit);

                if (iBean.new_user(identitlist)) {
                    logger.info("root Insert some UserInfo");
                    out.print("<script>alert('新增成功');</script>");
                    flag = true;
                } else {
                    out.print("<script>alert('新增失敗');</script>");
                }
            } else {
                out.print("<script>alert('新增失敗，已經有相同的使用者');</script>");
            }
        }
        if (delete_id != null) {
            if (iBean.deleteIdentit(Integer.parseInt(delete_id))) {
                logger.info("root Delete some UserInfo");
                out.print("使用者 " + delete_id + " 已經被刪除");
                out.print("<script>alert('刪除成功');</script>");
                flag = true;
            } else {
                out.print("<script>alert('刪除失敗');</script>");
            }
        }
        if (psw != null && old_psw != null) {
            List l = iBean.getAllUsers();
            Iterator it = l.iterator();
            boolean b = false;
            while (it.hasNext()) {
                Identit i = (Identit) it.next();
                if (i.getPassword().equals(old_psw)) {
                    logger.info(i.getJobnumber() + " changed the password itself.");
                    b = iBean.alterPassword(i.getJobnumber(), psw.trim(), i.getId());
                    test1 = i.getJobnumber() + " " + psw.trim() + " " + i.getId();
                    test2 = old_psw;
//                    Cookie cookie[] = req.getCookies();
//                    for (Cookie c : cookie) {
//                        if (c.getValue().equals(old_psw)) {
//                            c.setMaxAge(0);
//                            res.addCookie(c);
//                        }
//                    }
                    break;
                }
            }
            if (b) {
                out.print("<script>alert('密碼修改成功');</script>");
//                out.print("<script>alert('訊息: " + test1 + "\\n" + test2 + "');</script>");
                flag = true;
            } else {
                out.print("<script>alert('找不到此使用者');</script>");
            }
        }
        if (!flag) {
            out.print("<script>alert('無資料更動');</script>");
        }
        res.addHeader("refresh", "0;URL=AlterMember.do");
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

    public boolean checkUser(String[] user_jobnumber) {
        boolean b = true;
        IdentitBean iBean = new IdentitBean();
        List l = iBean.getAllUsers();
        Iterator it = l.iterator();
        for (String s : user_jobnumber) {
            while (it.hasNext()) {
                Identit i = (Identit) it.next();
                if (i.getJobnumber().equals(s)) {
                    b = false;
                    break;
                }
            }
        }
        return b;
    }
}
