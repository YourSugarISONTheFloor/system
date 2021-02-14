layui.use(['form', 'miniTab', 'util'], function () {
        var form = layui.form;
        var layer = layui.layer;
        var util = layui.util;
        var miniTab = layui.miniTab;

        miniTab.listen();

        layer.load(0);
        ajax_hasData("getTime", 'POST', {id: JSON.parse(localStorage.user).id}, function (rest) {
            console.log(rest)
            //开始时间
            var serverTime = new Date().getTime();

            //倒计时工具
            util.countdown(rest.data.time, serverTime, function (date, serverTime, timer) {
                var str = date[0] + '天' + date[1] + '时' + date[2] + '分' + date[3] + '秒';
                layui.$('#row-two span').html(str);
            });

            if (serverTime < rest.data.time) {
                if (rest.data.state != 0) {
                    $(".circle_in").css("color", "#d2d2d2");
                    $(".circle_in p").html("已签到");
                } else {
                    //改变鼠标样式
                    document.getElementById("circle").style.cursor = "pointer";
                    //点击签到
                    $("#circle").on('click', function () {
                        clickBtn();
                    })
                }
            } else {
                $(".circle_in").css("color", "#d2d2d2");
                $(".circle_in p").html("签到已结束");
            }
            layer.closeAll()
        });


        //点击签到
        var clickBtn = function () {
            //点击签到-定位成功后的签到
            layer.open({
                type: 1,
                title: '签到范围',
                area: ['800px', '600px'],
                shade: 0.4,
                shadeClose: true,
                id: 'Range',
                btn: ['签到', '取消'],
                btnAlign: 'c',
                moveType: 1,
                content: '<div id="container" style=\'width:100%; height:100%;\'></div>',
                success: function (layero) {
                    getMap();
                },
                btn1: function (index, layero) {
                    if (1 == 1) {
                        layer.open({
                            title: '',
                            closeBtn: 0,
                            content: '范围外签到确定要签到吗？',
                            btn: ['继续签到', '重新定位'],
                            yes: function (index, layero) {
                                ajax_hasData("report", 'POST', {id: JSON.parse(localStorage.user).id}, function (rest) {
                                    layer.msg("签到成功", {icon: 1, time: 3 * 1000, shift: 6})
                                }, null, false)
                            },
                            btn2: function (index, layero) {

                            }
                        });
                    }
                },
                end: function () {
                    layer.closeAll();
                }
            })
        }

        //定义地图
        var getMap = function () {
            var map = new AMap.Map('container', {
                center: [116.433322, 39.900256],
                zoom: 14,
                resizeEnable: true
            });

            //定位自己的位置
            AMap.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                    enableHighAccuracy: true,//是否使用高精度定位，默认:true
                    timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                    maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                    convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                    showButton: true,        //显示定位按钮，默认：true
                    buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                    buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                    showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                    showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                    panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                    zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                });

                map.addControl(geolocation);
                geolocation.getCurrentPosition(function (status, result) {
                    //判断是否定位成功
                    if (status == 'complete') {
                        onComplete(result)
                    } else {
                        onError(result)
                    }
                });
            })

            function onComplete(data) {
                // data是具体的定位信息
                console.log("定位信息成功")
                console.log(data)
            }

            function onError(data) {
                // 定位出错
                console.log("定位信息失败")
                console.log(data)
            }

            //高亮圆圈
            var circle = new AMap.Circle({
                center: [106.2846, 31.28460],
                radius: 1000, //半径
                borderWeight: 3,
                strokeColor: "#FF33FF",
                strokeOpacity: 1,
                strokeWeight: 6,
                strokeOpacity: 0.2,
                fillOpacity: 0.4,
                strokeStyle: 'dashed',
                strokeDasharray: [10, 10],
                // 线样式还支持 'dashed'
                fillColor: '#1791fc',
                zIndex: 50,
            })
            circle.setMap(map)
            // 缩放地图到合适的视野级别
            map.setFitView([circle])

            var circleMarker = new AMap.CircleMarker({
                center: [106.2846, 31.28460],
                radius: 10,//3D视图下，CircleMarker半径不要超过64px
                strokeColor: 'white',
                strokeWeight: 2,
                strokeOpacity: 0.5,
                fillColor: 'rgba(0,0,255,1)',
                fillOpacity: 0.5,
                zIndex: 10,
                bubble: true,
                cursor: 'pointer',
                clickable: true,
                zIndex: 100,
            })
            circleMarker.setMap(map)
        }
    }
)