package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.DataResult;
import cn.fantuan.system.modular.entities.SysMenu;
import cn.fantuan.system.modular.mapper.SysMenuServiceMapper;
import cn.fantuan.system.modular.service.SysMenuService;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.util.menu.TreeUtil;
import cn.fantuan.system.modular.vo.MenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuServiceMapper, SysMenu> implements SysMenuService {
	@Autowired
	private RedisUtil redisUtil;

	public Map<String, Object> menuSys(QueryWrapper queryWrapper) {
		//返回的菜单列表
		Map<String, Object> map = new HashMap<>(16);
		//home列表
		Map<String, Object> home = new HashMap<>(16);
		//logo列表
		Map<String, Object> logo = new HashMap<>(16);
		//获取所有激活的菜单数组：父ID为0和状态为激活的菜单
		List<SysMenu> menuList = this.list(queryWrapper);
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
		home.put("href", "welcome");
		//logo标题
		logo.put("title", "后台管理系统");
		//logo图片地址
		logo.put("image", "/styles/images/logo.png");
		//logo链接地址
		logo.put("href", "");

		map.put("homeInfo", home);
		map.put("logoInfo", logo);
		map.put("menuInfo", new TreeUtil().toTree(menuInfo, 0L));
		//System.out.println(map);
		return map;
	}

	@Override
	public Map<String, Object> menu(Long id) {
		//用户ID为1的为超级管理员
		if (id == 1) {
			return menuSys(new QueryWrapper<SysMenu>().eq("status", "1"));
		}
		Map<String, Object> hmget = redisUtil.hmget(RedisConst.role + id);
		List roleList = (List) hmget.get("role_list");
		return menuSys(new QueryWrapper<SysMenu>().eq("status", "1").in("id", roleList));
	}

	@Override
	public Map<String, Object> menuAll() {
		return menuSys(null);
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

	//更改菜单状态
	@Override
	public Object editState(Map map) {
		UpdateWrapper<SysMenu> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("status", Boolean.parseBoolean(map.get("status").toString()));
		updateWrapper.eq("id", map.get("id"));
		if (update(updateWrapper)) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	//删除菜单
	@Override
	public Object del(Integer id) {
		if (remove(new QueryWrapper<SysMenu>().eq("id", id))) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}

	//修改菜单
	@Override
	public Object editMenu(SysMenu sysMenu) {
		if (update(sysMenu, new QueryWrapper<SysMenu>().eq("id", sysMenu.getId()))) {
			return new CommonResult(SuccessCode.SUCCESS, true);
		}
		return new CommonResult(ErrorCode.HAPPEN_ERROR, false);
	}
}