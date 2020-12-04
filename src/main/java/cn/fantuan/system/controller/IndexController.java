package cn.fantuan.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
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

    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("session_user");
        return "redirect:/login";
    }
}