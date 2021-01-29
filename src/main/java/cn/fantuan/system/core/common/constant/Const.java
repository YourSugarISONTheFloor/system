package cn.fantuan.system.core.common.constant;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * 系统常量
 */
public interface Const {
	/**
	 * 不需要权限验证的资源表达式
	 */
	List<String> NONE_PERMISSION_RES = CollectionUtil.newLinkedList("/login", "/logging", "/codeImage", "/forgetPassword/**", "/registered/**", "/styles/**", "/error/**");
}
