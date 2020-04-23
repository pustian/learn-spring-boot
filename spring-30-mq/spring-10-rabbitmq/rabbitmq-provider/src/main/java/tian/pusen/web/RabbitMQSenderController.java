package tian.pusen.web;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.config.DirectRabbitConfig;

import java.util.UUID;

@RestController
public class RabbitMQSenderController {
    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/rabbitmq/sendMsg")
    public void sendDirectMessage(@RequestParam(defaultValue = "test message, hello!") String msg) {
        Message message = new Message(msg.getBytes(), new MessageProperties() );
        rabbitTemplate.convertSendAndReceive(message);
        rabbitTemplate.convertSendAndReceive(DirectRabbitConfig.EXCHANGE_NAME, DirectRabbitConfig.ROUTING_KEY, message);
        System.out.println("rabbit发送消息");
    }

}
