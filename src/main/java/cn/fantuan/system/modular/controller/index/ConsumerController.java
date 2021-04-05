package cn.fantuan.system.modular.controller.index;

import cn.fantuan.system.modular.service.UserServer;
import cn.fantuan.system.modular.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consumer/")
public class ConsumerController {
	private final String PATH = "/subsidiary/page/";

	@Autowired
	private UserServer userServer;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 初始化用户树接口
	 *
	 * @return
	 */
	@GetMapping("tree")
	public String role() {
		return PATH + "tree";
	}

	/**
	 * 用户树的数据
	 */
	@RequestMapping("treeData")
	@ResponseBody
	public Object treeData(String name, Integer tree) {
		return userServer.getTree(name, tree);
	}
}
