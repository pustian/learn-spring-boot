package tian.pusen.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端建立连接通道开启......");
        channelGroup.add(ctx.channel() );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端断开连接通道关闭......");
        channelGroup.remove(ctx.channel());
    }

    /**
     * 客户端发消息会触发, 服务器接收到数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("服务器收到消息: {}", textWebSocketFrame.toString());
        sendAllMessage();
    }

    private void sendMessage(ChannelHandlerContext ctx) {
        String message = "你好" + ctx.channel().localAddress() + "给固定的人法消息";
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message) );
    }
    private void sendAllMessage() {
        String message = "警告： 现在发布天气警告";
        channelGroup.writeAndFlush(new TextWebSocketFrame(message) );
    }
    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
