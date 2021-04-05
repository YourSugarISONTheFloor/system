package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.Role;

public interface RoleServer {

	//获取所有的角色集
	CommonResult getRole();

	//添加角色集
	CommonResult add(Role role);

	//删除角色
	CommonResult del(Long id);
}
