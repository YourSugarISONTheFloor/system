layui.use(['tree'], function () {
    var tree = layui.tree,
        data = "";

    ajax_hasData('/user/getDeptAll', 'GET', null, function (rest) {
        //将查询的child转换为children
        data = JSON.parse(JSON.stringify(rest.data).replace(/"child"/g, '"children"'));
        console.log(data)
    }, null, false);

    //仅节点左侧图标控制收缩
    tree.render({
        elem: '#tree',
        data: data,
        children: 'child',
        onlyIconControl: true, //是否仅允许节点左侧图标控制展开收缩
        click: function (obj) {
            localStorage.deptId = obj.data.id;
            localStorage.title = obj.data.title;
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
    });
});