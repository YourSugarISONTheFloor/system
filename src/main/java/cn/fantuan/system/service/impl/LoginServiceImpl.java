package cn.fantuan.system.service.impl;

import cn.fantuan.system.dao.LoginDao;
import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.User;
import cn.fantuan.system.service.LoginService;
import cn.fantuan.system.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Autowired
    private LoginDao loginDao;
    @Autowired
    HttpSession session;

    User user;

    //根据username获取用户信息
    public Integer getUser(String username) {
        if (username.matches(EMAIL)) {
            user = loginDao.getUserToEmail(username);
            return 1;
        } else {
            user = loginDao.getUserToPhone(username);
            return 0;
        }
    }

    //查询用户
    @Override
    public Object getUserTo(String username, String password) {
        getUser(username);
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
    public Object updatePassword(String password, String username) {
        Integer rest = getUser(username);
        if (user != null) {
            if (loginDao.updatePassword(username, password, rest) == 1) {

                return new CommonResult(1, "更改密码成功", null);
            } else {
                return new CommonResult(0, "发生错误了", null);
            }
        } else {
            return new CommonResult(0, "该用户还没住进灰姑凉家里呢", null);
        }

    }

    //添加用户
    @Override
    public Object addUser(String username, String password, String name) {
        Integer rest = getUser(username);
        if (user == null) {
            if (StringUtils.isEmpty(name)) {
                name = CodeUtil.getUUID();
            }
            Integer integer = loginDao.addUser(username, password, name, rest);
            if (integer != null) {
                return new CommonResult(1, "该用户入住灰姑凉成功", null);
            } else {
                return new CommonResult(0, "出错啦！", null);
            }
        }
        return new CommonResult(0, "该用户已经在灰姑凉家里了", null);
    }
}