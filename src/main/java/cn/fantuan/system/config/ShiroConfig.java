package cn.fantuan.system.config;

import cn.fantuan.system.core.shiro.MyFormAuthenticationFilter;
import cn.fantuan.system.core.shiro.Realm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
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
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//给filter设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());

		/**
		 * 覆盖默认的user拦截器(默认拦截器解决不了ajax请求 session超时的问题,若有更好的办法请及时反馈作者)
		 */
		HashMap<String, Filter> myFilters = new HashMap<>();
		myFilters.put("user", new MyFormAuthenticationFilter());
		shiroFilterFactoryBean.setFilters(myFilters);

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
		hashMap.put("/aa/aa", "authc");
		hashMap.put("/**", "user");


		//配置系统受限资源
		//配置系统公共资源
		shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

		//默认为认证界面路径
		shiroFilterFactoryBean.setLoginUrl("/login");
//		//登陆成功后跳转的url
//		shiroFilterFactoryBean.setSuccessUrl("/");
		//没有权限跳转的url
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/404");

		return shiroFilterFactoryBean;
	}

	//创建安全管理器
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//给安全管理器设置Realm
		securityManager.setRealm(getRealm());
		//设置缓存
		securityManager.setCacheManager(getCacheShiroManager());
		return securityManager;
	}

	/**
	 * 创建自定义的Realm
	 */
	@Bean
	public Realm getRealm() {
		Realm realm = new Realm();
//		//修改凭证校验匹配器
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//		//设置加密算法
//		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
//		//设置散列次数
//		hashedCredentialsMatcher.setHashIterations(1024);
//		realm.setCredentialsMatcher(hashedCredentialsMatcher);
		return realm;
	}

	/**
	 * 记住密码Cookie
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
		return simpleCookie;
	}

	/**
	 * 缓存管理器 使用Ehcache实现
	 */
	@Bean
	public CacheManager getCacheShiroManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		return ehCacheManager;
	}

	/**
	 * 在方法中 注入 securityManager,进行代理控制
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		bean.setArguments(securityManager);
		return bean;
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

	/**
	 * 启用shrio授权注解拦截方式，AOP式方法级权限检查
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
