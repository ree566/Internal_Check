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

        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/index_2.js"></script>
        <script src="js/lockcommit.js"></script>
        <script>
            function change() {
                document.selection.submit();
            }
        </script>
    </head>
    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <div>
                <form name="selection" method="post" action="index2.jsp">
                    <center>
                        <select name="typelist" id="typelist" onchange="change()">
                            <option value="0">---請選擇問題類別---</option>
                        <c:forEach var="types" items="${type.catagorized}">
                            <option value="${types.id}">${types.name}</option>
                        </c:forEach>
                    </select>
                </center>
            </form>
        </div>
        <div id="container">
            <div id="container_r" style="width: 1000px; margin: 0px auto;">
                <hr />
                <font color="red">
                <p>※請先選擇問題分類，之後再點選編輯做資料更新。</p>
                </font>
                <c:choose>
                    <c:when test="${param.typelist != null}"> 
                        <form action="Alter.do" method="post" onsubmit="dosubmit()">
                            <c:forEach var="types" items="${type.getOneCatagorized(param.typelist)}">
                                <div id="typeforbg"><strong id="type">${types.name}</strong>
                                    <input type="hidden" id="type_no" name="type_no" value="${types.id}"/>
                                    <input type="button" id="alt_type" value="修改類別">
                                    <input type="submit" value="確定" class="edit">
                                    <input type="button" id="cancel" value="取消" class="edit">
                                </div>
                                <ul>
                                    <c:choose>
                                        <c:when test="${questions.getQuestionByType(param.typelist).isEmpty()}">
                                            <center><h3>此類別的問題數是零，可以點選上方的 <kbd><a href="createnew.jsp" style="color:white">新增資料</a></kbd> 來新增資料</h3></center>
                                            <hr>
                                        </c:when>
                                            <c:otherwise>
                                                <c:forEach var="question" items="${questions.getQuestionByType(param.typelist)}">
                                                <div id="q${question.id}">
                                                    <p>
                                                    <li class="que">${question.name}</li>
                                                    <input type="hidden" value="${question.id}" />
                                                    </p>
                                                    <ol>
                                                        <p>
                                                            <c:forEach var="ans" items="${answers.getAnswerByQuestion(question.id)}">
                                                            <li class="ans">${ans.name}</li>
                                                            <input type="hidden" value="${ans.id}" />
                                                        </c:forEach>
                                                        </p>
                                                    </ol>
                                                    <div>
                                                        <ul>
                                                            <c:forEach var="isos" items="${iso.getIsoByQuestion(question.id)}">
                                                                <li class="iso"><font>${isos.name}</font></li>
                                                                <input type="hidden" value="${isos.id}" />
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                    <div>
                                                        <input type="button" name="q${question.id}" class="replace" value="編輯">
                                                        <div class="edit">
                                                            <!--<input type="button" class="add_ans" value="新增解答">
                                                                <input type="button" class="remove_ans" value="移除新增的解答">
                                                                <input type="button" class="add_iso" value="新增iso">
                                                                <input type="button" class="remove_iso" value="移除新增的iso">-->
                                                            <input type="submit" class="confirm" value="確定">
                                                            <input type="button" class="undo" value="取消">                                                            
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr />
                                            </c:forEach> 
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </c:forEach> 
                        </form>
                    </c:when>
                    <c:when test="${param.typelist == -1}">
                    </c:when>
                    <c:otherwise>  

                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
