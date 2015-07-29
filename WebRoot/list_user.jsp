<%-- 
    Document   : AJAX
    Created on : 2015/6/18, 下午 01:08:30
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="identit" class="com.ic.bean.IdentitBean" scope="application" />
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>使用者管理</title>
        <style>
            .name{
                width: 140px;
            }
            .user_name{
                width: 130px;
            }
            .jobnumber{
                width: 140px;
            }
            .user_jobnumber{
                width: 130px;
            }
            .password{
                width: 300px;
            }
            .user_psw{
                width: 290px;
            }
            .email{
                width: 300px;
            }
            .user_email{
                width: 290px;
            }
            #content{
                position: relative;
                display: block;
                margin: auto;
            }
            .float{
                position: fixed;
                left: 20px;
                bottom: 20px;   
                padding: 10px 15px;
                font-size: 20px;
                background-color:gray;
                opacity: 0.9;
            }
            .error{
                color: red;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script src="js/list_user.js"></script> 
        <script>
  
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.Jobnumber != \"root\"}">
            <c:redirect url=""/>
        </c:if>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <div id="content">
                <div id="container_r" style="width:1000px; margin: auto;">
                    <form id="form" method="post" action="AlterMember.do">
                        <table class="table table-striped">
                            <tr>
                                <th>id</th>
                                <th>使用者名稱</th>
                                <th>使用者帳號</th>
                                <th>使用者密碼</th>
                                <th>email</th>
                                <th>動作</th>
                            </tr>
                        <c:forEach var="user" items="${identit.allUsers}">
                            <c:if test="${user.jobnumber != \"root\"}">
                                <tr id="user${user.id}">
                                    <td class="id">${user.id}</td>
                                    <td class="name">${user.name}</td>
                                    <td class="jobnumber">${user.jobnumber}<input type="hidden" value="${user.jobnumber}"></td>
                                    <td class="password">${user.password}</td>
                                    <td class="email">${user.email}</td>    
                                    <td>
                                        <input type="button" value="修改" class="alter" name="${user.id}">   
                                        <input type="hidden" value="${user.id}">
                                        <input type="submit" value="刪除" class="delete" onclick="return confirm('確定刪除使用者${user.id}?')">
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                    <div class="float">
                        <input type="submit" value="確定" id="submit">
                        <input type="button" value="取消" id="undo">
                    </div>
                </form>
                <form id="form1" method="post" action="AlterMember.do">
                    <center>
                        新增使用者(四欄皆必填)：
                        <input type="text" name="new_user_name" placeholder="使用者名稱">
                        <input type="text" name="new_user_account" placeholder="使用者帳號(5-10)">
                        <input type="text" name="new_user_password" placeholder="使用者密碼(5-10)">
                        <input type="text" name="new_user_email" placeholder="Email Address">
                        <input type="submit" value="+">
                    </center>
                </form>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
