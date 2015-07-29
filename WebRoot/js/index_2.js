$(document).ready(function () {
    $(".ans").each(function () {
        $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
    });
    $(".iso").each(function () {
        $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
    });
    $(".edit").hide();
    
    $("#alt_type").click(function(){
        $(this).hide();
        $(this).next().show();
        $("#cancel").show();
        $(".replace").attr("disabled", true);
        $type = $("#type").html();
        $("#type").html("<p>問題分類:<input type='text' id='type_list' value='" + $type + "'></p>");
        $("#type_list").change(function () {
            if ($(this).attr("name") !== "type_list") {
                $(this).attr({name: "type_list"});
//                $("#type").next().attr({name: "type_no"});
            }
        });
    });
    $("#cancel").click(function(){
        $(this).hide();
        $(this).prev().hide();
        $("#alt_type").show();
        $("#type_list").parent().replaceWith($type);
        $(".replace").removeAttr("disabled");
    });
    
    var $f;
    var $que;
    var $type;
    $(".replace").click(function () {
        $("#alt_type").attr("disabled", true);
        $(".replace").attr("disabled", true);
        $(this).removeAttr("disabled");
        $(this).parent().after("<center style='color:red'>↑ [ 編輯中 ] ↑</center>");
        $(this).hide();
        $(this).next().show();
        $f = "#" + $(this).attr("name");
        $que = $($f + " .que").html();
        $($f + " .que").html("<p>問題:<input type='text' id='que_list' value='" + $que + "'></p>");
                
        $($f + " .ans").each(function () {
            var text = $(this).html().replace("<br>", "\n");
            $(this).html("<p>解答:<textarea class='ans_list form-control' rows='3'>" + text + "</textarea></p>");
        });
        $($f + " font").each(function () {
            var text = $(this).html().replace("<br>", "\n");
            $(this).html("<p><textarea class='iso_list form-control' rows='2'>" + text + "</textarea></p>");
        });

        $("#que_list").change(function () {
            if ($(this).attr("name") !== "que_list") {
                $(this).attr({name: "que_list"});
                $($f + " .que").next().attr({name: "q_id"});
            }
        });
        $($f + " .ans_list").change(function () {
            if ($(this).attr("name") !== "ans_list") {
                $(this).attr({name: "ans_list"});
                $(this).parents(".ans").next().attr({name: "ans_id"});
//                $($f + " .que").next().attr({name: "q_id"});
            }
        });
        $($f + " .iso_list").change(function () {
            if ($(this).attr("name") !== "iso_list") {
                $(this).attr({name: "iso_list"});
                $(this).parents(".iso").next().attr({name: "iso_id"});
//                $($f + " .que").next().attr({name: "q_id"});
            }
        });
    });
    $(".undo").click(function () {
        $("#alt_type").removeAttr("disabled");
        $(".replace").removeAttr("disabled");
        $(this).parent().hide();
        $(this).parent().prev().show();
        $(this).parent().parent().next().remove();
        $("textarea").each(function () {
            var text = $(this).text().replace("\n", "<br>");
            $(this).parent().replaceWith(text);
        });
        $("#que_list").parent().replaceWith($que);
        $("input[type='hidden']").removeAttr("name");
        $("#type_no").attr("name", "type_no");
//        $(".new_ans").remove();
//        $(".new_iso").remove();
    });
});