package cn.fantuan.system.controller;

import cn.fantuan.system.servlet.SendEmailServlet;
import cn.fantuan.system.uitil.CodeImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class LoginController {
    private static final String PATH = "/subsidiary/";
    //发送邮箱的处理器
    @Autowired
    SendEmailServlet sendEmailServlet;
    //获取验证码图片的处理器
    @Autowired
    CodeImg codeImg;

    //验证码图片
    @RequestMapping(value = "/codeImage", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] codeImage() throws IOException {
        return codeImg.getCodeImg();
    }

    //立即注册
    @RequestMapping("/registered")
    public String registered() {
        return PATH + "register";
    }

    //忘记密码
    @RequestMapping("/forgetPassword")
    public String forgetPassword() {
        return PATH + "forget";
    }

    //获取验证码
    @RequestMapping("/getCode")
    @ResponseBody
    public Integer getCode(String user) {
        sendEmailServlet.sendEmail(user);
        return 0;
    }
}