package cn.fantuan.system.util;

/**
 * 定义邮箱内容
 */
public class HtmlText {
    //返回html页面携带6为数的验证码
    public static String html(String code) {
        String html = "Email邮箱验证<br/>您在<span style='color:#DC143C;'><b>\"灰姑娘的水晶鞋\"</b></span>的验证码为：<br/><h3 style='color:#00CED1;'>" + code + "</h3><br/>";
        return html;
    }
}
