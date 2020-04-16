浏览器登陆 http://localhost:8080/ 或是 http://localhost:8080/index
查看浏览器的控制台 console 可以看到 以下消息
client 页面
发送消息给指定的用户       === OK
显示实时消息              === OK
显示当前在线人数，当前的session列表 OK

admin 页面
可以认为admin是一个固定session的页面，固定sessionID 未能实现
后期用用户代替session.
显示在线人数和用户 --client已有
发送通知消息      --index已有
获取所有的用户交互信息 
    ---方案1 服务器 onMessage 中统一处理发送给admin用户一次。
    ---方案2 通过日志kafka 所有数据进入kafka。 与方案1 一样的效果 

参考代码：
   https://github.com/niezhiliang/springbootwebsocket.git  

session需要改为用户名。 
    在redis存储session。

承压大小压力没有试过
    分布式的话需要考虑 websocket 连接的状态稳定性。引起的问题。
    多用户问题。

测试步骤：
1, 打开多个 http://localhost:8080/client 并建立连接
2,    http://localhost:8080/index 建立连接
3, http://localhost:8080/index 建立连接,发送通知
4, client 可以看到多个列表，可以互相通信

实现: 
    onopen, onmessage 这些js方法中增加显示的js函数