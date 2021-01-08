package cn.fantuan.system.config;

import cn.fantuan.system.util.RedisUtil;
import cn.fantuan.system.util.code.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行登录检查
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisUtil redisUtil;

	//目标方法执行之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("拦截器：" + request.getServletPath());
		//获取请求头中的token
		String token = request.getHeader("token");
		//判断token是否为空
		token = token == null ? "" : token;
		//获取token的剩余时间
		long expire = redisUtil.getExpire(token);
		if (expire > 0) {
			//重置token的时间
			redisUtil.expire(token, 60L);
			//用户登录过，放行
			return true;
		} else {
			//未登录，返回登录页面
			request.setAttribute("msg", ErrorCode.TOKEN_ERROR.getMessage());
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
