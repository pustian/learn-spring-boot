## RabbitMQ实战教程

https://blog.csdn.net/hellozpc/article/details/81436980

# RabbitMQ简介

AMQP：AMQP是消息队列的一个协议。

> + Queue 是RabbitMQ的内部对象，用于存储消息。RabbitMQ中的消息只能存储在 *Queue* 中，消费者从 *Queue* 中获取消息并消费。
> + Exchange 生产者将消息发送到 *Exchange*，由 *Exchange* 根据一定的规则将消息路由到一个或多个 *Queue* 中（或者丢弃）。
> + Binding RabbitMQ中通过 *Binding* 将 *Exchange* 与 *Queue* 关联起来。
> + Binding key 在绑定（Binding） *Exchange* 与 *Queue* 的同时，一般会指定一个 *binding key*。
> + Routing key 生产者在将消息发送给 *Exchange* 的时候，一般会指定一个 *routing key*，来指定这个消息的路由规则。 *Exchange* 会根据 *routing key* 和 *Exchange Type* 以及 *Binding key* 的匹配情况来决定把消息路由到哪个 *Queue*。
> + Exchange Types  RabbitMQ常用的Exchange Type有 *fanout*、 *direct*、 *topic*、 *headers* 这四种。
>   + fanout 这种类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中，这时 *Routing key* 不起作用。
>   + direct 这种类型的Exchange路由规则也很简单，它会把消息路由到那些 *binding key* 与 *routing key*完全匹配的Queue中。
>   + topic 这种类型的Exchange的路由规则支持 *binding key* 和 *routing key* 的模糊匹配，会把消息路由到满足条件的Queue。 *binding key* 中可以存在两种特殊字符 `*`与 `#`，用于做模糊匹配，其中 `*` 用于匹配一个单词，`#` 用于匹配多个单词（可以是零个），单词以 `.`为分隔符。
>   + headers  这种类型的Exchange不依赖于 *routing key* 与 *binding key* 的匹配规则来路由消息，而是根据发送的消息内容中的 *headers* 属性进行匹配。

官网：https://www.rabbitmq.com/

5中消息队列

https://www.rabbitmq.com/getstarted.html

![](../images/mq.png)

## 安装文档

Docker安装

https://registry.hub.docker.com/_/rabbitmq/

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.8.3-management
```

## 添加用户

![](./../images/rabbitmq_adduser.png)

## 用户角色

+ 超级管理员(administrator)
  可登陆管理控制台，可查看所有的信息，并且可以对用户，策略(policy)进行操作。
+ 监控者(monitoring)
  可登陆管理控制台，同时可以查看rabbitmq节点的相关信息(进程数，内存使用情况，磁盘使用情况等)
+ 策略制定者(policymaker)
  可登陆管理控制台, 同时可以对policy进行管理。但无法查看节点的相关信息(上图红框标识的部分)。
+ 普通管理者(management)
  仅可登陆管理控制台，无法看到节点信息，也无法对策略进行管理。
+ 其他
  无法登陆管理控制台，通常就是普通的生产者和消费者。

## 创建Virtual Hosts

![](./../images/rabbitmq_addvirtualhost.png)

默认在创建在登陆用户下

点击具体用户可以在里面配置virtualhost

![](./../images/rabbitmq_virtualhost1.png)

## 检查权限

![](./../images/rabbitmq_virtualhost2.png)

# 应用

### 01-simplest

##### pom.xml

```xml
        <!-- https://mvnrepository.com/artifact/com.rabbitmq/amqp-client -->
        <dependency>
            <groupId>com.rabbitmq</groupId>
            <artifactId>amqp-client</artifactId>
            <version>4.12.0</version>
        </dependency>
```

##### ConnectionUtil

```java
public class ConnectionUtil {
    static final String HOST_IP = "192.168.122.249";
    static final Integer PORT = 5672;
    static final String VIRTUAL_HOST = "spring";
    static final String USERNAME = "admin";
    static final String PASSWORD = "admin";
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost(HOST_IP);
        //端口
        factory.setPort(PORT);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
```

##### Sender

```java
public class Sender {
    private final static String QUEUE_NAME = "simplest";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World!" + UUID.randomUUID();
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
```

##### Receiver

```java
public class Receiver {
    private final static String QUEUE_NAME = "simplest";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
//        Consumer consumer = new DefaultConsumer(channel);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }

