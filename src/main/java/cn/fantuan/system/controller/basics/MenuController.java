package cn.fantuan.system.controller.basics;

import cn.fantuan.system.entities.SysMenu;
import cn.fantuan.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private SysMenuService sysMenuService;

	//初始化接口
	@GetMapping("")
	@ResponseBody
	public Map<String, Object> menu() {
		return sysMenuService.menu();
	}

	//菜单管理接口
	@RequestMapping("/manager")
	public String menuManager() {
		return "/subsidiary/menu";
	}

	//菜单列表
	@GetMapping("/getMenu")
	@ResponseBody
	public Object getMenu(){
		return sysMenuService.getMenu();
	}

	//添加菜单视图
	@GetMapping("/add")
	public String menuAdd(){
		return "/subsidiary/table/menu_add";
	}

	//获取菜单树
	@GetMapping("/commonTree")
	public String commonTree(){
		return "/subsidiary/table/menu_tree";
	}

	//判断父级菜单是否存在
	@PostMapping("/hasParent")
	@ResponseBody
	public Object hasParent(Integer pid){
		return sysMenuService.hasParent(pid);
	}

	//添加菜单
	@PostMapping("/addMenu")
	@ResponseBody
	public Object addMenu(SysMenu sysMenu){
		return sysMenuService.addMenu(sysMenu);
	}
}
