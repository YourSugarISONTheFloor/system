package cn.fantuan.system.service.impl;

import cn.fantuan.system.entities.CommonResult;
import cn.fantuan.system.entities.outside.User;
import cn.fantuan.system.mapper.LoginMapper;
import cn.fantuan.system.service.LoginService;
import cn.fantuan.system.util.RedisUtil;
import cn.fantuan.system.util.code.CodeUtil;
import cn.fantuan.system.util.code.ErrorCode;
import cn.fantuan.system.util.code.SuccessCode;
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

	private User user;

	//根据username获取用户信息
	public Integer getUser(String username) {
		if (username.matches(EMAIL)) {
			user = loginMapper.selectOne(new QueryWrapper<User>().eq("email", username));
			return 1;
		} else {
			user = loginMapper.selectOne(new QueryWrapper<User>().eq("phone", username));
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
//				Cookie cookie = new Cookie("token", token);
//				cookie.setMaxAge(30 * 60);// 设置为30min
//				cookie.setPath("/");
//				System.out.println("已添加===============");
//				HttpServletResponse response = null;
//				response.addCookie(cookie);

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
		Integer rest = getUser(username);
		System.out.println("rest:" + rest);
		if (user == null) {
			//用户未输入用户名
			if (StringUtils.isEmpty(name)) {
				name = CodeUtil.getUUID();
			}
			User user = new User();
			user.setPassword(password);
			user.setName(name);
			if (rest == 1) {
				//用户是邮箱注册
				user.setEmail(username);
			} else {
				//用户是手机号注册
				user.setPhone(Long.valueOf(username));
			}
			Integer integer = loginMapper.insert(user);
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