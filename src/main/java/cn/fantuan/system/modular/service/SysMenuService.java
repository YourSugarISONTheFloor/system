package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysMenuService extends IService<SysMenu> {
	//初始化菜单接口
	Map<String, Object> menu();

	//获取所有菜单
	Map<String, Object> menuAll();

	Object getMenu();

	//判断父级菜单是否存在
	Object hasParent(Integer pid);

	//添加菜单
	Object addMenu(SysMenu sysMenu);

	//更改菜单状态
	Object editState(Map map);

	//删除菜单
	Object del(Integer id);

	//修改菜单
	Object editMenu(SysMenu sysMenu);
}
