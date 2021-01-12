package cn.fantuan.system.controller.basics;

import cn.fantuan.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

	@Autowired
	LoginService loginService;

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
	public String logout(HttpServletRequest request) {
		loginService.logout(String.valueOf(request.getHeaders("token")));
		//redirect重定向:参数会丢失
		//Forward转发：参数不会丢失
		return "forward:/login";
	}
}