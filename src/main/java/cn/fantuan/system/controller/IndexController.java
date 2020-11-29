package cn.fantuan.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }
    //首页
    @RequestMapping("/welcome")
    public String welcome() {
        return "/subsidiary/welcome";
    }

    //登录页面
    @RequestMapping("/login")
    public String logo() {
        return "/subsidiary/login";
    }
}