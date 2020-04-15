package tian.pusen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class IndexController {

    @GetMapping(value = {"index"})
    public String index() {
        return "index";
    }
    // 对应的WebSocketServer中的 @ServerEndpoint(value = "/websocket")
    @GetMapping(value = {"websocket"})
    public String websocket() {
        return "websocket";
    }
}
