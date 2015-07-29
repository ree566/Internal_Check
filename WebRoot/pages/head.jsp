<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/head1.css">
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>-->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="js/jquery.headtacular.min.js"></script>
<script src="js/head1.js"></script>
<input type="hidden" id="state" value="<c:out value="${sessionScope.state}" default="" />" />
<div id="bootstrap">
    <div class="container">
        <div class="page-header">
            <h1>稽核系統</h1>
            <div id="welcomemsg" style="float:right;color:red">
                <b>親愛的 <c:out value="${sessionScope.name}" default="訪客"></c:out>
                    您好。
                </b>
            </div>
        </div>
        <nav class="navbar navbar-inverse header">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.jsp">回首頁</a>
                </div>
                <div>
                    <ul class="nav navbar-nav">
                        <li><a href="Alter.do" data-toggle="tooltip" data-placement="bottom" title="編輯問題">編輯資料</a></li>
                        <li><a href="Create.do" data-toggle="tooltip" data-placement="bottom" title="新增問題">新增資料</a></li>
                        <li><a href="Insert.do" data-toggle="tooltip" data-placement="bottom" title="從現有問題插入">從現有問題插入</a></li>
                        <li><a href="Delete.do" data-toggle="tooltip" data-placement="bottom" title="資料刪除">資料刪除</a></li>
                        <li><a href="AlterMember.do" data-toggle="tooltip" data-placement="bottom" title="限最高權限使用者">使用者管理</a></li>
                        <li><a href="exefiles/Renamer.exe">點此下載iso執行程式</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li id="login"><a href="Login.do"><span class="glyphicon glyphicon-log-in"></span> 管理人員登入</a></li>
                        <li id="logout"><a href="Logout.do">登出</a></li>
                        <li id="forgot"><a href="Mail.do">忘記密碼?</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>
<br />
<!--<a href="SearchFile.do">我是關鍵字</a>-->
<!-- 為了省略include所造成多餘的<html><body>標籤而簡化，encoding會有問題還是要加上開頭 -->