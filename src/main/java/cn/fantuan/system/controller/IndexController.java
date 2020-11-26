package cn.fantuan.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/welcome")
    public String welcome() {
        return "/subsidiary/welcome-1";
    }

    @RequestMapping("/login")
    public String logo() {
        return "/subsidiary/login";
    }
}
