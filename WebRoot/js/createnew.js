$(document).ready(function () {
    //動態新增刪除按鈕
    $("input[type=text]").attr("autocomplete", "off");
    $("#addans").bind("click", function () {
        $("#ans").append("<tr><td class='title'>新增解答：</td><td><input type='text' name='new_ans'></td></tr>");
    });
    $("#addiso").bind("click", function () {
        $("#iso").append("<tr><td class='title'>新增iso：</td><td><input type='text' name='new_iso'></td></tr>");
    });
//    $("#removeans").click(function () {
//        $("#ans tbody").children().last().remove();
//    });
//    $("#removeiso").click(function () {
//        $("#iso tbody").children().last().remove();
//    });
    //新增連接的提示視窗所用
    $(function () {
//        var dialog, form, name = $("#name"),
//                link = $("#link");
//
//        function addMsg() {
//            $("#content").text("<a href='" + link.val() + "'>" + name.val() + "</a>");
//            //$("#msg").val() + "<a href='" + link.val() + "'>" + name.val() + "</a>"
////                    $("#users input[type='text']").val().append();
//            dialog.dialog("close");
//        }
//        dialog = $("#dialog-form").dialog({
//            autoOpen: false,
//            height: 220,
//            width: 350,
//            modal: true,
//            buttons: {
//                "確定": addMsg,
//                取消: function () {
//                    dialog.dialog("close");
//                }
//            },
//            close: function () {
//                form[ 0 ].reset();
//            }
//        });
//        form = dialog.find("form").on("submit", function (event) {
//            event.preventDefault();
//            addMsg();
//        });
//        $("#create").on("click", function () {
//            dialog.dialog("open");
//        });

        //資料庫關鍵字比對
        $("#search_question").click(function () {
            $.ajax({
                type: "POST",
                url: "SearchQuestion.do",
                data: {dataString: $("#new_question").val()},
                dataType: "html",
                success: function (response) {
                    $('#msg').html(response);
                }
            });
        });
        $.validator.addMethod("regex", //addMethod第1个参数:方法名称
                function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
                    var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
                    return exp.test(value);                    //测试是否匹配
                }, "格式錯誤");
        $('#create_new_data').validate({
            rules: {
                typelist: {
                    required: true
                },
                new_question: {
                    required: true
                }
            },
            messages: {
                typelist: {
                    required: '請選擇類別'
                },
                new_question: {
                    required: '請輸入問題'
                }
            },
            submitHandler: function (form) {
                $("input[type='submit']").val("傳送中請稍後...");
                $("input[type='submit']").attr("disabled", true);
                form.submit();
            }
        });
        $("#search_question").tooltip();
        $("#new_question").tooltip();
        
        $("#typelist").change(function(){
            if ($("#typelist").val() < 0){
                $(this).after("<input id=\"new_catagorized\" type=\"text\" placeholder=\"請輸入類別\" name=\"new_catagorized\">");
            }else{
                $("#new_catagorized").remove();
            }

        });
    });
});