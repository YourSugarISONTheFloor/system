package cn.fantuan.system.modular.controller.basics;

import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	@Autowired
	private RedisUtil redisUtil;

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
		String token = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			//cookie不为空
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
				}
			}
		}
		//输出用户登录时保存的token
		System.out.println(token);
		redisUtil.select(1);
		//从缓存库中删除对应的token
		redisUtil.del(token);
		redisUtil.select(0);
//		request.getCookies().getName().equals("token");
		loginService.logout(token);
		//redirect重定向:参数会丢失
		//Forward转发：参数不会丢失
		return "forward:/login";
	}
}