var ajax_data = {
    //默认是get请求
    type: 'GET',
    //默认是异步
    async: true,
    //请求路径
    url: '',
    //默认数据是空
    data: '',
    dataType:'text',
    error:function (a) {
        layer.msg("发生错误了！")
    }
}
//自定义方法
var MyMethod = {
    language: function (a) {
        if (sessionStorage.getItem("language") === null) {
            window.location = a;
        } else {
            window.location = a + '?language=' + sessionStorage.getItem("language");
        }
    },
    ajax: function () {
        console.log("data：")
        console.log(ajax_data.data)
        console.log("url：")
        console.log(ajax_data.url)
        $.ajax(ajax_data);
    }
}