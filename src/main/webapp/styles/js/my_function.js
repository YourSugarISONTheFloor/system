//自定义方法
var MyMethod = {
    language: function (a) {
        if (sessionStorage.getItem("language") === null) {
            window.location = a;
        } else {
            window.location = a + '?language=' + sessionStorage.getItem("language");
        }
    }
}