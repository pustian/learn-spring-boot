package tian.pusen.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.entity.Messages;

@RestController
public class Sender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    @PostMapping("/user/send")
    public String saveUser(@RequestBody Messages message){
        String useJson = gson.toJson(message);
        kafkaTemplate.send("userTopic1", useJson);
//        kafkaTemplate.send("userTopic", user);
        return "OK";
    }
}
