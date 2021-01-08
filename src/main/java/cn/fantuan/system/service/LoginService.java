package cn.fantuan.system.service;

public interface LoginService {
    //根据用户输入的用户名或密码进行查询
    Object getUserTo(String username, String password);
    //跟新密码
    Object updatePassword(String password, String username);
    //添加用户
    Object addUser(String username, String password, String name);

	Object logout(String token);

	Object look(String token);
}
