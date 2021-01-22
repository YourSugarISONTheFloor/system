package cn.fantuan.system.modular.service.impl;

import cn.fantuan.system.modular.entities.CommonResult;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.mapper.LoginMapper;
import cn.fantuan.system.modular.util.code.ErrorCode;
import cn.fantuan.system.modular.util.code.SuccessCode;
import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.code.CodeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, User> implements LoginService {
	private static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	@Resource
	private LoginMapper loginMapper;

	@Autowired
	private RedisUtil redisUtil;

	private Integer rest;

	//根据username获取用户信息
	public User getUser(String username) {
		if (username.matches(EMAIL)) {
			rest = 1;
			return loginMapper.selectOne(new QueryWrapper<User>().eq("email", username));
		} else {
			rest = 0;
			return loginMapper.selectOne(new QueryWrapper<User>().eq("phone", username));
		}
	}

	//查询用户
	@Override
	public Object getUserTo(String username, String password) {
		User user = getUser(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				//获取token值
				String token = IdUtil.simpleUUID();
//				Cookie cookie = new Cookie("token", token);
//				cookie.setMaxAge(30 * 60);// 设置为30min
//				cookie.setPath("/");
//				System.out.println("已添加===============");
//				HttpServletResponse response = null;
//				response.addCookie(cookie);

				//添加指定token
				redisUtil.set(token, user, 10 * 24 * 60L);
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
		User user = getUser(username);
		if (user != null) {
			user.setPassword(password);
			if (rest == 1) {
				//用户输入的是邮箱
				loginMapper.update(user, new QueryWrapper<User>().eq("email", username));
			} else {
				//用户输入的是手机号
				loginMapper.update(user, new QueryWrapper<User>().eq("phone", username));
			}
			return new CommonResult(SuccessCode.SUCCESS_CHANGE);
		} else {
			return new CommonResult(ErrorCode.USER_NOT_REG);
		}
	}

	//添加用户
	@Override
	public Object addUser(String username, String password, String name) {
		User user = getUser(username);
		if (user == null) {
			//用户未输入用户名
			if (StringUtils.isEmpty(name)) {
				name = CodeUtil.getUUID();
			}
			User use = new User();
			use.setPassword(password);
			use.setName(name);
			if (rest == 1) {
				//用户是邮箱注册
				use.setEmail(username);
			} else {
				//用户是手机号注册
				use.setPhone(Long.valueOf(username));
			}
			Integer integer = loginMapper.insert(use);
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

	//获取用户信息
	@Override
	public Object look(String token) {
		Object object = redisUtil.get(token);
		return new CommonResult(SuccessCode.SUCCESS, object);
	}
}