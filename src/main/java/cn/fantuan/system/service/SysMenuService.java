package cn.fantuan.system.service;

import cn.fantuan.system.entities.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysMenuService extends IService<SysMenu> {
	//获取所有菜单
	Map<String, Object> menu();

	Object getMenu();
	//判断父级菜单是否存在
	Object hasParent(Integer pid);
	//添加菜单
	Object addMenu(SysMenu sysMenu);

}
