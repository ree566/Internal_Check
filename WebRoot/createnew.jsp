<%-- 
    Document   : createnew
    Created on : 2015/6/16, 下午 03:09:55
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="type" class="com.ic.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="qBean" class="com.ic.bean.QuestionBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <title>新增問題</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <style>
            input[type='text']{
                width: 60%;
            }
            table{
                width:100%;
            }
            .title{
                width:150px;
            }
            .error{
                color: red;
            }
        </style>

        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script src="js/createnew.js"></script>
        <script src="js/lockcommit.js"></script>
    </head>
    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
        <div id="container">
            <div id="container_r" style="width: 1000px; margin: 0px auto;">
                <form action="Create.do" method="post" class="ui-widget" id="create_new_data" onsubmit="dosubmit()">
                    <table>
                        <tr>
                            <td class="title">請選擇問題分類：</td>
                            <td>
                                <select name="typelist" id="typelist">
                                    <option value="">---請選擇問題類別---</option>
                                    <c:forEach var="types" items="${type.catagorized}">
                                        <option value="${types.id}">${types.name}</option>
                                    </c:forEach>
                                        <option value="-1">---新增類別---</option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="title">請輸入問題：</td>
                            <td>
                                <input type="text" name="new_question" id="new_question" title="欲查詢時請盡量避免特殊符號"/>
                                <input type="button" value="查相似" id="search_question" title="搜尋結果會顯示比對結果下"/>
                            </td>
                            <td>                          
                            </td>
                        </tr>
                    </table>
                    <table id="ans"></table>
                    <table id="iso"></table>
                    <div>
                        <input type="button" value="新增解答" id="addans" />
                        <!--<input type="button" value="刪除解答" id="removeans" />-->
                        <input type="button" value="新增iso文件" id="addiso" />
                        <!--<input type="button" value="刪除iso文件" id="removeiso" />-->
                        <!--<input type="button" id="create" value="新增連結" />-->
                        <input type="submit" class="confirm" value="確定"/>
                    </div>
                </form>
<!--舊的新增連結div-->
<!--                <div id="dialog-form" title="新增連結">
                    <form>
                        <table>
                            <tr>
                                <td>連結名稱</td>
                                <td><input type="text" name="name" id="name"></td>
                            </tr>
                            <tr>
                                <td>Link</td>
                                <td><input type="text" name="link" id="link"></td>
                            </tr>

                             Allow form submission with keyboard without duplicating the dialog button 
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                        </table>
                    </form>
                </div>-->
                <!--            連結語法:<div id="msg" class="ui-widget"></div>-->
                <!--<p><input type="button" id="copy_btn" value="Copy to Clipboard" /></p>-->
                <p><a id="content"></a></p>
                <p>比對結果：</p>
                <div id="msg"></div>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
    </body>
</html>
