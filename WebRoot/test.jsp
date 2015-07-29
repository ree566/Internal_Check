<%-- 
    Document   : testjsp
    Created on : 2015/7/14, 下午 01:32:50
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="type" class="com.ic.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="answers" class="com.ic.bean.AnswerBean" scope="application" />
<jsp:useBean id="questions" class="com.ic.bean.QuestionBean" scope="application" />
<jsp:useBean id="iso" class="com.ic.bean.IsoBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            textarea{
                width:80%;
                height:150px;
                resize: none;
            }

        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script>
        </script>
    </head>

    <body>
        <form action="Test.do" method="post">
            <input type="text" name="text">
            <input type="text" name="text">
            <input type="text" name="text">
            <input type="text" name="text1">
            <input type="submit" value="test">
        </form>
    </body>
</html>
