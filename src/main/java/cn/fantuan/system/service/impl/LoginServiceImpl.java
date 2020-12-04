package cn.fantuan.system.service.impl;

import cn.fantuan.system.dao.LoginDao;
import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.User;
import cn.fantuan.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;
    @Autowired
    HttpSession session;

    @Override
    public Object getUser(String username, String password) {
        User user = loginDao.getUser(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("session_user", user.getName());
                return new CommonResult(1, "可以登录", user);
            } else {
                return new CommonResult(2, "密码错误", null);
            }
        } else {
            return new CommonResult(0, "该用户走丢啦", null);
        }
    }

    //跟新密码
    @Override
    public Integer updatePassword(String password, String username) {
        return loginDao.updatePassword(username,password);
    }
}