package cn.fantuan.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    //后台首页
    @RequestMapping("/welcome")
    public String welcome() {
        return "/subsidiary/welcome-1";
    }
    //登录页面
    @RequestMapping("/login")
    public String logo() {
        return "/subsidiary/login";
    }
}
