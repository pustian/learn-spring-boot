WebSocket 服务
 有两种方案可以建立起WebSocket服务，一种是基于Spring 的 spring-websocket，一种是基于 java 的 websocket-api。
spring-websocket
 WebSocketHandler 接口定义了服务端处理WebSocket消息要做的一系列事情。相比直接实现WebSocketHandler，更为简单的方法是扩展AbstractWebSocketHandler，这是WebSocketHandler的一个抽象实现
websocket-api
 通过注解 @ServerEndpoint("/ws")

浏览器登陆 http://localhost:8080/ 或是 http://localhost:8080/index
查看浏览器的控制台 console 可以看到 以下消息
```
恭喜：您的浏览器支持WebSocket
(index):36 Socket 已打开
(index):41 连接成功年哦 (From Server，Session ID=1)
(index):41 收到消息内容消息发送测试(From Client) (From Server，Session ID=1)
```
刷新页面或是新开的页面 Session ID=1 是不一样的
通过http发送工具调用 sendAll/sendOne 可以看到 浏览器页面有新增的响应
```bash
curl http://localhost:8080/api/ws/sendAll?message=Tianpusen
```
浏览器控制台（每个页面的控制台都会有,ID 对应刚才的id）
```
Tianpusen (From Server，Session ID=2)
```

```bash
curl 'http://localhost:8080/api/ws/sendOne?message=Tian&id=1'
```
浏览器控制台只有id为1的页面会有
```
Tianpusen (From Server，Session ID=1)
```
