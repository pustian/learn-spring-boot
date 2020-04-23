package tian.pusen;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
