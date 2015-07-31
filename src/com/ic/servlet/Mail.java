/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.servlet;

import com.ic.bean.IdentitBean;
import com.ic.entity.Identit;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(
        urlPatterns = {"/Mail.do"},
        initParams = {
            @WebInitParam(name = "mailHost", value = "kevin@172.20.131.52"),
            @WebInitParam(name = "mailPort", value = "587"),
            @WebInitParam(name = "username", value = "kevin"),
            @WebInitParam(name = "password", value = "kevin")
        }
)
public class Mail extends HttpServlet {

    private String mailHost;
    private String mailPort;
    private String username;
    private String password;
    private Properties props;

    @Override
    public void init() throws ServletException {
        mailHost = getServletConfig().getInitParameter("mailHost");
        mailPort = getServletConfig().getInitParameter("mailPort");
        username = getServletConfig().getInitParameter("username");
        password = getServletConfig().getInitParameter("password");

        props = new Properties();
        props.setProperty("mail.smtp.host", mailHost);
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", mailPort);

//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", mailPort);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("forgotmail.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String from = "java.email.service@gmail.com";
        String to = req.getParameter("to");
        if (to == null) {
            req.getRequestDispatcher("forgotmail.jsp").forward(req, res);
        } else {
            String subject = "密碼更改信件";
            StringBuilder text = new StringBuilder();
            String ipAddress = req.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                ipAddress = req.getRemoteAddr();
            }
            boolean b = false;
            IdentitBean iBean = new IdentitBean();
            Identit i;
            List l = iBean.getAllUsers();
            Iterator it = l.iterator();
            while (it.hasNext()) {
                i = (Identit) it.next();
                if (i.getEmail().trim().equals(to.trim())) {
                    b = true;
                    String id = i.getPassword().trim();
                    StringBuffer sb = req.getRequestURL();
                    String st = generaterandomnumber();
                    Cookie cookies[] = req.getCookies();
                    for (Cookie c : cookies) {
                        if (c.getName().equals(st)) {
                            st = generaterandomnumber();
                            break;
                        }
                    }
                    Cookie cookie = new Cookie(st, id);
                    cookie.setMaxAge(600);
                    res.addCookie(cookie);
                    text.append("<font style='color:red'>請勿回覆此信</font><br />");
                    text.append("您在<strong>");
                    text.append(new Date());
                    text.append("</strong>時間於下列ip提出了密碼變更請求<strong>");
                    text.append(ipAddress);
                    text.append("</strong><br />若要找回密碼請點選連結<a href=\"");
                    text.append(sb.replace(sb.indexOf(req.getServletPath()), sb.length(), "/SessionView.do?id="));
                    text.append(st);
                    text.append("\">密碼找回</a>");
                    if (getMessage(from, to, subject, text.toString())) {
                        out.println("寄件成功");
                    } else {
                        out.println("寄件失敗");
                    }
                    break;
                }
            }
            if (b) {
                out.print("<script>alert(\"信件已經寄出，請於10分鐘內完成密碼修改。\");</script>");
                res.addHeader("refresh", "0;URL=index.jsp");
            } else {
                out.print("<script>alert(\"您輸入的信箱並未在資料庫之中，如有問題請通知系統管理者。\");</script>");
                res.addHeader("refresh", "0;URL=index.jsp");
            }
        }
    }

    private boolean getMessage(String from, String to, String subject, String text) {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(InsertData.class);
        boolean b = false;
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setSentDate(new Date());
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(text, "text/html;charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(mailHost, Integer.parseInt(mailPort), username, password);
            Transport.send(message);
            b = true;
        } catch (MessagingException ex) {
            logger.error(ex);
        }
        return b;
    }

    public String generaterandomnumber() {
        return Integer.toString((int) (Math.random() * 10000));
    }
}
