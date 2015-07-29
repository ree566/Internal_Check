<%-- 
    Document   : index2
    Created on : 2015/6/24, 上午 08:36:55
    Author     : Wei.Cheng
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="type" class="com.ic.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="answers" class="com.ic.bean.AnswerBean" scope="application" />
<jsp:useBean id="questions" class="com.ic.bean.QuestionBean" scope="application" />
<jsp:useBean id="iso" class="com.ic.bean.IsoBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>稽核系統</title>
        <link rel=stylesheet type="text/css" href="styles/index.min.css">
        <style>
            .mar{
                margin-bottom:20px;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script>
            function change() {
                document.selection.submit();
            }
            $(document).ready(function () {
                hide();
                $(".content").first().show();
                $(".que").click(function () {
                    $(this).next().slideToggle();
                });
                $(".ans").each(function () {
                    $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
                });
                $(".iso").each(function () {
                    $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
                });
                $("#open").click(function () {
                    $(".content").show();
                });
                $("#close").click(hide);

                function hide() {
                    $(".content").hide();
                }
            });
        </script>
    </head>
    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <form name="selection"  method="post" action="index.jsp">
                <center>
                    <select name="typelist" id="typelist" onchange="change()">
                        <option value="">---請選擇問題類別---</option>
                    <c:forEach var="types" items="${type.catagorized}">
                        <option value="${types.id}">${types.name}</option>
                    </c:forEach>
                </select>
            </center>
        </form>
        <div id="container">
            <div id="container_r" style="width: 1000px; margin: 0px auto;">
                <hr>
                <c:choose>
                    <c:when test="${param.typelist != null}">
                        <c:forEach var="types" items="${type.getOneCatagorized(param.typelist)}">
                            <div id="typeforbg" class="mar"><strong id="type">${types.name}</strong> [<b id="open" class="point">全部展開</b> | <b id="close" class="point">收合</b>]</div>
                            <ul>
                                <c:choose>
                                    <c:when test="${questions.getQuestionByType(param.typelist).isEmpty()}">
                                        <center>
                                            <h3>此類別的問題數是零，如需新增請先 <kbd><a href="Login.do" style="color:white">登入</a></kbd></h3>
                                            <h3>若您已經登入，可點選 <kbd><a href="createnew.jsp" style="color:white">新增資料</a></kbd> 來新增資料</h3>
                                        </center>
                                        <hr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="question" items="${questions.getQuestionByType(param.typelist)}">
                                            <li class="que point mar">${question.name}</li>
                                            <div class="content">
                                                <ol class="anslist">
                                                    <c:forEach var="ans" items="${answers.getAnswerByQuestion(question.id)}">
                                                        <li class="ans mar">${ans.name}</li>
                                                        </c:forEach>
                                                </ol>
                                                <ul>
                                                    <c:forEach var="isos" items="${iso.getIsoByQuestion(question.id)}">
                                                        <li class="iso mar"><font>${isos.name}</font></li>
                                                        </c:forEach>
                                                </ul>
                                            </div>
                                            <hr />
                                        </c:forEach> 
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </c:forEach> 
                    </c:when>

                    <c:otherwise>  
                        <center>
                            <div>
                                <img src="pages/images/mouse-pointer_f.png"/>    
                            </div>
                        </center>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
