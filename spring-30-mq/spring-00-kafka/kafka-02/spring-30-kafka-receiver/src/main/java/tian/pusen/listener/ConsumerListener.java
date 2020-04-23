package tian.pusen.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tian.pusen.entity.User;

import java.util.Optional;

@Component
public class ConsumerListener {
    @KafkaListener(topics = "userTopic")
    public void listen(User user) {
//    public void listen(String user) {
        System.out.println(user);
    }
}
