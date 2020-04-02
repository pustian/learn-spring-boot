package tian.pusen.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // curl '192.168.1.204:8080/message/send?topic=pus&message=IloveO'
    @GetMapping("/message/send")
    public Boolean send(@RequestParam(name = "topic", required = true) String topic
            , @RequestParam(name = "message", required = true) String message) {
        kafkaTemplate.send(topic, message);
        return true;
    }

}
