package cn.fantuan.system.modular.controller.basics;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.util.code.CodeImg;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.email.SendEmailServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
	private static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
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
	@GetMapping("/forgetPassword/getCode")
	@ResponseBody
	public Integer getCode(String user, HttpSession session) {
		if (user.matches(EMAIL)) {
			session.setAttribute("session_EmailCode", sendEmailServlet.sendEmail(user));
		} else {
			System.out.println("手机发送验证码");
		}
		return 0;
	}

	//判断验证码是否正确
	@RequestMapping("/forgetPassword/ifCode")
	@ResponseBody
	public Integer ifCode(String code, HttpSession session) {
		if (session.getAttribute("session_EmailCode") != null) {
			if (!code.equals(session.getAttribute("session_EmailCode").toString())) {
				return 408;
			} else {
				return 409;
			}
		}
		return 408;
	}

	//根据用户输入的用户名或密码进行查询
	@PostMapping("/logging")
	@ResponseBody
	public Object logging(String username, String password, String clod, String choice, HttpSession session, Model model) {
		//choice为on代表选中保持登录
		//choice为null代表未选中保持登录

		//判断验证码
		if (session.getAttribute("session_validatecode") != null) {
			if (!clod.equalsIgnoreCase(session.getAttribute("session_validatecode").toString())) {
				//返回提示验证码错误
				return new CommonResult(ErrorCode.CAPTCHA_ERROR);
			}
		}
//		return loginService.getUserTo(username,password);

		//使用Shiro编写认证操作
		//获取Subject
		Subject subject = SecurityUtils.getSubject();

		//封装用户数据
		AuthenticationToken token = new UsernamePasswordToken(username, password);

		System.out.println("choice = " + choice);
		//如果开启了记住我功能
		if ("on".equals(choice)) {
			((UsernamePasswordToken) token).setRememberMe(true);
		} else {
			((UsernamePasswordToken) token).setRememberMe(false);
		}

		//执行登录操作
		try {
			//login()方法没有返回值，只能通过异常判断是否登陆成功
			subject.login(token);
			//登陆成功
			return new CommonResult(SuccessCode.SUCCESS, token);
		} catch (UnknownAccountException e) {
			//账号不存在
			return new CommonResult(ErrorCode.NO_THIS_USER);
		} catch (IncorrectCredentialsException e) {
			//密码错误
			return new CommonResult(ErrorCode.ERROR_PASSWORD);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "";
		}
	}

	//忘记密码中的跟新密码
	@PostMapping("/forgetPassword/updatePassword")
	@ResponseBody
	public Object updatePassword(String password, String username) {
		return loginService.updatePassword(password, username);
	}

	//用户注册
	@PostMapping("/registered/addUser")
	@ResponseBody
	public Object addUser(String username, String password, String name) {
		return loginService.addUser(username, password, name);
	}

	//获取用户信息
	@GetMapping("/user")
	@ResponseBody
	public Object user(HttpServletRequest request) {
		String token = request.getHeader("token");
		return loginService.look(token);
	}
}