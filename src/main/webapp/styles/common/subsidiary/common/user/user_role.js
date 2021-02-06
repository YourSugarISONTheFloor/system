layui.use(['layer', 'form', 'table', 'laydate', 'tree'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var tree = layui.tree;

    /**
     * 系统管理--用户管理
     */
    var MgrUser = {
        tableId: "userTable",    //表格id
        treeId: "deptTree",      //部门id
        deptIdList: null,
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
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '用户编号'},
            {field: 'name', sort: true, title: '姓名'},
            {field: 'deptName', sort: true, title: '部门'},
            {field: 'roleNames', sort: true, title: '角色'},
            {field: 'email', title: '邮箱'},
            {field: 'phone', title: '电话'},
            {field: 'status', templet: '#statusTpl', title: '状态', align: 'center', fixed: 'right', unresize: true},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280, fixed: 'right'}
        ]];
    };

    /**
     * 初始化左侧部门树
     */
    ajax_hasData('/user/getDeptAdd', 'GET', null, function (rest) {
        //将查询的child转换为children
        // MgrUser.condition.deptData = rest.data
        MgrUser.condition.deptData = JSON.parse(JSON.stringify(rest.data).replace(/"child"/g, '"children"'));
    }, null, false);

    //左侧部门渲染，仅节点左侧图标控制收缩
    tree.render({
        elem: '#' + MgrUser.treeId,
        accordion: true,//是否开启手风琴模式，默认 false
        data: MgrUser.condition.deptData,
        children: 'child',
        onlyIconControl: true, //是否仅允许节点左侧图标控制展开收缩
        text: {
            defaultNodeName: '未命名', //节点默认名称
            none: '无数据' //数据为空时的提示文本
        },
        click: function (item) {
            MgrUser.onClickDept(item);
        }
    });

    //表格初始渲染
    var tableResult = table.render({
        text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        elem: '#' + MgrUser.tableId,
        url: '/user/getAll/',
        cols: MgrUser.initColumn(),
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        id: 'userTable'
    });

    /**
     * 获取点击部门的ID，包括所有子类的ID
     * @param map
     */
    MgrUser.getMapAll = function (map) {
        MgrUser.deptIdList.push(map.id);
        if (map.children != null) {
            var i;
            for (i in map.children) {
                MgrUser.getMapAll(map.children[i]);
            }
        }
    }

    /**
     * 选择部门时
     */
    MgrUser.onClickDept = function (treeNode) {
        MgrUser.deptIdList = [];
        MgrUser.getMapAll(treeNode.data);
        MgrUser.condition.deptId = MgrUser.deptIdList;
        MgrUser.search();
    };

    /**
     * 点击查询按钮
     */
    MgrUser.search = function () {
        layer.closeAll('loading');
        var queryData = {};
        queryData['deptId'] = MgrUser.deptIdList == null ? null : MgrUser.condition.deptId.toString();
        queryData['name'] = MgrUser.deptIdList == null ? $("#name").val() : null;
        table.reload(MgrUser.tableId, {where: queryData});
        //清空标识
        MgrUser.deptIdList = null;
    };

    /**
     * 导出excel按钮
     */
    MgrUser.exportExcel = function () {
        var checkRows = table.checkStatus(MgrUser.tableId);
        if (checkRows.data.length === 0) {
            layer.msg("请选择要导出的数据", {icon: 2, time: 3 * 1000, shift: 6});
        } else {
            var xlsData = checkRows.data;
            //遍历选中的列表中的数据
            for (data of xlsData) {
                data.status = data.status == 0 ? "正常" : "冻结";
            }
            table.exportFile(tableResult.config.id, xlsData, 'xls');
        }
    };


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrUser.search();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MgrUser.exportExcel();
    });


    //监听状态操作
    form.on('switch(state)', function (obj) {
        ajax_hasData('/user/editState', 'post', {id: this.value, status: obj.elem.checked}, function (rest) {
            layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
        })
    });

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    MgrUser.onEditUser = function (data) {
        layer.open({
            type: 2,
            title: ['编辑用户', 'background-color: #1E9FFF; color: #ffffff'],
            area: ['500px', '390px'],
            content: '/user/edit?id=' + data.id,
            //是否允许拉伸
            resize: false,
            //遮罩
            shade: 0.5,
            //是否点击遮罩关闭
            shadeClose: true,
            //层销毁后触发的回调
            end: function () {
                layer.load(2);
                MgrUser.search();
            }
        });
    };

    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    MgrUser.onDeleteUser = function (data) {
        layer.open({
            title: ['删除用户', 'color: #FF5722'],
            btn: ['是', '否'],
            btnAlign: 'c',
            content: '是否删除用户?',
            yes: function () {
                ajax_hasData('/user/delete', 'post', {id: data.id}, function (rest) {
                    layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
                })
            },
            //层销毁后触发的回调
            end: function () {
                layer.load(2);
                MgrUser.search();
            }
        });
    };

    /**
     * 分配角色
     */
    MgrUser.roleAssign = function (data) {
        layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'],
            //是否点击遮罩关闭
            shadeClose: true,
            content: '/user/role_assign?id=' + data.id,
            end: function () {
                layer.load(2);
                MgrUser.search();
            }
        });
    }

    /**
     * 重置密码
     * @param data
     */
    MgrUser.resetPassword = function (data) {
        layer.open({
            title: ['重置密码', 'color: #FF5722'],
            btn: ['是', '否'],
            btnAlign: 'c',
            content: '是否重置密码?',
            yes: function () {
                ajax_hasData('/user/reset', 'post', {id: data.id}, function (rest) {
                    layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
                })
            }
        });
    };

    // 工具条点击事件
    table.on('tool(' + MgrUser.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrUser.onEditUser(data);
        } else if (layEvent === 'delete') {
            MgrUser.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            MgrUser.roleAssign(data);
        } else if (layEvent === 'reset') {
            MgrUser.resetPassword(data);
        }
    });

});
