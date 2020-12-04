package cn.fantuan.system.controller;

import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.service.LoginService;
import cn.fantuan.system.util.CodeImg;
import cn.fantuan.system.util.SendEmailServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    private static  final  String EMAIL="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String PATH = "/subsidiary/";
    @Autowired
    LoginService loginService;
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
    @GetMapping("/getCode")
    @ResponseBody
    public Integer getCode(String user,HttpSession session) {
        if(user.matches(EMAIL)){
            session.setAttribute("session_EmailCode",sendEmailServlet.sendEmail(user));
        }else{
            System.out.println("手机发送验证码");
        }
        return 0;
    }

    //判断验证码是否正确
    @GetMapping("/ifCode")
    @ResponseBody
    public Integer ifCode(String code,HttpSession session){
        System.out.println("ifCode");
        if (session.getAttribute("session_EmailCode") != null) {
            if (!code.equals(session.getAttribute("session_EmailCode").toString())) {
                return 0;
            }else{
                return 1;
            }
        }
        return  0;
    }

    //根据用户输入的用户名或密码进行查询
    @GetMapping("/logging")
    @ResponseBody
    public Object logging(String username, String password, String clod, HttpSession session) {
        if (session.getAttribute("session_validatecode") != null) {
            if (!clod.equalsIgnoreCase(session.getAttribute("session_validatecode").toString())) {
                return new CommonResult(2, "验证码错误", null);
            }
        }
        return loginService.getUser(username, password);
    }

    //忘记密码中的跟新密码
    @GetMapping("/updatePassword")
    @ResponseBody
    public Integer updatePassword(String password,String username){
        return loginService.updatePassword(password,username);
    }
}