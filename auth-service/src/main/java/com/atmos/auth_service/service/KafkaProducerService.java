package com.atmos.auth_service.service;

import com.atmos.auth_service.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "auth-alerts";

    public void sendUserEvent(UserEvent event) {
        log.info("Sending User Event: {}", event);
        kafkaTemplate.send(TOPIC, event);
    }
}
