package cn.fantuan.system.core.shiro.service.impl;

import cn.fantuan.system.core.shiro.ShiroKit;
import cn.fantuan.system.core.shiro.ShiroUser;
import cn.fantuan.system.core.shiro.service.UserAuthService;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.service.LoginService;
import cn.fantuan.system.modular.util.SpringContextHolder;
import cn.fantuan.system.modular.util.code.ManagerStatus;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthServiceServiceImpl implements UserAuthService {
	@Autowired
	private LoginService loginService;

	public static UserAuthService me() {
		return SpringContextHolder.getBean(UserAuthService.class);
	}

	@Override
	public User user(String account) {

		User user = loginService.getUser(account);

		// 账号不存在
		if (null == user) {
			throw new UnknownAccountException("用户不存在");
		}
		// 账号被冻结
		if (user.getStatus() == ManagerStatus.DELETED.getCode()) {
			throw new LockedAccountException("账号被冻结");
		}
		// 账号被冻结
		if (user.getStatus() == ManagerStatus.FREEZED.getCode()) {
			throw new DisabledAccountException("账号被禁用");
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

	/**
	 * 获取权限列表通过角色id
	 *
	 * @param roleId 角色id
	 */
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
