package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.page.LayuiPage;
import cn.fantuan.system.modular.page.LayuiPageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserServer extends IService<User> {
	//获取用户信息
	LayuiPageInfo getAll(LayuiPage layuiPage);

	//更改用户状态
	CommonResult editState(Map map);
}
