package tian.pusen;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiverError {
    private final static String QUEUE_NAME = "topic-queue-error";

    private final static String EXCHANGE_NAME = "05-topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列  随即名称的队列
         channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        String queueName = channel.queueDeclare().getQueue();

        // 建立exchange和队列的绑定关系
//        String[] bindKeys = {"#"};
        String[] bindKeys = {"error"};
        for(int i =0; i< bindKeys.length; ++i) {
            // 绑定队列到交换机
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindKeys[i]);
        }

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);


        // 5.通过回调生成消费者并进行监听
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 获取消息内容然后处理
                String message = new String(body);
                System.out.println(" [Recv] Received '" + message + "'");

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 6.消费消息 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
