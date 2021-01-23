package cn.fantuan.system.core.shiro;

import cn.fantuan.system.modular.entities.outside.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * shiro工具类
 */
public class ShiroKit {

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
		shiroUser.setDeptId(10L);
		shiroUser.setDeptName("");
		shiroUser.setName(user.getName());
		shiroUser.setEmail(user.getEmail());
		shiroUser.setAvatar(user.getAvatar());

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
