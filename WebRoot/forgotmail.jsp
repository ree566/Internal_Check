<%-- 
Document   : error
Created on : 2015/7/8, 下午 05:47:21
Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>emailtest</title>
        <style>
            #to-error{
                color: red;
            }
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <!--        <script type="text/javascript" src="js/verifyy.js"></script>-->
        <script>
            function doSubmit() {
                var isCommitted = false;
                if (!isCommitted) {
                    isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
//                    $("input[type='submit']").val("傳送中請稍後...");
//                    $("input[type='submit']").attr("disabled", true);
                    return true;//返回true让表单正常提交
                } else {
                    return false;//返回false那么表单将不提交
                }
            }
            jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
                    function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
                        var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
                        return exp.test(value);                    //测试是否匹配
                    }, "格式錯誤");    //addMethod第3个参数:默认错误信息
            $(function () {
                $("#form").validate({
                    rules: {
                        to: {
                            required: true,
                            //                            regex: "^[0-9a-zA-Z-]+$",
                            email: true   //电子邮箱必须合法
                        } 
                    },
                    messages: {
                        to: {
                            required: "必须填寫",
                            //                            regex: "格式錯誤",
                            email: "格式錯誤"
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
            <form action="Mail.do" method="post" id="form">
                <p>請輸入您的電子郵件信箱，以便系統將密碼修改郵件寄給您。</p>
                <p>email:<input type="text" name="to" id="to"></p>
                <p><input type="submit" value="將修改密碼的郵件寄給我"></p>
            </form>
        </center>
    <jsp:include page="pages/footer.jsp"></jsp:include>
</body>
</html>
