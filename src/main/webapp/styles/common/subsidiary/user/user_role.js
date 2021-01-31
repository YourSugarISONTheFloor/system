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
        condition: {
            name: "",
            deptId: "",
            timeLimit: ""
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
            {field: 'roleNames', sort: true, title: '角色'},
            {field: 'deptName', sort: true, title: '部门'},
            {field: 'email', title: '邮箱'},
            {field: 'phone', title: '电话'},
            {field: 'status', templet: '#statusTpl', title: '状态', fixed: 'right', unresize: true},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280, fixed: 'right'}
        ]];
    };


    //表格初始渲染
    table.render({
        elem: '#' + MgrUser.tableId,
        url: '/user/getAll/',
        cols: MgrUser.initColumn(),
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        id: 'userTable'
    });
    

    //监听状态操作
    form.on('switch(state)', function (obj) {
        ajax_hasData('/user/editState', 'post', {id: this.value, status: obj.elem.checked}, function (rest) {
            layer.msg(rest.message, {icon: 1, time: 3 * 1000, shift: 6});
        })
    });

});
