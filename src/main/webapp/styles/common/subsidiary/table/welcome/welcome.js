layui.use(['layer', 'miniTab', 'echarts'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        miniTab = layui.miniTab,
        echarts = layui.echarts;

    miniTab.listen();

    /**
     * 查看公告信息
     **/
    $('body').on('click', '.layuimini-notice', function () {
        var title = $(this).children('.layuimini-notice-title').text(),
            noticeTime = $(this).children('.layuimini-notice-extra').text(),
            content = $(this).children('.layuimini-notice-content').html();
        var html = '<div style="padding:15px 20px; text-align:justify; line-height: 22px;border-bottom:1px solid #e2e2e2;background-color: #2f4056;height:100%;color: #ffffff">\n' +
            '<div style="text-align: center;margin-bottom: 20px;font-weight: bold;border-bottom:1px solid #718fb5;padding-bottom: 5px"><h4 class="text-danger">' + title + '</h4></div>\n' +
            '<div style="font-size: 12px">' + content + '</div>\n' +
            '</div>\n';
        parent.layer.open({
            type: 1,
            title: '系统公告' + '<span style="float: right;right: 1px;font-size: 12px;color: #b1b3b9;margin-top: 1px">' + noticeTime + '</span>',
            area: '300px;',
            shade: 0.4,
            shadeClose: true,
            id: 'layuimini-notice',
            btn: ['确定'],
            btnAlign: 'c',
            moveType: 1,
            content: html
        });
    });

    /**
     * 获取用户数，团队数，合伙人数
     */
    $.ajax({
        url: "/welcome/getCountUser",
        success(rest) {
            //用户数
            $('.user').html(rest.data);
        }
    });

    //用户 user
    //迟到 late
    //早退 ex-Leave
    //请假 leave

    $(function () {
        Highcharts.setOptions({ //全局配置参数是针对所有 Highcharts 图表生效的配置，所以只能通过 Highcharts.setOption 函数来配置
            chart: { //关于图表区和图形区的参数及一般图表通用参数。
                events: { //图表相关的事件
                    //函数中的 this 代表着 Chart 对象。在通过点击打印菜单项或手动调用 Chart.print 方法打印图表之前触发，该事件依赖导出功能模块。
                    beforePrint: function () {
                        this.oldhasUserSize = this.hasUserSize;
                        this.resetParams = [this.chartWidth, this.chartHeight, false];
                        this.setSize(600, 400, false);
                    },
                    //函数中的 this 代表着 Chart 对象。在通过点击打印菜单项或手动调用 Chart.print 方法打印图表之前触发，该事件依赖导出功能模块。
                    afterPrint: function () {
                        this.setSize.apply(this, this.resetParams);
                        this.hasUserSize = this.oldhasUserSize;
                    },

                }
            },
            lang: {
                months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
                contextButtonTitle: "导出按钮的标题",
                downloadJPEG: "导出为JPEG图片",
                downloadPDF: "导出为PDF文件",
                downloadPNG: "导出为PNG图片",
                downloadSVG: "导出为SVG图片",
                printChart: "打印图表",
                noData: "暂无数据哟"
            }
        });
    });

    /**
     *市场交易与糖果走势图报表
     */
    $.ajax({
        url: "/welcome/getLastSevenDays",
        //async: false,//设置为同步请求，执行完再执行
        success: function (rest) {
            var data = rest.data;
            //迟到数量 late
            $('.late').html(data.series[0].date[6]);
            //缺勤 notAttendance
            $('.notAttendance').html(data.series[2].date[6]);
            //请假 leave
            $('.leave').html(data.series[3].date[6]);
            var chart = Highcharts.chart('echarts-records', { //该函数返回的对象就是图表对象。
                title: { //图表标题对象
                    text: '员工考勤情况走势图'
                },
                subtitle: { //副标题对象
                    text: '近七天相关走势'
                },
                yAxis: { //当前图表的 Y轴对象数组
                    title: {
                        text: '数量'
                    }
                },
                xAxis: {
                    categories: data.categories,
                    title: {
                        enabled: true,
                        text: '记录相关天数',
                        style: {
                            fontWeight: 'normal'
                        }
                    }
                },
                legend: { //图例说明是包含图表中数列标志和名称的容器。各数列（若饼图则为各点）由图例中的标志和名称表示。
                    layout: 'vertical', //图例数据项的布局。布局类型： "horizontal" 或 "vertical" 即水平布局和垂直布局 默认是：horizontal
                    align: 'right', //设定图例在图表区中的水平对齐方式，合法值有left，center 和 right。
                    verticalAlign: 'middle' //设定图例在图表区中的垂直对齐方式，合法值有 top，middle 和 bottom。垂直位置可以通过 y 选项做进一步设定
                },
                series: [{
                    data: data.series[0].date,
                    name: data.series[0].name //数据列的名字，将会被显示在图例、数据提示框等中

                }, {
                    name: data.series[1].name,
                    data: data.series[1].date
                }, {
                    name: data.series[2].name,
                    data: data.series[2].date
                }, {
                    name: data.series[3].name,
                    data: data.series[3].date
                }, {
                    name: data.series[4].name,
                    data: data.series[4].date
                }]
                ,
                responsive: { //通过设定不同的响应规则来实现对图表在不同尺寸下的响应（图表中的每个组件都支持响应）。
                    rules: [{ //响应规则数组，这些响应规则是依次往下执行的（即下标越大的规则比前面的规则后执行）
                        condition: { //响应条件，即在什么条件下响应对应的规则。
                            maxWidth: 500 //响应的最大宽度，即图表宽度小于这个值时规则生效。
                        },
                        chartOptions: { //响应规则对应的图表配置，该配置支持全部的图表配置，当响应规则生效时，本属性将覆盖当前图表的配置
                            legend: { //图例说明是包含图表中数列标志和名称的容器。各数列（若饼图则为各点）由图例中的标志和名称表示。
                                layout: 'horizontal',
                                align: 'center',
                                verticalAlign: 'bottom'
                            }
                        }
                    }]
                }
            });
        }
    });


    /**
     *用户活跃量报表
     */
    $.ajax({
        url: "/welcome/getActiveUser",
        success: function (rest) {
            var data = rest.data;
            $('.active_user_ratio').html(data.count);
            $('.ex_ratio').html(data.radio);
            if (data.radio.indexOf("增长") >= 0) {
                $('.ratio').addClass("fa-long-arrow-up icon-tip").removeClass("fa-long-arrow-down icon-gift");
            } else {
                $('.ratio').addClass("fa-long-arrow-down icon-gift").removeClass("fa-long-arrow-up icon-tip");
            }
        }
    });

});