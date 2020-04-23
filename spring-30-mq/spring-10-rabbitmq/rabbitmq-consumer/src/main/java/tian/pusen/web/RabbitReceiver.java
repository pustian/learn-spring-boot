package tian.pusen.web;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tian.pusen.config.DirectRabbitConfig;

@Component
public class RabbitReceiver {
    @RabbitHandler
    @RabbitListener(queues = DirectRabbitConfig.QUEUE_NAME)
    public void process(Message message) {
        String msg = new String(message.getBody());
        System.out.println(msg);
    }
}
