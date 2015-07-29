/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.servlet;

import com.ic.bean.IdentitBean;
import com.ic.entity.Identit;
import com.ic.testclassess.TestClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(
        urlPatterns = {"/Test.do"},
        initParams = {
            @WebInitParam(name = "VIEW", value = "/test.jsp")}
)
public class TestData extends HttpServlet {

    private String VIEW;

    @Override
    public void init() throws ServletException {
        VIEW = getServletConfig().getInitParameter("VIEW");
    }

    public void doProcessRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        String username = req.getPathInfo().substring(1);
//        req.setAttribute("name", username);
//        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
//        String user = req.getPathInfo().substring(1);
//        res.getWriter().print(user);
//        req.setAttribute("name", user);
//        req.getRequestDispatcher(VIEW).forward(req, res);
        res.setContentType("text/html");
        String[] st = req.getParameterValues("text");
        String st1 = req.getParameter("text1");
        for(String str:st){
            res.getWriter().print(str);
        }   
        res.getWriter().print(st1);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }
}
