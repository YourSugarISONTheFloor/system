package cn.fantuan.system.core.shiro;

import cn.fantuan.system.modular.util.code.ErrorCode;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
		/**
		 * 如果是ajax请求则不进行跳转
		 */
		if (httpServletRequest.getHeader("x-requested-with") != null
				&& httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			httpServletResponse.setHeader("sessionstatus", "timeout");
			return false;
		} else {
			/**
			 * 第一次点击页面
			 */
			String referer = httpServletRequest.getHeader("Referer");
			if (referer == null) {
				// 这里做的重定向
				saveRequestAndRedirectToLogin(request, response);
				return false;
			} else {
				/**
				 * 从别的页面跳转过来的
				 */
				if (ShiroKit.getSession().getAttribute("sessionFlag") == null) {
					httpServletRequest.setAttribute("msg", ErrorCode.TOKEN_ERROR.getMessage());
					httpServletRequest.getRequestDispatcher("/login").forward(request, response);
					return false;
				} else {
					// 这里做的重定向
					saveRequestAndRedirectToLogin(request, response);
					return false;
				}

			}
		}
	}
}
