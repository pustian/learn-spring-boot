package tian.pusen.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.server.WebSocketServer;

import java.io.IOException;

@RestController
@RequestMapping("/api/ws")
public class WebSocketController {
    /**
     * 群发消息内容
     * @param message
     * @return
     */
    @GetMapping(value="/notifyMsg")
    public String sendAllMessage(@RequestParam String message){
        try {
            WebSocketServer.BroadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    /**
     * 指定会话ID发消息
     * @param message 消息内容
     * @param id 连接会话ID
     * @return
     */
    @GetMapping(value="/sendMsg")
    public String sendOneMessage(@RequestParam String message,@RequestParam String id){
        try {
            WebSocketServer.SendMessage(message,id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
