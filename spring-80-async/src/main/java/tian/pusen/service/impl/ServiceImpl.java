package tian.pusen.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tian.pusen.service.IService;

@Service
public class ServiceImpl implements IService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    @Async
    @Override
    public String asyncService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("hello async service 异步调用");
        return "hello async service 异步调用";
    }

    @Override
    public String syncService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("hello sync service 同步调用");
        return "hello async service 同步调用";
    }
}
