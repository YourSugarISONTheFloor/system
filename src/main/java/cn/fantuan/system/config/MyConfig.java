package cn.fantuan.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 标注这个类是一个配置类
@Configuration
public class MyConfig implements WebMvcConfigurer {
    // 配置视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器请求下面addViewController括号中的请求地址，会被跳转到视图前缀+setViewName+视图后缀
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    //注册拦截器的
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截请求addPathPatterns("/**")，拦截所有请求。
        //排除拦截请求excludePathPatterns("/login","/log")
        //springboot已经做好静态文件的映射，不用处理静态资源了
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/login","/logging","/codeImage","/forgetPassword","/registered","/styles/**");
    }


    // 将本地化解析器配置到容器中
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}