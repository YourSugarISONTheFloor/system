package cn.fantuan.system.modular.controller.index;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import cn.fantuan.system.modular.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/user/")
public class UserController {

	private final String PATH = "/subsidiary/page/";

	@Autowired
	private UserServer userServer;

	//初始化用户管理接口
	@GetMapping("role")
	public String role() {
		return PATH + "user_role";
	}

	//获取默认数据
	@GetMapping("getAll")
	@ResponseBody
	public LayuiPageInfo getAll(LayuiPage layuiPage) {
		System.out.println(userServer.getAll(layuiPage));
		return userServer.getAll(layuiPage);
	}

	//更改用户状态
	@PostMapping("editState")
	@ResponseBody
	public CommonResult editState(@RequestParam Map map) {
		return userServer.editState(map);
	}

}
