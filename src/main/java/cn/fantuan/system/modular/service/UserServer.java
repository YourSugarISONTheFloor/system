package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface UserServer extends IService<User> {
	//获取用户
	LayuiPageInfo getAll(LayuiPage layuiPage, String name, List deptId);

	//更改用户状态
	CommonResult editState(Map map);

	//更改用户信息
	CommonResult editUser(Map map);

	//删除用户
	CommonResult delete(Long id);

	//用户角色分配
	CommonResult setRole(Map map);

	//重置密码
	CommonResult reset(Long id);

	Object getTree(String name, Integer tree);
}
