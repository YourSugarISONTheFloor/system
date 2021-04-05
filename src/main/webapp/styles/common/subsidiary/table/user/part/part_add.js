layui.use(['form'], function () {
    var form = layui.form,
        layer = layui.layer;


    //监听提交
    form.on('submit(btnSubmit)', function (data) {
        ajax_hasData('/user/addPart', 'post', data.field, function (rest) {
            if (rest.code == 200) {
                layer.msg(rest.message);
            } else {
                layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6});
            }
        });
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
        return false;
    });

    $('#rest').click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.location.reload();//刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
        parent.layer.close(index); //再执行关闭
    })
});