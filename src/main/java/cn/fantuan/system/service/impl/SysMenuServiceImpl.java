package cn.fantuan.system.service.impl;

import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.DataResult;
import cn.fantuan.system.entities.SysMenu;
import cn.fantuan.system.mapper.SysMenuServiceMapper;
import cn.fantuan.system.service.SysMenuService;
import cn.fantuan.system.util.code.ErrorCode;
import cn.fantuan.system.util.code.SuccessCode;
import cn.fantuan.system.util.menu.TreeUtil;
import cn.fantuan.system.vo.MenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuServiceMapper, SysMenu> implements SysMenuService {
	@Override
	public Map<String, Object> menu() {
		//返回的菜单列表
		Map<String, Object> map = new HashMap<>(16);
		//home列表
		Map<String, Object> home = new HashMap<>(16);
		//logo列表
		Map<String, Object> logo = new HashMap<>(16);
		//获取所有激活的菜单数组：父ID为0和状态为激活的菜单
		List<SysMenu> menuList = this.list(new QueryWrapper<SysMenu>().eq("status", "1").or(m -> m.eq("pid", "0")));
		//child数组
		List<MenuVo> menuInfo = new ArrayList<>();
		for (SysMenu e : menuList) {
			MenuVo menuVO = new MenuVo();
			menuVO.setId(e.getId());
			menuVO.setPid(e.getPid());
			menuVO.setHref(e.getHref());
			menuVO.setTitle(e.getTitle());
			menuVO.setIcon(e.getIcon());
			menuVO.setTarget(e.getTarget());
			menuInfo.add(menuVO);
		}
		home.put("title", "首页");
		home.put("href", "/welcome");
		//logo标题
		logo.put("title", "后台管理系统");
		//logo图片地址
		logo.put("image", "/styles/images/logo.png");
		//logo链接地址
		logo.put("href", "");

		map.put("homeInfo", home);
		map.put("logoInfo", logo);
		map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));
		return map;
	}

	@Override
	public Object getMenu() {
		List list = this.list();
		return new DataResult(0, "", list.size(), list);
	}

	//判断父级菜单是否存在
	@Override
	public Object hasParent(Integer pid) {
		if (getOne(new QueryWrapper<SysMenu>().eq("id", pid)) == null) {
			return new CommonResult(ErrorCode.NOT_HAS_MENU, false);
		} else {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
	}

	//添加菜单
	@Override
	public Object addMenu(SysMenu sysMenu) {
		if (save(sysMenu)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}
}