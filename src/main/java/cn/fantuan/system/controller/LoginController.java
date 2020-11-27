package cn.fantuan.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    //忘记密码
    @RequestMapping("forgetPassword")
    public String forgetPassword() {
        return "subsidiary/forget";
    }

    //获取验证码
    @RequestMapping("getCode")
    @ResponseBody
    public Integer getCode() {
        return 0;
    }
}