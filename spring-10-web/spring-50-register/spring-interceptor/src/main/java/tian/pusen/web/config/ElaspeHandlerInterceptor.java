package tian.pusen.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tian.pusen.Application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class ElaspeHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ElaspeHandlerInterceptor.class);
    ThreadLocal<Long> elaspe = new ThreadLocal<>();

    //  请求方法执行之前  返回true则通过
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer sb = request.getRequestURL();
        long begin = System.currentTimeMillis();
        elaspe.set(begin);
        logger.info("preHandler 请求URL： {} at {}", sb.toString() ,  new Date(begin));
        System.out.println("preHandler 请求URL："  + sb.toString() + " at " + new Date(begin));
        return true;
    }

    // 返回modelAndView之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long end = System.currentTimeMillis();
        logger.info("postHandle返回modelAndView之前 at {} elaspe {} millis", new Date(end)  ,  (end-elaspe.get()) );

        System.out.println("postHandle返回modelAndView之前 at " + new Date(end) + " elaspe " + (end-elaspe.get()) );
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long end = System.currentTimeMillis();
        logger.info("afterCompletion执行完请求方法完全返回之后 at {} elaspe {} millis", new Date(end)  ,  (end-elaspe.get()) );

        System.out.println("afterCompletion执行完请求方法完全返回之后 at " + new Date(end) + " elaspe " + (end-elaspe.get()) );
    }
}