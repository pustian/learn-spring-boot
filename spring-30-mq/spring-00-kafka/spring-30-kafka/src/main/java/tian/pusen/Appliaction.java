package tian.pusen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tian.pusen.service.KafkaSender;

@SpringBootApplication
public class Appliaction {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Appliaction.class, args);

//        KafkaSender sender = context.getBean(KafkaSender.class);
//        for (int i = 0; i < 1000; i++) {
//            sender.send();
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
