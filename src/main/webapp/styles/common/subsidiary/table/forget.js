//<![CDATA[
layui.use(['form', 'step'], function () {
        var $ = layui.$;
        var form = layui.form;
        var step = layui.step;
        var btn_getCode = false;
        var account = $("select option:selected").val();
        var verify;

        form.verify({
            pass: [/^(?=.*[a-zA-Z])(?=.*\d)[^]{6,}$/, '[[#{forget.msgPasswordRules}]]']
        })

        //下拉列表的选中事件
        $(".layui-anim-upbit dd").on('click', function () {
            //判断元素是否为不可点击状态
            if ($(this).prop("disabled") == true) {
                account = $(this).attr("lay-value");
            }
            if (account == 1) {
                verify = "phone";
            } else {
                verify = "email";
            }
            $('.account').attr("lay-verify", verify);
        });

        step.render({
            elem: '#stepForm',
            filter: 'stepForm',
            width: '100%', //设置容器宽度
            stepWidth: '750px',
            height: '500px',
            stepItems: [{
                title: '[[#{forget.step_one}]]'
            }, {
                title: '[[#{forget.getcode}]]'
            }, {
                title: '[[#{forget.step_two}]]'
            }]
        });

        form.on('submit(formStep)', function (data) {
            if ($('.password_1').val() == $('.password_2').val()) {
                //赋值给获取验证码的账号
                $('.account_two').text($('.account').val());
                step.next('#stepForm');
            } else {
                layer.msg("[[#{forget.msg_pass}]]", {icon: 2, time: 3 * 1000, shift: 6});
            }
            return false;
        });

        form.on('submit(formStep2)', function (data) {
            if ($("input[name='code']").val() == "") {
                layer.msg('[[#{forget.codeMsg}]]', {icon: 2, time: 3 * 1000, shift: 6})
                return false;
            }
            ajax_hasData("/forgetPassword/ifCode", "GET", {code: $("input[name='code']").val()}, function (data) {
                if (data == 409) {
                    ajax_hasData("/forgetPassword/updatePassword", "POST", {
                        password: $("input[name='password']").val(),
                        username: $('.account').val()
                    }, function (rest) {
                        if (rest.code == 230) {
                            //分布表单下一步
                            step.next('#stepForm');
                        } else {
                            layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6})
                        }
                    })
                } else {
                    layer.msg('[[#{forget.codeMsg}]]', {icon: 2, time: 3 * 1000, shift: 6})
                }
            })
            return false;
        });

        form.on('submit(formStep3)', function (data) {
            if ($("input[name='code']").val() == "") {
                layer.msg('[[#{forget.codeMsg}]]', {icon: 2, time: 3 * 1000, shift: 6})
                return false;
            }
            ajax_hasData("/forgetPassword/ifCode", "GET", {code: $("input[name='code']").val()}, function (data) {
                if (data == 409) {
                    ajax_hasData("/registered/addUser", "POST", {
                        password: $("input[name='password']").val(),
                        username: $('.account').val(),
                        name: $("input[name='name']").val()
                    }, function (rests) {
                        console.log(rests)
                        if (rests.code == 220) {
                            //分布表单下一步
                            step.next('#stepForm');
                        } else {
                            layer.msg(rests.message, {icon: 2, time: 3 * 1000, shift: 6})
                        }
                    })
                } else {
                    layer.msg('[[#{forget.codeMsg}]]', {icon: 2, time: 3 * 1000, shift: 6})
                }
            });
            return false;
        });

        $('.pre').click(function () {
            //分布表单上一步
            step.pre('#stepForm');
        });

        //获取验证码，一分钟内只能点击一次
        $('.getCode').on('click', function () {
            if (!btn_getCode) {
                btn_getCode = true;
                getcode($(this));
                ajax_hasData("/forgetPassword/getCode", null, {user: $('.account_two').text()})
            } else {
                return false;
            }
        })

        $('.next').click(function () {
            step.pre('#stepForm');
        });

        //前往登录
        $('.land').click(function () {
            window.location = 'login';
        });

        //点击事件
        var getcode = function (date) {
            //获取点击的对象
            var obj = date;
            //获取按钮的文本
            var text = obj.text();
            //给对象添加禁止点击
            obj.addClass("layui-disabled");
            var time = 59;
            //定义一个每秒执行一次的事件
            var timer = setInterval(function () {
                var temp = time--;
                //更改按钮的值
                obj.text(text + ' (' + temp + "S)");
                //判断是否已经结束了禁止点击的时间
                if (temp <= 0) {
                    btn_getCode = false;
                    //移除禁止点击的效果
                    obj.removeClass("layui-disabled");
                    //改回按钮的值
                    obj.text(text);
                    //清除每秒执行的一次的事件
                    clearInterval(timer);
                    return;
                }
            }, 1000);
        }
    }
)
// ]]>