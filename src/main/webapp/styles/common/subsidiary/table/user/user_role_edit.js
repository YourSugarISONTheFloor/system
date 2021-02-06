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
        layer = layui.layer,
        selectM = layui.selectM,
        tagData = "",
        //初始获取该用户的角色信息
        tagList = $("#roleList").val() == "" ? [] : JSON.parse($("#roleList").val());

    ajax_hasData('/user/getRole', 'get', null, function (rest) {
        tagData = rest.data;
    }, 'json', false);


    // 点击部门
    $('#deptName').focus(function () {
        parent.layer.open({
            type: 2,
            title: ['部门列表', 'background-color: #2F4056; color: #ffffff;'],
            area: ['300px', '400px'],
            content: '/user/getDeptTree',
            shadeClose: true,
            end: function () {
                $("#deptId").val(localStorage.deptId == null ? $("#deptId").val() : localStorage.deptId);
                $("#deptName").val(localStorage.title == null ? $("#deptName").val() : localStorage.title);
                localStorage.removeItem("deptId");
                localStorage.removeItem("title");
            }
        });
    });

    //角色多选下拉列表
    //多选标签-基本配置
    var MySelect = selectM({
        //元素容器【必填】
        elem: '#roleNames',
        field: {idName: 'roleId', titleName: 'name'},
        //候选数据【必填】
        data: tagData,
        //默认值
        selected: tagList,
        //最多选中个数，默认5
        max: 5,
        //input的name 不设置与选择器相同(去#.)
        name: 'roleData',
        //值的分隔符
        delimiter: ',',
        width: 400,
        //添加验证
        verify: 'required',
    });

    //监听提交
    form.on('submit(btnSubmit)', function (data) {
        //修改菜单
        layer.open({
            title: '',
            closeBtn: 0,
            content: '确定要修改该用户吗？',
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                data.field.roleData = JSON.stringify({
                    "roleList": MySelect.values.map(Number),
                    "roleNames": MySelect.names
                });
                // console.log(data.field)
                ajax_hasData('/user/editUser', 'post', data.field, function (rest) {
                    if (rest.code == 200) {
                        layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
                    } else {
                        layer.msg(rest.message, {icon: 2, time: 3 * 1000, shift: 6});
                    }
                });
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