package tian.pusen.web.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    private final static String[] PREFIX_URL = {
            "http://192.168.1.204:8080/webjars",
            "http://192.168.1.204:8080/asserts"
    };
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        for(String prefixUrl: PREFIX_URL) {
            if(url.startsWith(prefixUrl) )
                return true;
        }
        Object user = request.getSession().getAttribute("username");
        logger.info("check username exists {}", null == user);
        if (null == user) {
            // 转发到index.html 页面
            request.setAttribute("msg", "ERROR:");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("postHandler do nothing");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        logger.info("afterCompletion do nothing");;
    }

}
