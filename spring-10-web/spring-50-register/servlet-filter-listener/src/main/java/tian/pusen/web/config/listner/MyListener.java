package tian.pusen.web.config.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("监听器开始初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("监听器开始销毁");
    }
}