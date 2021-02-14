package cn.fantuan.system.modular.controller.basics;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.service.WelcomeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

	@Autowired
	LoginService loginService;

	@Autowired
	private WelcomeServer welcomeServer;

	//首页
	@RequestMapping("/welcome")
	public String welcome() {
		return "/subsidiary/welcome";
	}

	/**
	 * 首页用户数据
	 *
	 * @return
	 */
	@RequestMapping("/welcome/getCountUser")
	@ResponseBody
	public CommonResult getCountUser() {
		return welcomeServer.getCountUser();
	}

	/**
	 * 首页近7天的数据
	 *
	 * @return
	 */
	@RequestMapping("/welcome/getLastSevenDays")
	@ResponseBody
	public CommonResult getLastSevenDays() {
		return welcomeServer.getLastSevenDays();
	}

	/**
	 * 首页用户活跃数据
	 *
	 * @return
	 */
	@RequestMapping("/welcome/getActiveUser")
	@ResponseBody
	public CommonResult getActiveUser() {
		return welcomeServer.getActiveUser();
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
					//删除cookie中的token
					cookie.setMaxAge(0);
				}
			}
		}
		//输出用户登录时保存的token
		System.out.println(token);
//		request.getCookies().getName().equals("token");
		loginService.logout(token);
		//redirect重定向:参数会丢失
		//Forward转发：参数不会丢失
		return "forward:/login";
	}
}