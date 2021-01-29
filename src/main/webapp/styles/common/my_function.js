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
        // beforeSend: function () {
        //     //请求前的处理
        //     console.log("ajax请求前")
        //     layer.loading(1);
        // },
        //请求成功时处理
        success: success,
        //请求出错处理
        error: function (xhr) {
            var sessionStatus = xhr.getResponseHeader('sessionstatus');
            var msg = xhr.getResponseHeader('msg');
            if (sessionStatus == 'timeout') {
                layer.msg(msg, {icon: 2, time: 3 * 1000, shift: 6});
                setTimeout(function () {
                    MyMethod.language('/login');
                }, 2000);
            } else {
                layer.msg("发生错误了：" + xhr.status, {icon: 2, time: 3 * 1000, shift: 6});
            }
        },
        // complete: function () {
        //     //请求完成的处理
        //     console.log("ajax请求完成")
        //     layer.closeAll();
        // }
    });
}

//自定义方法
var MyMethod = {
    //获取用户信息
    getUser: function () {
        ajax_hasData('/user', null, null, function (rest) {
            //可以将任意的JavaScript值序列化成JSON字符串形式,存储在本地
            localStorage.user = JSON.stringify(rest.data);
            // $('#user_name').text(localStorage.user == undefined ? "临时用户" : JSON.parse(localStorage.user).name);
            //JSON.parse(localStorage.user)将JSON字符串转为对象
            //console.log(JSON.parse(localStorage.user).name)
        }, null, false)
    },
    //携带语言的路径
    language: function (url) {
        //获取浏览器存储的语言
        console.log("浏览器存储的语言：" + sessionStorage.language)
        if (sessionStorage.language === undefined) {
            window.location.href = url;
        } else {
            window.location.href = url + '?language=' + sessionStorage.language
        }
    },
    //设置cookie的值，以及过期时间,时间为天数
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