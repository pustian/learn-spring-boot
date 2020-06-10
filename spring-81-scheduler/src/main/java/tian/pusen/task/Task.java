package tian.pusen.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;

@Component
public class Task {
    @Scheduled(cron="4/17 * *  * * ? ")   //每10秒执行一次
    public void crontest() {
        System.out.println("do somthing at " + new Date());
    }
}
