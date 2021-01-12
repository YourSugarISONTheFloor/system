layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treetable = layui.treetable;

    /**
     * 系统管理--菜单管理
     */
    var Menu = {
        tableId: '#munu-table',    //表格id
        url: '/menu/getMenu'
    };

    /**
     * 初始化表格的列
     */
    Menu.initColumn = function () {
        return [[
            {type: 'numbers', title: '序号', fixed: 'left'},
            {field: 'title', minWidth: 200, title: '权限名称'},
            {field: 'id', align: 'center', title: '权限标识'},
            {field: 'href', title: '请求URL'},
            {
                field: 'sort', width: 80, align: 'center', title: '排序号', templet: function (d) {
                    return d.sort == 0 ? "" : d.sort
                }
            },
            {templet: '#status', width: 120, align: 'center', title: '状态', fixed: 'right'},
            {
                field: 'isMenu', width: 80, align: 'center', templet: function (d) {
                    if (d.pid == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if (d.isMenu == 1) {
                        return '<span class="layui-badge layui-bg-cyan">按钮</span>';
                    } else if (d.isMenu == 0) {
                        return '<span class="layui-badge-rim layui-bg-green">菜单</span>';
                    }

                }, title: '类型', fixed: 'right'
            },
            {templet: '#auth-state', width: 120, align: 'center', title: '操作', fixed: 'right'}
        ]]
    };

    // 渲染表格
    layer.load(2);
    var menuTable = function () {
        return treetable.render({
            //树形图标显示在第几列
            treeColIndex: 1,
            //最上级的父级id
            treeSpid: 0,
            //id字段的名称
            treeIdName: 'id',
            //父级节点字段
            treePidName: 'pid',
            //是否默认折叠
            treeDefaultClose: false,
            //绑定的表格id
            elem: Menu.tableId,
            url: Menu.url,
            page: false,
            cols: Menu.initColumn(),
            //数据渲染完的回调
            done: function () {
                layer.closeAll('loading');
            }
        });
    };
    menuTable();
    /**
     * 弹出添加菜单对话框
     */
    Menu.openAddMenu = function () {
        layer.open({
            type: 2,
            title: ['添加菜单', 'background-color: #1E9FFF; color: #ffffff'],
            area: ['500px', '440px'],
            content: '/menu/add',
            //是否允许拉伸
            resize: false,
            //遮罩
            shade: 0.5,
            //是否点击遮罩关闭
            shadeClose: true,
            //层销毁后触发的回调
            end: function () {
                layer.load(2);
                menuTable();
            }
        });
    };

    /**
     * 点击编辑菜单按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Menu.onEditMenu = function (data) {

    };

    /**
     * 点击删除菜单按钮
     *
     * @param data 点击按钮时候的行数据
     */
    Menu.onDeleteMenu = function (data) {

    };

    //全部展开
    $('#btn-expand').click(function () {
        treetable.expandAll('#munu-table');
    });

    //全部折叠
    $('#btn-fold').click(function () {
        treetable.foldAll('#munu-table');
    });

    //添加菜单
    $('#btn-add').click(function () {
        Menu.openAddMenu();
    });

    //监听工具条
    table.on('tool(munu-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') {
            //删除菜单
            Menu.onDeleteMenu(data)
        } else if (layEvent === 'edit') {
            //编辑菜单
            Menu.onEditMenu(data)
        } else if (layEvent === 'status') {
            //更改菜单状态
            layer.msg('状态' + data.id);
        }
    });
});