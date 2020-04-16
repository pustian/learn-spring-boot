package tian.pusen.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Session> SessionSet
            = new CopyOnWriteArraySet<Session>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
    public void onOpen(Session session) {
        this.session = session;
        int cnt = OnlineCount.incrementAndGet();
        SessionSet.add(session);
        logger.info("有新连接加入！当前在线人数为{}", cnt);
        SendMessage(session, "连接成功年哦");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);  //从set中删除
        int cnt = OnlineCount.decrementAndGet();
        logger.info("有用户退出,当前在线人数为:{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("客户端用户消息:{}", message);
        SendMessage(session, "收到消息内容"+message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误{}, Session id{}", error.getMessage(), session.getId() );
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     */
    public static void SendMessage(Session session, String message) {
        String textMessage = String.format("%s (From Server，Session ID=%s)",message, session.getId());
        try {
            session.getBasicRemote().sendText(textMessage);
        } catch (IOException e) {
            logger.error("发送消息出错 {}", e);
        }
//
//        for (WebSocketServer item : webSocketSet) {
//            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
//            // this.session.getBasicRemote().sendText(message);
//            item.session.getAsyncRemote().sendText(message);//异步发送消息.
//        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if(session.isOpen()){
                SendMessage(session, message);
            }
        }
    }

    /**
     * 指定SessionId 发送消息
     * @param message
     * @param sessionId
     * @throws IOException
     */
    public static void SendMessage(String message,String sessionId) throws IOException {
        Session session = null;
        for(Session s: SessionSet) {
            if(s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }
        if(null != session) {
            SendMessage(session, message);
        } else {
            logger.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }
}
