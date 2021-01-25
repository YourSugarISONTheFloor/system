//<![CDATA[
layui.use(['form', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;
    //获取页面中的错误信息
    var msg = "[[${msg}]]";
    if (msg != "") {
        layer.msg(msg, {icon: 2, time: 3 * 1000, shift: 6});
    }

    // 登录过期的时候，跳出ifram框架
    if (top.location != self.location) top.location = self.location;
    //查看密码
    $('.bind-password').on('click', function () {
        if ($(this).hasClass('icon-5')) {
            $(this).removeClass('icon-5');
            $("input[name='password']").attr('type', 'password');
        } else {
            $(this).addClass('icon-5');
            $("input[name='password']").attr('type', 'text');
        }
    });
    //保持登录
    $('.icon-nocheck').on('click', function () {
        if ($(this).hasClass('icon-check')) {
            $(this).removeClass('icon-check');
        } else {
            $(this).addClass('icon-check');
        }
    });
    //看不清，换一张
    $('#refreshCaptcha').on('click', function () {
        this.src = '/codeImage?time=' + (new Date).getTime();
    })
    //忘记密码
    $('.forget-password').on('click', function () {
        MyMethod.language('/forgetPassword');
    });

    //立即注册
    $('.registered').on('click', function () {
        MyMethod.language('/registered');
    });
    if (sessionStorage.getItem("user") != "") {
        $("input[name='username']").val(sessionStorage.getItem("user"));
        $("input[name='password']").val(sessionStorage.getItem("word"));
    }
    // 进行登录操作
    form.on('submit(login)', function (data) {
        data = data.field;
        sessionStorage.setItem("user", data.username);
        sessionStorage.setItem("word", data.password);
        if (data.username == '') {
            layer.msg('[[#{login.usernameNull}]]', {icon: 2, time: 3 * 1000, shift: 6});
            return false;
        }
        if (data.password == '') {
            layer.msg('[[#{login.passwordNull}]]', {icon: 2, time: 3 * 1000, shift: 6});
            return false;
        }
        if (data.clod == '') {
            layer.msg('[[#{login.codeNull}]]', {icon: 2, time: 3 * 1000, shift: 6});
            return false;
        }
        ajax_hasData('/logging', 'post', data, function (rest) {
            console.log(rest)
            if (rest.code == 200) {
                //存储token
                localStorage.token = rest.data;
                MyMethod.setCookie("token", rest.data, 10);
                MyMethod.language('/');
            } else {
                layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6});
            }
        }, null, false);
        //更换验证码
        $('#refreshCaptcha').click();
        return false;
    });
});
// ]]>