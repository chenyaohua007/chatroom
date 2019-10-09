layui.use(['element','form', 'layedit', 'laydate'], function(){
    var element = layui.element;
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;
    var $ = layui.jquery;

    $("#mofpwd").on("click",function () {
        var str ='<table class="layui-table">\n' +
            '            <tbody id="calculation">\n' +
            '                <tr>\n' +
            '                    <td><input id="oldpwd" class="layui-input" type="password" autocomplete="off" placeholder="请输入原密码"></td>\n' +
            '                </tr>\n' +
            '                <tr>\n' +
            '                    <td><input id="newpwdA" class="layui-input" type="password" autocomplete="off" placeholder="请输入新密码"></td>\n' +
            '                </tr>\n' +
            '                <tr>\n' +
            '                    <td><input id="newpwdB" class="layui-input" type="password" autocomplete="off" placeholder="请确认新密码"></td>\n' +
            '                </tr>\n' +
            '            </tbody>\n' +
            '        </table>'
        layer.confirm(str,
            {title:'密码修改'}, function(index){
                var paras = {
                    "pwd" : "1",
                    "password" : $("#oldpwd").val()
                }
                $.post('/user/getUserinfo',paras,function(result){
                    if(result.data.length == 0){
                        layer.msg('旧密码不匹配！');
                        return;
                    }else{
                        if($("#newpwdA").val() != $("#newpwdB").val()){
                            layer.msg('新密码不一致，请重新输入！');
                            return;
                        }else{
                            var params = {
                                "pwd" : "1",
                                "password" : $("#newpwdA").val()
                            }
                            $.post('/user/saveUserinfo',params,function(result){
                                if(result.returncode == "0"){
                                    layer.msg('密码修改成功！');
                                    layer.close(index);
                                }
                            })
                        }
                    }
                })
        });
    })


    //监听提交
    form.on('submit(demo1)', function(data){
        layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        })
        return false;
    });
    $("#checkout").on("click", function () {
        $.ajax({
            url: "/login/logout",
            type: "post",
            async: "false",
            success: function (data) {
                window.location.href="/chatroom/login";
            }

        });
    })
});
