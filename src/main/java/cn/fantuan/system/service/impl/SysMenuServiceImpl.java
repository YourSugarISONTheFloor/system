package cn.fantuan.system.service.impl;

import cn.fantuan.system.entities.SysMenu;
import cn.fantuan.system.service.SysMenuService;
import cn.fantuan.system.util.TreeUtil;
import cn.fantuan.system.vo.MenuVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Override
	public Map<String, Object> menu() {
		Map<String, Object> map = new HashMap<>(16);
		Map<String, Object> home = new HashMap<>(16);
		Map<String, Object> logo = new HashMap<>(16);
		List<SysMenu> menuList = findAllByStatusOrderBySort(true);
		List<MenuVo> menuInfo = new ArrayList<>();
		for (SysMenu e : menuList) {
			MenuVo menuVO = new MenuVo();
			menuVO.setId(e.getKey().getId());
			menuVO.setPid(e.getPid());
			menuVO.setHref(e.getKey().getHref());
			menuVO.setTitle(e.getKey().getTitle());
			menuVO.setIcon(e.getIcon());
			menuVO.setTarget(e.getTarget());
			menuInfo.add(menuVO);
		}
		map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));
		home.put("title", "首页");
		home.put("href", "/page/welcome-1");//控制器路由,自行定义
		logo.put("title", "后台管理系统");
		logo.put("image", "/static/images/back.jpg");//静态资源文件路径,可使用默认的logo.png
		map.put("homeInfo", "{title: '首页',href: '/ruge-web-admin/page/welcome.html'}}");
		map.put("logoInfo", "{title: 'RUGE ADMIN',image: 'images/logo.png'}");
		return map;
	}

	private List<SysMenu> findAllByStatusOrderBySort(boolean b) {
		return null;
	}
}