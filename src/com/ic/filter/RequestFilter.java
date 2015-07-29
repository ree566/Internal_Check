package com.ic.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("*.jsp")
public class RequestFilter implements Filter {

//    @SuppressWarnings("unused")
//    private FilterConfig fc;
    @Override
    public void init(FilterConfig config) throws ServletException {
        //this.fc = config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (session.getAttribute("name") == null) {
            httpReq.getRequestDispatcher("").forward(httpReq, httpRes);
//            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }//造成lag的原因?
    }

    @Override
    public void destroy() {
    }
}
