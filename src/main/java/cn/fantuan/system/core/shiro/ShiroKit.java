package cn.fantuan.system.core.shiro;

import cn.fantuan.system.core.common.constant.RedisConst;
import cn.fantuan.system.modular.entities.outside.User;
import cn.fantuan.system.modular.util.RedisUtil;
import cn.fantuan.system.modular.util.SpringContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;


/**
 * shiro工具类
 */
public class ShiroKit {

	private static RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);

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
		//获取用户部门信息
		Map<Object, Object> map = redisUtil.hmget(RedisConst.dept + user.getId());
		System.out.println("map:" + map);
		shiroUser.setDeptId((Long) map.get("deptID"));
		shiroUser.setDeptName(map.get("deptName") == null ? "" : map.get("deptName").toString());
		shiroUser.setRoleList((List<Long>) map.get("roleList"));
		shiroUser.setRoleNames((List<String>) map.get("roleNames"));
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
