package cn.fantuan.system.core.shiro;

import cn.fantuan.system.core.shiro.service.UserAuthService;
import cn.fantuan.system.core.shiro.service.impl.UserAuthServiceServiceImpl;
import cn.fantuan.system.modular.entities.outside.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm类，在该类中可以声明自定义的登录认证和授权的方法
 */
public class Realm extends AuthorizingRealm {


	/**
	 * 执行授权逻辑
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo:执行授权逻辑");
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加资源授权字符串
		info.addStringPermission("user:add");
		return info;
	}

	/**
	 * 自定义的登录认证逻辑
	 *
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//编写Shiro判断用户是否可以登录
		UserAuthService userAuthService = UserAuthServiceServiceImpl.me();
		//获取前端传递的数据
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		//获取用户信息
		User user = userAuthService.user(token.getUsername());
		ShiroUser shiroUser = userAuthService.shiroUser(user);
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), super.getName());
	}
}
