package cn.fantuan.system.service;

import cn.fantuan.system.entities.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysMenuService extends IService<SysMenu> {
	//获取所有菜单
	Map<String, Object> menu();
}
