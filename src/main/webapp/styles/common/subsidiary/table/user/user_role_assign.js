layui.use(['tree'], function () {
    var tree = layui.tree,
        data = "",
        roleIds = [];

    ajax_hasData('/menu/all', 'GET', null, function (rest) {
        //将查询的child转换为children
        console.log(rest)
        data = JSON.parse(JSON.stringify(rest.menuInfo).replace(/"child"/g, '"children"'));
    }, null, false);

    //仅节点左侧图标控制收缩
    tree.render({
        elem: '#tree',
        data: data,
        children: 'child',
        id: 'tree',
        showCheckbox: true,//是否显示复选框
        onlyIconControl: true, //是否仅允许节点左侧图标控制展开收缩
        checkChild: true,//不自动选中子类
        text: {
            defaultNodeName: '未命名', //节点默认名称
            none: '无数据' //数据为空时的提示文本
        }
    });
    tree.setChecked('tree', $("#role_list").val() == "" ? [] : JSON.parse($("#role_list").val())); //勾选指定节点

    //保存按钮
    $("#saveButton").bind("click", function () {
        var ajax_data = {
            id: Number($("#id").val()),
            role_list: getCheckedId(tree.getChecked('tree'))
        }
        roleIds = [];
        // console.log(ajax_data)
        ajax_hasData("/user/setRole", 'POST', {data: JSON.stringify(ajax_data)}, function (rest) {
            layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6})
        });
    });

    //取消按钮
    $("#closeButton").bind("click", function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    })

    //获取所有选中的节点id
    function getCheckedId(data) {
        $.each(data, function (index, item) {
            roleIds.push(item.id);
            //item 没有children属性
            if (item.children != null) {
                getCheckedId(item.children);
            }
        });
        return roleIds;
    }
});