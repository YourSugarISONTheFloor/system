window.rootPath = (function () {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath = curWwwPath.substring(0, pos);
    return localhostPath;
})();

layui.config({
    base: rootPath + "/styles/js/lay-module/",
    version: true
}).extend({
    selectM: 'select/selectM'
});

layui.use(['form', 'selectM'], function () {
    var form = layui.form,
        selectM = layui.selectM,
        tagData = "",
        layer = layui.layer;


    ajax_hasData('/user/dept_all', 'get', null, function (rest) {
        tagData = rest.data;
    }, 'json', false);

    //角色多选下拉列表
    //多选标签-基本配置
    var MySelect = selectM({
        //元素容器【必填】
        elem: '#roleNames',
        field: {idName: 'deptId', titleName: 'deptName'},
        //候选数据【必填】
        data: tagData,
        //最多选中个数，默认5
        max: 1,
        //input的name 不设置与选择器相同(去#.)
        name: 'pid',
        //值的分隔符
        delimiter: ',',
        width: 400,
        //添加验证
        verify: 'required',
    });

    //监听提交
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        ajax_hasData('/user/addDept', 'post', data.field, function (rest) {
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