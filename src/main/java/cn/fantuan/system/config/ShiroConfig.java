package cn.fantuan.system.config;

import cn.fantuan.system.core.shiro.MyFormAuthenticationFilter;
import cn.fantuan.system.core.shiro.Realm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
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
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//给filter设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

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
		hashMap.put("/**", "user");


		//配置系统受限资源
		//配置系统公共资源
		shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

		//默认为认证界面路径
		shiroFilterFactoryBean.setLoginUrl("/login");
		//没有权限跳转的url
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/404");

		return shiroFilterFactoryBean;
	}

	//创建安全管理器
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(CacheManager cacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//给安全管理器设置Realm
		securityManager.setRealm(getRealm());
		//设置管理器记住我
		securityManager.setRememberMeManager(rememberMeManager());
		//设置缓存
		securityManager.setCacheManager(cacheManager);
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
		simpleCookie.setMaxAge(10 * 24 * 60 * 60);//10天
		return simpleCookie;
	}

	/**
	 * cookie管理对象
	 *
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥  建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
		CookieRememberMeManager manager = new CookieRememberMeManager();
		manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
		manager.setCookie(rememberMeCookie);
		return manager;
	}

	/**
	 * 缓存管理器 使用Ehcache实现
	 */
	@Bean
	public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(ehcache.getObject());
		return ehCacheManager;
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
