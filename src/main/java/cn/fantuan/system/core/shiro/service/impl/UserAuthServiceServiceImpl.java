package cn.fantuan.system.core.shiro.service.impl;

import cn.fantuan.system.core.shiro.ShiroKit;
import cn.fantuan.system.core.shiro.ShiroUser;
import cn.fantuan.system.core.shiro.service.UserAuthService;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.util.code.ManagerStatus;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthServiceServiceImpl implements UserAuthService {
	@Autowired
	private LoginService loginService;

	@Override
	public User user(String account) {

		User user = loginService.getUser(account);

		// 账号不存在
		if (null == user) {
			throw new CredentialsException();
		}
		// 账号被冻结
		if (user.getStatus() != ManagerStatus.OK.getCode()) {
			throw new LockedAccountException();
		}
		return user;
	}

	/**
	 * 根据系统用户获取Shiro的用户
	 *
	 * @param user 系统用户
	 */
	@Override
	public ShiroUser shiroUser(User user) {

		ShiroUser shiroUser = ShiroKit.createShiroUser(user);
		return shiroUser;
	}

	@Override
	public List<String> findPermissionsByRoleId(Long roleId) {
		return null;
	}

	@Override
	public String findRoleNameByRoleId(Long roleId) {
		return null;
	}

	@Override
	public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
		return null;
	}
}
