package com.davidosantos.kafka.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.andreinc.mockneat.MockNeat;

@AllArgsConstructor
@Component
@Log4j2
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final MockNeat mock = MockNeat.threadLocal();

    @Scheduled(fixedRate = 500, initialDelay = 2000)
    public void generate() {

        Users user = mock.reflect(Users.class)
                .field("id", mock.longs().range(1l, 30_000l).get())
                .field("name", mock.names())
                .field("email", mock.emails())
                .val();

        log.info("Sending message to Kafka: " + user.toString());

        kafkaTemplate.send("harryPotterBookShelf", user.getEmail(), user.toString());

    }
}
