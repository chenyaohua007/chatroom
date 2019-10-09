layui.use(['element', 'form'], function () {
    var element = layui.element;
    var $ = layui.jquery;

    //监听提交
    $("#checkin").on("click", function () {
        $.ajax({
            url: "/login/index",
            type: "post",
            async: "false",
            data: {
                "username": $("#form-username").val()
            },
            success: function (data) {
                window.location.href="/chatroom/index";
            }

        });
    })
});
