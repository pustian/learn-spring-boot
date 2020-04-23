package tian.pusen.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import tian.pusen.entity.User;

@RestController
public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, Object> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();


//     curl -H "Content-Type:application/json" -H "Data_Type:msg" -X POST \
//     --data '{	"id": 1,	"name": "pus",	"age": 18,	"desc": "Well DONE" }' \
//     http://127.0.0.1:8080/user/send
    @PostMapping("/user/send")
    public boolean saveUser(@RequestBody User user){
//        String useJson = gson.toJson(user);
//        kafkaTemplate.send("userTopic", useJson);
        kafkaTemplate.send("userTopic", user);


//        logger.info("kafka sendMessage start");
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("userTopic", user);
//        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
//            }
//
//            @Override
//            public void onSuccess(SendResult<Integer, String> result) {
//                logger.info("kafka sendMessage success topic = {}, data = {}",topic, data);
//            }
//        });
//        logger.info("kafka sendMessage end");

        return true;
    }
}