        //关闭通道和连接
//        channel.close();
//        connection.close();
    }
}
```

```
QueueingConsumer在4.X以上版本已不支持
```

##### 测试方法

> Sender.main 先执行，注意打印信息
>
> 观察http://192.168.122.249:15672/#/queues 中 Ready 增加
>
> Receiver.main 执行，注意打印信息 
>
> 再观测 http://192.168.122.249:15672/#/queues 中 Ready 增加 未0

### 02-works

> 一个生产者、2个消费者。

##### pom.xml 同上

##### Sender

```java
public class Sender {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i =0; i< 100; ++i) {
            // 消息内容
            String message = "Hello World!" + "==>" + i + "    "  + UUID.randomUUID();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(10);
        }

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
```

##### Receiver/ReceiverReplica/ReceiverReplica2

+ 方便测试Thread.sleep(10/20/30);

```java
public class Receiver {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //休眠
            Thread.sleep(10);
            // 返回确认状态，注释掉表示使用自动确认模式
            //channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

        //关闭通道和连接
//        channel.close();
//        connection.close();
    }
}
```

##### 测试方法

> Sender.main 先执行，注意打印信息
>
> 观察http://192.168.122.249:15672/#/queues 中 Ready 增加
>
> Receiver.main 执行，注意打印信息 
>
> ReceiverReplica.main 执行，注意打印信息 
>
> 再观测 http://192.168.122.249:15672/#/queues 中 Ready 增加
>
> 
>
> 默认使用轮询方式分发消息
>
> basicQos( prefetchCount = 1)方法，来限制RabbitMQ只发不超过1条的消息给同一个消费者。当消息处理完毕后，有了反馈，才会进行第二次发送。

### 02-work-update

##### Receiver/ReceiverReplica/ReceiverReplica2

```
public class Receiver {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1); // Per consumer limit

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //休眠
            Thread.sleep(10);
            // 返回确认状态，注释掉表示使用自动确认模式
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

        //关闭通道和连接
//        channel.close();
//        connection.close();
    }
}
```

##### 测试方法 

同上

> 注意输出 三个Receiver输出不再平均

### 03-Publish/Subscribe

> + 1个生产者，多个消费者
> + 每一个消费者都有自己的一个队列
> + 生产者没有将消息直接发送到队列，而是发送到了交换机
> + 每个队列都要绑定到交换机
> + 生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
>   注意：`一个消费者队列`可以有多个消费者实例，只有其中一个消费者实例会消费

+ 在绑定（Binding）Exchange与Queue的同时，一般会指定一个binding key。

  在绑定多个Queue到同一个Exchange的时候，这些Binding允许使用相同的binding key。

  生产者在将消息发送给Exchange的时候，一般会指定一个routing key，来指定这个消息的路由规则，生产者就可以在发送消息给Exchange时，通过指定routing key来决定消息流向哪里。

  RabbitMQ常用的Exchange Type有三种：fanout、direct、topic。

  + fanout:把所有发送到该Exchange的消息投递到所有与它绑定的队列中(后期补上)。
  + direct: 把消息投递到那些binding key与routing key完全匹配的队列中。
  + topic:将消息路由到binding key与routing key模式匹配的队列中。

+ 有几种交换类型可供选择：direct, topic, headers and fanout. 我们将专注于最后这个-- fanout. 

+ 示例图
  [EXCHANGE_NAME|publish-subscribe] 

  [QUEUE_NAME |public-subscribe-queue]     [QUEUE_NAME |public-subscribe-queue2]

  [Consumer| Receiver/ReceiverReplica]		   [Consumer|ReceiverAnother]

##### pom.xml 

同上

##### Sender

```java
public class Sender {
    private final static String EXCHANGE_NAME = "publish-subscribe";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String message = "Hello World!" + UUID.randomUUID();
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
```

##### Receiver/ReceiverReplica 同一队列

```java
public class Receiver {
    private final static String QUEUE_NAME = "public-subscribe-queue";

    private final static String EXCHANGE_NAME = "publish-subscribe";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [Recv] Received '" + message + "'");
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
```

##### ReceiverAnother 不同队列

```java
public class ReceiverAnother {
    // 不同的消息队列
    private final static String QUEUE_NAME = "public-subscribe-queue2";

    private final static String EXCHANGE_NAME = "publish-subscribe";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [Recv] Received '" + message + "'");
            Thread.sleep(30);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
```

##### 测试方法 

同上

执行会发现操作界面没有什么用

> 消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中。

Exchange未做持久化操作

### 04-Route

##### pom.xml

同上

##### Sender

```java
public class Sender {

    private final static String EXCHANGE_NAME = "04-route";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 为通道声明tpoic类型的 exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

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
```

##### Receive

```java
public class ReceiverAll {
    private final static String QUEUE_NAME = "route-queue-all";

