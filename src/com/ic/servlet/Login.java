package com.ic.servlet;

import com.ic.bean.IdentitBean;
import com.ic.entity.Identit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@WebServlet(urlPatterns = {"/Login.do"},
        initParams = {
            @WebInitParam(name = "LOGIN_VIEW", value = "login.jsp")}
)
public class Login extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String LOGIN_VIEW;

    @Override
    public void init() throws ServletException {
        LOGIN_VIEW = getServletConfig().getInitParameter("LOGIN_VIEW");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Login.class);
        HttpSession session = request.getSession(true);
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        boolean flag = false;
        if (name.equals("root")) {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(getServletContext().getRealPath("WEB-INF/classes/test.properties"));
            properties.load(in);
            String ppsw = properties.getProperty("password");
            if (password.equals(ppsw)) {
                session.setAttribute("name", properties.getProperty("username"));
                session.setAttribute("id", properties.getProperty("id"));
                session.setAttribute("Jobnumber", name);
                session.setAttribute("state", "login");
                logger.info("User " + name + " is login.");
                flag = true;
            }
        } else {
            IdentitBean id = new IdentitBean();
            Identit i = new Identit();
            name = name.trim();
            password = password.trim();
            i = id.login(name, password);
            if (i != null) {
                session.setAttribute("name", i.getName());
                session.setAttribute("id", i.getId());
                session.setAttribute("Jobnumber", name);
                session.setAttribute("state", "login");
                logger.info("User " + name + " is login.");
                flag = true;
            }
        }
        if (flag) {
            request.getRequestDispatcher("").forward(request, response);
        } else {
            request.setAttribute("errormsg", "錯誤的帳號或密碼");
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }
}
