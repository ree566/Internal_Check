$(document).ready(function () {
    $("#submit").attr("disabled", true);
    $name = null;
    $psw = null;
    $email = null;

    $(".alter").click(function () {
        $id = "#user" + $(this).attr("name");
        $(this).next().attr({name: "user_id"});
        $(".alter").attr({"disabled": "disabled"});
        
        $($id + " .jobnumber").children("input[type='hidden']").attr("name", "job_number");

        $name = $($id + " .name").text();
        $($id + " .name").html("<input type='text' id='user_name' name='user_name' value='" + $name + "'>");

        $psw = $($id + " .password").text();
        $($id + " .password").html("<input type='text' id='user_psw'>");

        $email = $($id + " .email").text();
        $($id + " .email").html("<input type='text' id='user_email' name='user_email' value='" + $email + "'>");

        $($id).attr("class", "danger");
        $("input[type='text']").change(function () {
            $("#submit").removeAttr("disabled");
        });
        $("#user_psw").change(function () {
            $(this).attr("name", "user_psw");
        });
    });
    $("#undo").click(function () {
        $("label").remove();
        $("#submit").attr("disabled", true);
        $("input[type='hidden']").removeAttr("name");
        $("#user_name").replaceWith($name);
        $("#user_psw").replaceWith($psw);
        $("#user_email").replaceWith($email);
        $(".alter").removeAttr("disabled");
        $("tr").removeAttr("class");
    });
    $(".delete").click(function () {
        var id = "#user" + $(this).attr("name");
        $(this).prev().attr({name: "delete"});
    });
});
jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
        function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
            var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
            return exp.test(value);                    //测试是否匹配
        }, "格式錯誤");    //addMethod第3个参数:默认错误信息
var rule = {
    required: true,
    rangelength: [5, 10],
    regex: "^[0-9a-zA-Z-]+$"
};
var msg = {
    required: "必须填寫",
    rangelength: "請輸入5~10位英數字",
    regex: "格式錯誤"
};
$(function () {
    $("#form").validate({
        rules: {
            user_name: {required: true},
            user_psw: rule,
            user_email: {
                required: true,
                email: true
            }
        },
        messages: {
            user_name: {required: "必须填寫"},
            user_psw: msg,
            user_email: {
                required: "必须填寫",
                email: "格式錯誤"
            }
        },
        errorPlacement: function (error, element) {                             //错误信息位置设置方法  
            error.appendTo(element.parent().after());                            //这里的element是录入数据的对象  
        },
        debug: false, //如果修改为true则表单不会提交
        submitHandler: function (form) {
            $("input[type='submit']").val("傳送中請稍後...");
            $("input[type='submit']").attr("disabled", true);
            form.submit();
        }
    });
    $("#form1").validate({
        rules: {
            new_user_name: {required: true},
            new_user_account: rule,
            new_user_password: rule,
            new_user_email: {email: true}
        },
        messages: {
            new_user_name: msg,
            new_user_account: msg,
            new_user_password: msg,
            new_user_email: {email: "格式錯誤"}
        },
        errorPlacement: function (error, element) {                             //错误信息位置设置方法  
            error.appendTo(element.after());                            //这里的element是录入数据的对象  
        },
        debug: false, //如果修改为true则表单不会提交
        submitHandler: function (form) {
            $("input[type='submit']").val("傳送中請稍後...");
            $("input[type='submit']").attr("disabled", true);
            form.submit();
        }
    });
});