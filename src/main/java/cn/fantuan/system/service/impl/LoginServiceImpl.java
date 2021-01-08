package cn.fantuan.system.service.impl;

import cn.fantuan.system.dao.LoginDao;
import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.User;
import cn.fantuan.system.service.LoginService;
import cn.fantuan.system.util.code.CodeUtil;
import cn.fantuan.system.util.code.ErrorCode;
import cn.fantuan.system.util.RedisUtil;
import cn.fantuan.system.util.code.SuccessCode;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {
	private static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private RedisUtil redisUtil;

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
				//获取token值
				String token = IdUtil.simpleUUID();
				//添加指定token
				redisUtil.set(token, user, 60L);
				return new CommonResult(SuccessCode.SUCCESS, token);
			} else {
				return new CommonResult(ErrorCode.ERROR_PASSWORD);
			}
		} else {
			return new CommonResult(ErrorCode.NO_THIS_USER);
		}
	}

	//跟新密码
	@Override
	public Object updatePassword(String password, String username) {
		Integer rest = getUser(username);
		if (user != null) {
			if (loginDao.updatePassword(username, password, rest) == 1) {
				return new CommonResult(SuccessCode.SUCCESS_CHANGE);
			} else {
				return new CommonResult(ErrorCode.HAPPEN_ERROR);
			}
		} else {
			return new CommonResult(ErrorCode.USER_NOT_REG);
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
				return new CommonResult(SuccessCode.SUCCESS_REGISTER);
			} else {
				return new CommonResult(ErrorCode.HAPPEN_ERROR);
			}
		}
		return new CommonResult(ErrorCode.USER_ALREADY_REG);
	}

	//退出登录
	@Override
	public Object logout(String token) {
		boolean del = redisUtil.del(token);
		return new CommonResult(SuccessCode.SUCCESS, del);
	}

	@Override
	public Object look(String token) {
		Object o = redisUtil.get(token);
		return new CommonResult(SuccessCode.SUCCESS, o);
	}
}