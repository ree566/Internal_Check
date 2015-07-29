package com.ic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@WebServlet(urlPatterns = {"/Logout.do"},
        initParams = {
            @WebInitParam(name = "LOGOUT_VIEW", value = "index.jsp")}
)

public class Logout extends HttpServlet {

    private String LOGOUT_VIEW;
    
    @Override
    public void init() throws ServletException {
        LOGOUT_VIEW = getServletConfig().getInitParameter("LOGOUT_VIEW");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(InsertData.class);
        HttpSession session = request.getSession();
        logger.info(session.getAttribute("Jobnumber") + " is logout.");
        session.invalidate();
        request.getRequestDispatcher(LOGOUT_VIEW).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req,res);
    }
}
