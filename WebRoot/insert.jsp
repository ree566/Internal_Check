<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="type" class="com.ic.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="answers" class="com.ic.bean.AnswerBean" scope="application" />
<jsp:useBean id="questions" class="com.ic.bean.QuestionBean" scope="application" />
<jsp:useBean id="iso" class="com.ic.bean.IsoBean" scope="application" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>新增資料</title>
        <link rel=stylesheet type="text/css" href="styles/index.min.css">
        <style>
            /*            input[type='submit']{
                            position: fixed;
                            left: 20px;
                            bottom: 20px;   
                            padding: 10px 15px;
                            font-size: 20px;
                        }*/
            textarea{
                resize: none;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/lockcommit.js"></script>
        <script>
            function change() {
                document.selection.submit();
            }
            $(document).ready(function () {
                var $f;
                $(".edit").hide();
                $(".text-danger").hide();
                $(".replace").click(function () {
                    $f = "#" + $(this).attr("name");
                    $(this).next().show();
                    $(this).hide();
                    $(".replace").attr("disabled", true);
                });
                $(".undo").click(function () {
                    $(this).parent().hide();
                    $($f + " b").hide();
                    $($f + " .replace").show();
                    $(".replace").removeAttr("disabled");
                    $($f + " .new_ans").remove();
                    $($f + " .new_iso").remove();
                });
                $(".add_ans").bind("click", function () {
                    $($f + " .que").next().attr({name: "q_id"});
                    $($f + " .ans_list").append("<p><li class='new_ans'>新增解答：<textarea class='form-control' rows='3' name='new_ans' placeholder='請輸入解答'></textarea></li></p>");
                    $($f + " .text-danger").fadeIn();
                });
                $(".add_iso").bind("click", function () {
                    $($f + " .que").next().attr({name: "q_id"});
                    $($f + " .iso_list").append("<p class='new_iso'>新增參考文件：<textarea class='form-control' rows='2' name='new_iso' placeholder='請輸入iso'></textarea></p>");
                    $($f + " .text-danger").fadeIn();
                });
                $(".remove_ans").click(function () {
                    $(".new_ans").last().remove();
                });
                $(".remove_iso").click(function () {
                    $(".new_iso").last().remove();
                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <div>
                <form name="selection" method="post" action="insert.jsp">
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
                    <p>※請先選擇問題分類，之後按下編輯進行問題以及文件的新增。</p>
                </font>
                <c:choose>
                    <c:when test="${param.typelist != null}"> 
                        <form action="Insert.do" method="post" onsubmit="return dosubmit()">
                            <input type="hidden" name="selectedtype" value="${param.typelist}">
                            <c:forEach var="types" items="${type.getOneCatagorized(param.typelist)}">
                                <strong>                            
                                    類別:${types.name}
                                </strong>
                                <ul>
                                    <c:choose>
                                        <c:when test="${questions.getQuestionByType(param.typelist).isEmpty()}">
                                            <center><h3>此類別的問題數是零，可先到 <kbd><a href="createnew.jsp" style="color:white">新增資料</a></kbd> 來新增資料。</h3></center>
                                            <hr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="question" items="${questions.getQuestionByType(param.typelist)}">
                                                <div id="q${question.id}">
                                                    <p><li class="que">                                       
                                                        ${question.name}
                                                    </li>
                                                    <input type="hidden" value="${question.id}">
                                                    </p>
                                                    <ol class="ans_list">
                                                        <p>
                                                            <c:forEach var="ans" items="${answers.getAnswerByQuestion(question.id)}">
                                                            <li class="ans">                                                 
                                                                ${ans.name}
                                                            </li>
                                                        </c:forEach>
                                                        </p>
                                                    </ol>
                                                    <ul class="iso_list">
                                                        <c:forEach var="isos" items="${iso.getIsoByQuestion(question.id)}">
                                                            <li class="iso">
                                                                <font>${isos.name}</font>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                    <div>
                                                        <input type="button" name="q${question.id}" class="replace" value="編輯">
                                                        <div class="edit">
                                                            <input type="button" class="undo" value="取消">
                                                            <input type="button" class="add_ans" value="新增解答">
                                                            <input type="button" class="remove_ans" value="移除新增的解答">
                                                            <input type="button" class="add_iso" value="新增iso">
                                                            <input type="button" class="remove_iso" value="移除新增的iso"> 
                                                            <input type="submit" value="確定">
                                                            <b class="text-danger">←修改完之後別忘了按確定來儲存資料</b>
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
                </c:choose>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
