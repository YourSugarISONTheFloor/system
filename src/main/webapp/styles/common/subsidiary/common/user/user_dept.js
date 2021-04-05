layui.use('table', function () {
    var table = layui.table;

    /**
     * 系统管理--用户管理
     */
    var MgrUser = {
        tableId: "MyTable",    //表格id
        condition: {
            name: "",
            deptId: "",
            timeLimit: "",
            deptData: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrUser.initColumn = function () {
        return [[

            {type:'numbers'},
            {field: 'deptId', title: '编号',hide: true},
            {field: 'pid', title: '上级名称'},
            {field: 'name', title: '部门名称'},
            {align: 'center', toolbar: '#statusTpl', title: '操作', minWidth: 280, fixed: 'right'}
        ]];
    };

    //表格初始渲染
    var tableResult = table.render({
        text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        toolbar: '#tableBar', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '用户数据表',
        elem: '#' + MgrUser.tableId,
        url: '/user/getAllDept/',
        cols: MgrUser.initColumn(),
        page: true,
        height: "full-48",
        cellMinWidth: 100,
        id: 'userTable'
    });

    /**
     * 点击查询按钮
     */
    MgrUser.search = function () {
        layer.closeAll('loading');
        table.reload("userTable", {});

    };

    //头工具栏事件
    table.on('toolbar(MyTable)', function (obj) {
        switch (obj.event) {
            case 'addRole':
                layer.open({
                    type: 2,
                    title: '添加角色',
                    area: ['600px', '250px'],
                    //是否点击遮罩关闭
                    shadeClose: true,
                    content: '/user/dept_add',
                    end: function () {
                        layer.load(2);
                        MgrUser.search();
                    }
                });
                break;
            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('新增部门时，只需填写上级部门和部门名即可，编号自动生成');
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(MyTable)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                ajax_hasData('/user/deptDel', 'post', {id: data.deptId}, function (rest) {
                    layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
                    layer.close(index);
                    layer.load(2);
                    MgrUser.search();
                })
            });
        } else if (obj.event === 'edit') {
            layer.prompt({
                formType: 2,
                value: data.name
            }, function (value, index) {
                obj.update({
                    name: value
                });
                layer.close(index);
            });
        }
    });
});