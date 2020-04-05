package tian.pusen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SyncService {
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
    public void syncService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("hello sync service 同步调用");
//        return "hello async service 同步调用";
    }
}
