$(document).ready(function () {
    if ($("#state").val() !== "") {
        $("#welcomemsg").attr("style", "float:right;color:green");
        $("#login").hide();
        $("#forgot").hide();
        $("#logout").show();      
    } else {
        $("#login").show();
        $("#forgot").show();
        $("#logout").hide();    
    }
    $('.header').headtacular({ scrollPoint: 150 });
});