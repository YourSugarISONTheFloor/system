package cn.fantuan.system.core.shiro;

import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * shiro工具类
 */
public class ShiroKit {

	@Autowired
	private static RedisUtil redisUtil;

	/**
	 * 通过用户表的信息创建一个shiroUser对象
	 */
	public static ShiroUser createShiroUser(User user) {
		ShiroUser shiroUser = new ShiroUser();
		if (user == null) {
			return shiroUser;
		}
		shiroUser.setId(user.getId());
		shiroUser.setPhone(user.getPhone());
		shiroUser.setEmail(user.getEmail());
		shiroUser.setPassword(user.getPassword());
		shiroUser.setName(user.getName());
		shiroUser.setStatus(user.getStatus());
		shiroUser.setAvatar(user.getAvatar());
		shiroUser.setDeptName("");
		return shiroUser;
	}

	/**
	 * 获取当前 Subject
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 从shiro获取session
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

}
