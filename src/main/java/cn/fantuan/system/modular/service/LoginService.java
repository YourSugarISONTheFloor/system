package cn.fantuan.system.modular.service;

import cn.fantuan.system.modular.entities.outside.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginService extends IService<User> {
	//根据用户名查询用户信息
	User getUser(String username);

	//根据用户输入的用户名或密码进行查询
	Object getUserTo(String username, String password);

	//跟新密码
	Object updatePassword(String password, String username);

	//添加用户
	Object addUser(String username, String password, String name);

	//退出登录
	Object logout(String token);

	//获取用户信息
	Object look(String token);
}
