package cn.fantuan.system.core.shiro;

import cn.fantuan.system.modular.util.code.ErrorCode;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFormAuthenticationFilter extends AccessControlFilter {

	//可以发现他是调用的isAccessAllowed方法和onAccessDenied方法，
	//只要两者有一个可以就可以了，从名字中我们也可以理解，
	//他的逻辑是这样：先调用isAccessAllowed，如果返回的是true，
	//则直接放行执行后面的filter和servlet，如果返回的是false，
	//则继续执行后面的onAccessDenied方法，
	//如果后面返回的是true则也可以有权限继续执行后面的filter和servelt。
	//只有两个函数都返回false才会阻止后面的filter和servlet的执行

	/**
	 * isAccessAllowed：判断是否登录
	 * 在登录的情况下会走此方法，此方法返回true直接访问控制器
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			//判断是否有用户信息
			Subject subject = getSubject(request, response);
			return subject.getPrincipal() != null;
		}
	}

	/**
	 * onAccessDenied：是否是拒绝登录
	 * 没有登录的情况下会走此方法
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//		System.out.println("拦截器拦截的路径请求为：" + httpServletRequest.getServletPath());
		/**
		 * 如果是ajax请求则不进行跳转
		 */
		if (httpServletRequest.getHeader("x-requested-with") != null
				&& httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			httpServletResponse.setHeader("sessionstatus", "timeout");
			//设置提示信息
			httpServletResponse.setHeader("msg", ErrorCode.TOKEN_EXPIRED.getMessage());
			return false;
		} else {
			//未登录，返回登录页面
			request.setAttribute("msg", ErrorCode.TOKEN_ERROR.getMessage());
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
//			/**
//			 * 第一次点击页面
//			 */
//			if (httpServletRequest.getHeader("Referer") == null) {
//				//未登录，返回登录页面
//				request.setAttribute("msg", ErrorCode.TOKEN_ERROR.getMessage());
//				request.getRequestDispatcher("/login").forward(request, response);
//				return false;
//			} else {
//				httpServletRequest.setAttribute("msg", ErrorCode.TOKEN_ERROR.getMessage());
//				httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
//				return false;
//			}
		}
	}
}
