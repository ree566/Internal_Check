<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="type" class="com.ic.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="answers" class="com.ic.bean.AnswerBean" scope="application" />
<jsp:useBean id="questions" class="com.ic.bean.QuestionBean" scope="application" />
<jsp:useBean id="iso" class="com.ic.bean.IsoBean" scope="application" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>刪除資料</title>
        <link rel=stylesheet type="text/css" href="styles/index.min.css">
        <style>
            #delete{
                position: fixed;
                left: 20px;
                bottom: 20px;   
                padding: 10px 15px;
                font-size: 20px;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/lockcommit.js"></script>
        <script>
            function change() {
                document.selection.submit();
            }
            $(document).ready(function () {
                $(".ans").each(function () {
                    $(this).html($(this).html().replace("\n", "<br>"));
                });
                $(".iso").each(function () {
                    $(this).html($(this).html().replace("\n", "<br>"));
                });
                $("#type").click(function () {
                    if ($(this).prop("checked")) {
                        $("input[name='question']").prop("checked", true);
                        $("input[name='ans']").prop("checked", true);
                        $("input[name='iso']").prop("checked", true);
                    } else {
                        $("input[name='question']").prop("checked", false);
                        $("input[name='ans']").prop("checked", false);
                        $("input[name='iso']").prop("checked", false);
                    }
                });
                $("input[name='question']").click(function () {
                    $id = "#q" + $(this).val();
                    if ($(this).prop("checked")) {
                        $($id + " input[name='ans']").prop("checked", true);
                        $($id + " input[name='iso']").prop("checked", true);
                    } else {
                        $($id + " input[name='ans']").prop("checked", false);
                        $($id + " input[name='iso']").prop("checked", false);
                    }
                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <div>
                <form name="selection"  method="post" action="ddata.jsp">
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
            <div id="container_r" style="width:1000px; margin: auto;">
                <hr />
                <font color="red">
                    <p>※欲刪除問題請先選擇問題分類，之後再勾選您想要刪除的項目，確定刪除資料請按左下方的刪除鈕。</p>
                    <p>※請注意，選擇刪除類別或者問題時，會一併刪除掉子類別。</p>
                </font>
                <c:choose>
                    <c:when test="${param.typelist != null}"> 
                        <form action="Delete.do" method="post" onsubmit="return dosubmit()">
                            <input type="hidden" name="selectedtype" value="${param.typelist}">
                            <c:forEach var="types" items="${type.getOneCatagorized(param.typelist)}">
                                <strong>                            
                                    <input name="type" id="type" type=checkbox value="${types.id}">
                                    類別:${types.name}
                                </strong>
                                <ul>
                                    <c:forEach var="question" items="${questions.getQuestionByType(param.typelist)}">
                                        <div id="q${question.id}">
                                            <p><li class="que">                                       
                                                <input name="question" type=checkbox value="${question.id}">${question.name}
                                            </li></p>
                                            <ol>
                                                <p>
                                                    <c:forEach var="ans" items="${answers.getAnswerByQuestion(question.id)}">
                                                    <li class="ans"><input name="ans" type=checkbox value="${ans.id}">${ans.name}</li>
                                                    </c:forEach>
                                                </p>
                                            </ol>
                                            <ul>
                                                <c:forEach var="isos" items="${iso.getIsoByQuestion(question.id)}">
                                                    <li class="iso"><input name="iso" type=checkbox value="${isos.id}"> <font>${isos.name}</font></li>
                                                        </c:forEach>
                                            </ul>
                                        </div>
                                        <hr />
                                    </c:forEach> 
                                </ul>
                            </c:forEach> 
                            <input type="submit" value="刪除" id="delete" onclick="return confirm('確定刪除以上所選資料?')">
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
