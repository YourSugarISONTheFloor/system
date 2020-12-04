package cn.fantuan.system.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    private static final String PATH_PARAMETER = "language";
    private static final String PATH_PARAMETER_SPLIT = "_";

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取浏览器路径中携带的language参数的值
        String lang = httpServletRequest.getParameter(PATH_PARAMETER);
        Locale locale = httpServletRequest.getLocale();
        if (!StringUtils.isEmpty(lang)) {
            String[] split = lang.split(PATH_PARAMETER_SPLIT);
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}