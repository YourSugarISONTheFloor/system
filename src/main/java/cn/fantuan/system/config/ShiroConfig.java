package cn.fantuan.system.config;

import cn.fantuan.system.core.shiro.Realm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import static cn.fantuan.system.core.common.constant.Const.NONE_PERMISSION_RES;

/**
 * 用来整合shiro框架的相关配置
 * shiro权限管理的配置
 */
@Configuration
public class ShiroConfig {
	/**
	 * shiroFilter
	 * 负责拦截所有请求
	 * Shiro的过滤器链
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//给filter设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

		//添加Shiro内置过滤器
		/**
		 * 配置shiro拦截器链
		 *
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 * 常用的过滤器：
		 * anon：无需认证（登录）可以访问
		 * authc：必须认证才可以访问
		 * user：如果使用rememberMe的功能可以直接访问
		 * perms：该资源必须授权才可以访问
		 * role：该资源必须得到角色授权才可以访问
		 *
		 * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
		 *
		 * 顺序从上到下,优先级依次降低
		 *
		 * api开头的接口，走rest api鉴权，不走shiro鉴权
		 *
		 */
		Map<String, String> hashMap = new LinkedHashMap<>();
		for (String nonePermissionRe : NONE_PERMISSION_RES) {
			hashMap.put(nonePermissionRe, "anon");
		}
		hashMap.put("/**", "user");

		//配置系统受限资源
		//配置系统公共资源
		shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

		//默认认证界面路径
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登陆成功后跳转的url
		shiroFilterFactoryBean.setSuccessUrl("/");
		//没有权限跳转的url
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/404");

		return shiroFilterFactoryBean;
	}

	//创建安全管理器
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//给安全管理器设置Realm
		securityManager.setRealm(realm);
		return securityManager;
	}

	/**
	 * 创建自定义的Realm
	 */
	@Bean
	public Realm getRealm() {
		Realm realm = new Realm();
		//修改凭证校验匹配器
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		//设置加密算法
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		//设置散列次数
		hashedCredentialsMatcher.setHashIterations(1024);
		realm.setCredentialsMatcher(hashedCredentialsMatcher);
		return realm;
	}

	/**
	 * Shiro生命周期处理器:
	 * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
	 * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
