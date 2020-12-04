package cn.fantuan.system.service;

public interface LoginService {
    //根据用户输入的用户名或密码进行查询
    Object getUser(String username, String password);
    //跟新密码
    Integer updatePassword(String password, String username);
}
