<%-- 
    Document   : JQueryTest
    Created on : 2015/6/22, 下午 02:51:44
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>密碼更新</title>
        <style>
            .error{
                color: red;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script>
            jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
                    function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
                        var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
                        return exp.test(value);                    //测试是否匹配
                    }, "格式錯誤");    //addMethod第3个参数:默认错误信息
            $(function () {
                $("#form").validate({
                    rules: {
                        psw: {
                            required: true,
                            regex: "^[0-9a-zA-Z-]+$",
                            rangelength: [5, 10]
                        },
                        psw2: {
                            equalTo: "#password"
                        }
                    },
                    messages: {
                        psw: {
                            required: "請輸入密碼",
                            regex: "格式錯誤",
                            rangelength: "長度(3-5)"
                        },
                        psw2: {
                            equalTo: "請輸入相同的密碼"
                        }
                    },
                    errorPlacement: function (error, element) {                             //错误信息位置设置方法  
                        error.appendTo(element.parent().next());                            //这里的element是录入数据的对象  
                    },
                    debug: false, //如果修改为true则表单不会提交
                    submitHandler: function (form) {
                        $("input[type='submit']").val("傳送中請稍後...");
                        $("input[type='submit']").attr("disabled", true);
                        form.submit();
                    }
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="pages/head.jsp"></jsp:include>
        <center>
            <form id="form" action="AlterMember.do" method="post">
                <table cellpadding="10">
                    <tr>
                        <td>請輸入密碼:</td>
                        <td><input type="text" id="password" name="psw"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>再次確認:</td>
                        <td><input type="text" id="password2" name="psw2"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="確認"></td>
                        <td><input type="hidden" name="old_psw" value=${alter_psw}></td>
                    <td></td>
                    <!--原密碼-->
                </tr>
            </table>
        </form>
        <div id="msg"></div>
    </center>
    <jsp:include page="pages/footer.jsp"></jsp:include>
</body>
</html>
