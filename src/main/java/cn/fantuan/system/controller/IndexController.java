package cn.fantuan.system.controller;

import cn.fantuan.system.service.LoginService;
import cn.fantuan.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class IndexController {

	@Autowired
	LoginService loginService;
	@Autowired
	private SysMenuService sysMenuService;

	//初始化接口
	@GetMapping("/menu")
	public Map<String, Object> menu() {
		return sysMenuService.menu();
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

	//退出登录
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		loginService.logout(String.valueOf(request.getHeaders("token")));
		return "redirect:/login";
	}
}