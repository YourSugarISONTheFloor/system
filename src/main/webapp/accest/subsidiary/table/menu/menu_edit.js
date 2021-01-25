layui.use(['form'], function () {
    var form = layui.form,
        layer = layui.layer;

    // 点击父级菜单
    $('#pcodeName').click(function () {
        layer.open({
            type: 2,
            title: ['父级菜单', 'background-color: #2F4056; color: #ffffff;'],
            area: ['300px', '400px'],
            content: '/menu/commonTree',
            shadeClose: true,
            end: function () {
                $("#pid").val(localStorage.pid == null ? $("#pid").val() : localStorage.pid);
                $("#pcodeName").val(localStorage.title == null ? $("#pcodeName").val() : localStorage.title);
                localStorage.removeItem("pid");
                localStorage.removeItem("title");
            }
        });
    });

    //监听提交
    form.on('submit(btnSubmit)', function (data) {
        //如果是菜单的话，将href置换为空
        data.field.href = data.field.isMenu == 0 ? "" : data.field.href;
        ajax_hasData('/menu/hasParent', 'post', {pid: data.field.pid}, function (rest) {
            if (rest.code == 200) {
                //修改菜单
                layer.open({
                    title: '',
                    closeBtn: 0,
                    content: '确定要修改该菜单吗？',
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        ajax_hasData('/menu/editMenu', 'post', data.field, function (rest) {
                            if (rest.code == 200) {
                                layer.msg(rest.message);
                            } else {
                                layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6});
                            }
                        });
                    }
                });

            } else {
                layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6});
            }
        });
        return false;
    });

    $('#rest').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
        parent.layer.close(index); //再执行关闭
    })
});