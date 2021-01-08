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
    //携带语言的路径
    language: function (a) {
        if (sessionStorage.getItem("language") === null) {
            window.location.href = a;
        } else {
            window.location.href = a + '?language=' + sessionStorage.getItem("language");
        }
    },
    //设置cookie的值，以及过期时间
    setCookie: function (name, value, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = name + "=" + value + "; " + expires;
    },
    //得到cookie的值
    getCookie: function (name) {
        var name = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    },
    //删除指定cookie的值
    removeCookie(name) {
        MyMethod.setCookie(name, "", -1);
    },
    //删除全部的cookie值
    clearCookie: function () {
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
        if (keys) {
            for (var i in keys) {
                document.cookie = keys[i] + '=0;expires=' - 1;
            }
        }
    },
    //获取当前时间，格式YYYY-MM-DD
    getNowFormatDate: function () {
        var date = new Date();
        var year = date.getFullYear();                                                              //获取年限
        var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();       //获取月份
        var strDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();                  //获取天
        var strHours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();              //获取小时
        var strMinutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();      //获取分钟
        var strSeconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();      //获取秒数

        //显示的格式
        var nowData = year + "-" + month + "-" + strDate + " " + strHours + ":" + strMinutes + ":" + strSeconds;
        return nowData;
    },
    //毫秒级别的时间戳转为时间格式yyyy-MM-dd HH:mm:ss
    getDateTimeToDate: function (a) {
        var date = typeof a == "undefined" ? new Date() : typeof a == "number" ? new Date(a) : new Date(parseInt(a));
        return date.getFullYear()
            + '-' + ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth())
            + '-' + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
            + ' ' + (date.getHours() < 10 ? "0" + date.getHours() : date.getHours())
            + ':' + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes())
            + ':' + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
    }
}