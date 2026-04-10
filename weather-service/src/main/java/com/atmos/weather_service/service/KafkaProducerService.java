package com.atmos.weather_service.service;

import com.atmos.weather_service.event.WeatherAlertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "weather-alerts";

    public void sendWeatherAlert(WeatherAlertEvent event) {
        log.info("Sending Weather Alert Event: {}", event);
        kafkaTemplate.send(TOPIC, event);
    }
}
