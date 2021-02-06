package cn.fantuan.system.modular.controller.basics;

import cn.fantuan.system.modular.entities.SysMenu;
import cn.fantuan.system.modular.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private SysMenuService sysMenuService;

	//初始化菜单接口
	@GetMapping("")
	@ResponseBody
	public Map<String, Object> menu(Long id) {
		return sysMenuService.menu(id);
	}

	//获取所有菜单
	@GetMapping("/all")
	@ResponseBody
	public Map<String, Object> menuAll() {
		return sysMenuService.menuAll();
	}

	//菜单管理接口
	@RequestMapping("/manager")
	public String menuManager() {
		return "/subsidiary/page/menu";
	}

	//菜单列表
	@GetMapping("/getMenu")
	@ResponseBody
	public Object getMenu() {
		return sysMenuService.getMenu();
	}

	//添加菜单视图
	@GetMapping("/add")
	public String menuAdd() {
		return "/subsidiary/table/menu/menu_add";
	}

	//获取菜单树
	@GetMapping("/commonTree")
	public String commonTree() {
		return "/subsidiary/table/menu/menu_tree";
	}

	//判断父级菜单是否存在
	@PostMapping("/hasParent")
	@ResponseBody
	public Object hasParent(Integer pid) {
		return sysMenuService.hasParent(pid);
	}

	//添加菜单
	@PostMapping("/addMenu")
	@ResponseBody
	public Object addMenu(SysMenu sysMenu) {
		return sysMenuService.addMenu(sysMenu);
	}

	//菜单状态更改
	@PostMapping("/editState")
	@ResponseBody
	public Object editState(@RequestParam Map map) {
		return sysMenuService.editState(map);
	}

	//删除菜单
	@PostMapping("/del")
	@ResponseBody
	public Object del(Integer id) {
		System.out.println("id = " + id);
		return sysMenuService.del(id);
	}

	//编辑菜单视图
	@GetMapping("/edit")
	public Object edit(Integer id, Map<String, Object> model) {
		SysMenu sysMenuById = sysMenuService.getById(id);
		model.put("sysMenu", sysMenuById);
		//菜单的父ID不为0时
		if (sysMenuById.getPid() != 0) {
			String title = sysMenuService.getById(sysMenuById.getPid()).getTitle();
			model.put("pTitle", title);
		} else {
			model.put("pTitle", "顶级菜单");
		}
		return "/subsidiary/table/menu/menu_edit";
	}

	//更改菜单
	@PostMapping("/editMenu")
	@ResponseBody
	public Object editMenu(SysMenu sysMenu) {
		return sysMenuService.editMenu(sysMenu);
	}
}
