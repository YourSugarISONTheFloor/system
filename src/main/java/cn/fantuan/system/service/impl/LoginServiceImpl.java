package cn.fantuan.system.server.impl;

import cn.fantuan.system.dao.LoginDao;
import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.User;
import cn.fantuan.system.server.LoginServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServerImpl implements LoginServer {

    @Autowired
    private LoginDao loginDao;

    private User user;

    @Override
    public Object getUser(String username, String password) {
        user = loginDao.getUser(username);
        if (user != null) {
            if (user.getPassword() == password) {
                return new CommonResult(1, "可以登录", user);
            } else {
                return new CommonResult(1, "密码错误", null);
            }
        } else {
            return new CommonResult(1, "该用户走丢啦", null);
        }
    }
}