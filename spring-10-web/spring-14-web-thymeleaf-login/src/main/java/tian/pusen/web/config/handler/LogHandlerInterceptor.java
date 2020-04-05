package tian.pusen.web.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LogHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    //  ThreadLocal <>
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long begin = new Date().getTime();
        request.setAttribute("begin", "" +  begin) ;
        String url = request.getRequestURL().toString();
        logger.info("url {} begin {}", url, begin);
        return true;
    }

    //    postHandle
    //    调用前提：preHandle返回true
    //    调用时间：Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
    //    执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序倒着执行。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String beginStr = (String) request.getAttribute("begin");
        long begin = Long.parseLong(beginStr);
        long end = new Date().getTime();
        String url = request.getRequestURL().toString();
        logger.info("postHandle url {} end {} elapsed {}", url, end, end-begin);

    }

    //    afterCompletion
    //    调用前提：preHandle返回true
    //    调用时间：DispatcherServlet进行视图的渲染之后
    //            多用于清理资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String beginStr = (String) request.getAttribute("begin");
        long begin = Long.parseLong(beginStr);
        long end = new Date().getTime();
        String url = request.getRequestURL().toString();
        logger.info("afterCompletion url {} end {} elapsed {}", url, end, end-begin);
    }
}
