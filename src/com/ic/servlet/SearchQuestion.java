package com.ic.servlet;

import com.ic.bean.QuestionBean;
import com.ic.entity.Question;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/SearchQuestion.do"})

public class SearchQuestion
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setCharacterEncoding("Utf8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String keyword = req.getParameter("dataString");
        keyword = keyword.replaceAll("[!@#$%^&*()_+=//?]", "");
        int count = 0;
        if (keyword.length() >= 2) {
            StringBuffer sb = new StringBuffer();
            QuestionBean qbean = new QuestionBean();
            Question q = new Question();
            Iterator it = qbean.getQuestion().iterator();
            while (it.hasNext()) {
                Question qList = (Question) it.next();
                String data = qList.getName();
                if (data.lastIndexOf(keyword) == 0) {
                    count++;
                    sb.append("<p>");
                    sb.append(count);
                    sb.append(". <font style='color:red'>");
                    sb.append(keyword);
                    sb.append("</font>");
                    sb.append(data.substring(keyword.length(), data.length()));
                    sb.append("</p>");
                } else if (data.indexOf(keyword) > 0 && data.lastIndexOf(keyword) != (data.length() - keyword.length())) {
                    String st[] = data.split(keyword);
                    count++;
                    sb.append("<p>");
                    sb.append(count);
                    sb.append(". ");
                    for (int a = 0; a < st.length; a++) {
                        sb.append(st[a]);
                        if (a < st.length - 1) {
                            sb.append("<font style='color:red'>");
                            sb.append(keyword);
                            sb.append("</font>");
                        }
                    }
                    sb.append("</p>");
                } else if (data.length() > keyword.length() && data.lastIndexOf(keyword) == (data.length() - keyword.length())) {
                    count++;
                    sb.append("<p>");
                    sb.append(count);
                    sb.append(". ");
                    sb.append(data.substring(0, data.length() - keyword.length()));
                    sb.append("<font style='color:red'>");
                    sb.append(keyword);
                    sb.append("</font>");
                    sb.append("</p>");
                } else {
                }
            }
            if (sb.length() == 0) {
                out.print("<p style='color:green'>沒有相似的問題</p>");
            } else {
                out.print(sb);
            }
        } else {
            out.print("<p style='color:red'>請輸入資料 或者 增加您的關鍵字長度</p>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("").forward(req, res);
    }
}