    private final static String EXCHANGE_NAME = "04-route";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明队列  随即名称的队列
         channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        String queueName = channel.queueDeclare().getQueue();

        // 建立exchange和队列的绑定关系
//        String[] bindKeys = {"#"};
        String[] bindKeys = {"debug","info", "warn", "error"};
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
```

> String[] bindKeys = {"#"}; 使用这个符号来做key 绑定不可用。  只是作为routingkey = '#' 的情况使用

##### 测试方法 

同上

### 05-Topics 

与 04-route 相比较通配符模式

![](./../images/05_topic_exchange.webp)

##### pom.xml

同上

##### Sender

```java
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
```

>  channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
>
> channel.basicPublish 发送时是有routingKey

##### Receiver

```java
public class Receiver {
    private final static String QUEUE_NAME = "topic-queue-multi";

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
        String[] bindKeys = {"debug","info", "warn"};
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
```

>  channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
>
> channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindKeys[i]);
>
> 可获取对应key的消息值

##### 测试方法

同上

### 06-RPC

### 07-Publisher Confirms

# AMQP

+ Broker：简单来说就是消息队列服务器实体。
+ Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列。
+ Queue：消息队列载体，每个消息都会被投入到一个或多个队列。
+ Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来。
+ Routing Key：路由关键字，exchange根据这个关键字进行消息投递。
+ vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离。
+ producer：消息生产者，就是投递消息的程序。
+ consumer：消息消费者，就是接受消息的程序。
+ channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务。

消息队列的使用过程大概如下：

（1）客户端连接到消息队列服务器，打开一个channel。
（2）客户端声明一个exchange，并设置相关属性。
（3）客户端声明一个queue，并设置相关属性。
（4）客户端使用routing key，在exchange和queue之间建立好绑定关系。
（5）客户端投递消息到exchange。

### 声明MessageQueue

a)消费者是无法订阅或者获取不存在的MessageQueue中信息。

b)消息被Exchange接受以后，如果没有匹配的Queue，则会被丢弃

![](./../images/AMQP.png)

MessageQueue、Exchange和Binding构成了AMQP协议的核心

## 生产者发送消息

Exchange是接受生产者消息并将消息路由到消息队列的关键组件。ExchangeType和Binding决定了消息的路由规则。

![](./../images/direct-exchange.png)

### Direct类型

Direct类型会将消息中的RoutingKey与该Exchange关联的所有Binding中的BindingKey进行比较，如果相等，则发送到该Binding对应的Queue中

![](./../images/fanout-exchange.png)

### Fanout  类型

Fanout  类型，则会将消息发送给所有与该 Exchange  定义过 Binding  的所有 Queues  中去，其实是一种广播行为。

![](./../images/topic-exchange.png)

#### Topic类型

Topic类型，则会按照正则表达式，对RoutingKey与BindingKey进行匹配，如果匹配成功，则发送到对应的Queue中。

## 消费者订阅消息  

## 持久化

Rabbit MQ默认是不持久队列

设置Exchange和MessageQueue的durable属性为true，可以使得队列和Exchange持久化，但是这还不能使得队列中的消息持久化，这需要生产者在发送消息的时候，将delivery mode设置为2，只有这3个全部设置完成后，才能保证服务器重启不会对现有的队列造成影响。

持久化会对RabbitMQ的性能造成比较大的影响，可能会下降10倍不止。

## 事务

对事务的支持是AMQP协议的一个重要特性。类似数据库事物的概念

## Confirm机制

事务固然可以保证只有提交的事务，才会被服务器执行。但是这样同时也将客户端与消息服务器同步起来，这背离了消息队列解耦的本质。

Rabbit MQ提供了一个更加轻量级的机制来保证生产者可以感知服务器消息是否已被路由到正确的队列中——Confirm。

Confirm机制的最大优点在于异步，生产者在发送消息以后，即可继续执行其他任务。而服务器返回Confirm后，会触发生产者的回调函数，生产者在回调函数中处理Confirm信息。如果消息服务器发生异常，导致该消息丢失，会返回给生产者一个nack，表示消息已经丢失，这样生产者就可以通过重发消息，保证消息不丢失。

Confirm机制在性能上要比事务优越很多。

但是Confirm机制，无法进行回滚，就是一旦服务器崩溃，生产者无法得到Confirm信息，生产者其实本身也不知道该消息吃否已经被持久化，只有继续重发来保证消息不丢失，但是如果原先已经持久化的消息，并不会被回滚，这样队列中就会存在两条相同的消息，`系统需要支持去重`。