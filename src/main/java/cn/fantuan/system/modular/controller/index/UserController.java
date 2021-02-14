package cn.fantuan.system.modular.controller.index;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import cn.fantuan.system.modular.service.DeptServer;
import cn.fantuan.system.modular.service.RoleServer;
import cn.fantuan.system.modular.service.UserServer;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.core.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user/")
public class UserController {

	private final String PATH = "/subsidiary/page/";

	@Autowired
	private UserServer userServer;

	@Autowired
	private DeptServer deptServer;

	@Autowired
	private RoleServer roleServer;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 初始化权限管理接口
	 *
	 * @return
	 */
	@GetMapping("role")
	public String role() {
		return PATH + "user_role";
	}

	/**
	 * 获取默认数据
	 *
	 * @param layuiPage
	 * @param name      用户输入的搜索
	 * @param deptId    部门ID
	 * @return
	 */
	@GetMapping("getAll")
	@ResponseBody
	public LayuiPageInfo getAll(LayuiPage layuiPage, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "deptId", required = false) List deptId) {
		return userServer.getAll(layuiPage, name, deptId);
	}

	//获取所有角色
	@GetMapping("getRole")
	@ResponseBody
	public CommonResult getRole() {
		return roleServer.getRole();
	}

	//获取所有部门增加一个默认的
	@GetMapping("getDeptAdd")
	@ResponseBody
	public CommonResult getDeptAdd() {
		return deptServer.getDeptAdd();
	}

	//获取所有部门
	@GetMapping("getDeptAll")
	@ResponseBody
	public CommonResult getDeptAll() {
		return deptServer.getDept();
	}

	//部门树视图
	@GetMapping("getDeptTree")
	public String getDeptTree() {
		return "/subsidiary/table/user_role/user_role_tree";
	}

	//更改用户状态
	@PostMapping("editState")
	@ResponseBody
	public CommonResult editState(@RequestParam Map map) {
		return userServer.editState(map);
	}

	//编辑用户视图
	@GetMapping("edit")
	public String edit(Long id, Map<String, Object> model) {
		User user = userServer.getById(id);
		Map<String, Object> map = ValidateUtil.object2Map(user);
		//获取该用户对应的部门及角色信息
		Map<String, Object> hmget = redisUtil.hmget(RedisConst.dept + user.getId());
		map.put("deptName", hmget.get("deptName"));
		map.put("roleList", hmget.get("roleList"));
		map.put("roleNames", hmget.get("roleNames"));

		model.put("user", map);
		return "/subsidiary/table/user_role/user_role_edit";
	}

	//更改用户
	@PostMapping("editUser")
	@ResponseBody
	public CommonResult editUser(@RequestParam Map map) {
		return userServer.editUser(map);
	}

	//删除用户
	@PostMapping("delete")
	@ResponseBody
	public CommonResult delete(Long id) {
		return userServer.delete(id);
	}

	//用户角色分配视图
	@GetMapping("role_assign")
	public String role_assign(Long id, Map<String, Object> model) {
		//获取该用户对应的部门及角色信息
		Map<String, Object> hmget = redisUtil.hmget(RedisConst.role + id);
		model.put("user", id);
		model.put("user_role_list", hmget.get("role_list"));
		return "/subsidiary/table/user_role/user_role_assign";
	}

	//用户角色分配
	@PostMapping("setRole")
	@ResponseBody
	public CommonResult setRole(@RequestParam Map map) {
		return userServer.setRole(map);
	}

	//重置用户密码
	@PostMapping("reset")
	@ResponseBody
	public CommonResult reset(Long id) {
		return userServer.reset(id);
	}

	/**
	 * 初始化部门管理接口
	 *
	 * @return
	 */
	@RequestMapping("dept")
	public String dept() {
		return null;
	}

	/**
	 * 初始化角色管理接口
	 *
	 * @return
	 */
	@RequestMapping("part")
	public String part() {
		return null;
	}
}
