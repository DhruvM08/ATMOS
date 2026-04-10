package com.atmos.weather_service.service;

import com.atmos.weather_service.event.WeatherAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AlertService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${weather.api5.key}")
    private String apiKey;

    private static final String ALERTS_URL = "https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=1&aqi=no&alerts=yes";

    // Scheduled to run every 30 minutes to check for real-time government alerts
    @Scheduled(fixedRate = 1800000)
    public void checkForGovAlerts() {
        // You can expand this list with more cities or pull from a database
        String[] citiesToCheck = {"Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata"};

        for (String city : citiesToCheck) {
            try {
                String url = String.format(ALERTS_URL, apiKey, city);
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);

                if (response != null && response.containsKey("alerts")) {
                    Map<String, Object> alertsObj = (Map<String, Object>) response.get("alerts");
                    List<Map<String, Object>> alertList = (List<Map<String, Object>>) alertsObj.get("alert");

                    if (alertList != null && !alertList.isEmpty()) {
                        for (Map<String, Object> alert : alertList) {
                            String event = (String) alert.get("event");
                            String severity = (String) alert.get("severity");
                            String headline = (String) alert.get("headline");
                            String desc = (String) alert.get("desc");

                            log.warn("REAL-TIME GOV ALERT FOUND for {}: {} ({})", city, event, severity);

                            WeatherAlertEvent alertEvent = new WeatherAlertEvent();
                            alertEvent.setAlertId(UUID.randomUUID().toString());
                            alertEvent.setCityName(city);
                            alertEvent.setAlertType(event);
                            alertEvent.setMessage(desc != null ? desc : headline);
                            alertEvent.setSeverity(severity);
                            alertEvent.setTimestamp(LocalDateTime.now());

                            kafkaProducerService.sendWeatherAlert(alertEvent);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Failed to fetch gov alerts for {}: {}", city, e.getMessage());
            }
        }
    }
}
