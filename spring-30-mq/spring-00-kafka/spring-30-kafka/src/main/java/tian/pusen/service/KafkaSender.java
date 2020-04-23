package tian.pusen.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import tian.pusen.entity.Messages;

import java.util.Date;
import java.util.Random;

@Service
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    public void send() {
        Messages message = new Messages();
        message.setId(System.currentTimeMillis());
        message.setMsg(RandomStringUtils.randomAlphanumeric(10) );
        message.setSendTime(new Date());
        ListenableFuture<SendResult<String, String>> test0 = kafkaTemplate.send("newtopic", gson.toJson(message));
    }
}
