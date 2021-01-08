package cn.fantuan.system.service;

import cn.fantuan.system.entities.outside.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginService extends IService<User> {
    //根据用户输入的用户名或密码进行查询
    Object getUserTo(String username, String password);
    //跟新密码
    Object updatePassword(String password, String username);
    //添加用户
    Object addUser(String username, String password, String name);

	Object logout(String token);

	Object look(String token);
}
