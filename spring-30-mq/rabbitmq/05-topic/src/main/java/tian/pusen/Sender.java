package tian.pusen;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import javax.lang.model.element.NestingKind;
import java.util.UUID;

public class Sender {

    private final static String EXCHANGE_NAME = "05-topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 为通道声明tpoic类型的 exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String []routingKey = {"debug", "info", "warn","error"};
        // 消息内容
        for(int i =0; i< 100; ++i) {
            // 消息内容
            String message = "Hello World!" + routingKey[i%routingKey.length] + "==>" + i + "    "  + UUID.randomUUID() ;
            channel.basicPublish(EXCHANGE_NAME, routingKey[i%routingKey.length], null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(10);
        }

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
