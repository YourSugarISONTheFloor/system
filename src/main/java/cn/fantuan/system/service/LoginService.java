package cn.fantuan.system.server;

public interface LoginServer {
    //根据用户输入的用户名或密码进行查询
    Object getUser(String username, String password);
}
