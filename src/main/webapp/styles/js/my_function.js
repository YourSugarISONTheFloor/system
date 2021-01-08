//ajax无参数请求
function ajax_notData(url, type, success) {
    $.ajax({
        url: url,
        type: type,
        dataType: "json",
        headers: {"token": localStorage.token},
        success: success,
        error: function (xhr) {
            layer.msg("发生错误了：" + xhr.status, {icon: 2, time: 3 * 1000, shift: 6});
        }
    });
}

//ajax有参数请求
function ajax_hasData(url, type, data, success, dataType, async) {
    type = type == undefined ? "GET" : type;
    data = data == undefined ? null : data;
    success = success == undefined ? null : success;
    dataType = dataType == undefined ? "json" : dataType;
    async = async == undefined ? true : async;
    $.ajax({
        url: url,
        type: type,
        async: async,
        data: data,
        dataType: dataType,
        headers: {"token": localStorage.token},
        success: success,
        error: function (xhr) {
            layer.msg("发生错误了：" + xhr.status, {icon: 2, time: 3 * 1000, shift: 6});
        }
    });
}

//自定义方法
var MyMethod = {
    language: function (a) {
        if (sessionStorage.getItem("language") === null) {
            window.location.href = a;
        } else {
            window.location.href = a + '?language=' + sessionStorage.getItem("language");
        }
    }
}