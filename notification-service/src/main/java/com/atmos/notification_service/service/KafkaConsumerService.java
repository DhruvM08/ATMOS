package com.atmos.notification_service.service;

import com.atmos.notification_service.client.UserClient;
import com.atmos.notification_service.event.WeatherAlertEvent;
import com.atmos.notification_service.event.UserEvent;
import com.atmos.notification_service.model.NotificationHistory;
import com.atmos.notification_service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private UserClient userClient;

    @KafkaListener(topics = "weather-alerts", groupId = "notification-group")
    public void consumeWeatherAlert(WeatherAlertEvent event) {
        log.info("********** RECEIVED WEATHER ALERT **********");
        log.info("City: {}", event.getCityName());
        log.info("Type: {}", event.getAlertType());
        log.info("Message: {}", event.getMessage());
        log.info("Severity: {}", event.getSeverity());
        log.info("********************************************");

        NotificationHistory history = NotificationHistory.builder()
                .type("WEATHER")
                .title("Weather Alert: " + event.getAlertType())
                .message(event.getMessage())
                .recipient(event.getCityName())
                .timestamp(LocalDateTime.now())
                .build();
        notificationRepository.save(history);
        log.info("Saved weather alert to database");

        // Real-time Distribution: Fetch all users in this city and send SMS
        try {
            List<Map<String, Object>> usersInCity = userClient.getUsersByCity(event.getCityName());
            if (usersInCity != null && !usersInCity.isEmpty()) {
                String smsMessage = String.format("ATMOS EMERGENCY: %s alert for %s. %s", 
                        event.getAlertType(), event.getCityName(), event.getMessage());
                
                for (Map<String, Object> user : usersInCity) {
                    String phone = (String) user.get("phoneNumber");
                    if (phone != null) {
                        twilioService.sendSms(phone, smsMessage);
                        log.info("Emergency SMS sent to {} ({})", user.get("email"), phone);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to distribute real-time alerts: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "auth-alerts", groupId = "notification-group")
    public void consumeUserEvent(UserEvent event) {
        log.info("********** RECEIVED USER EVENT ***********");
        log.info("User ID: {}", event.getUserId());
        log.info("Email: {}", event.getEmail());
        log.info("Type: {}", event.getEventType());
        log.info("******************************************");

        NotificationHistory history = NotificationHistory.builder()
                .type("AUTH")
                .title("Auth Event: " + event.getEventType())
                .message("User event occurred for " + event.getEmail())
                .recipient(event.getEmail())
                .timestamp(LocalDateTime.now())
                .build();
        notificationRepository.save(history);
        log.info("Saved auth event to database");

        if ("REGISTRATION".equalsIgnoreCase(event.getEventType())) {
            log.info("Sending Welcome SMS to: {} at {}", event.getEmail(), event.getPhoneNumber());
            if (event.getPhoneNumber() != null) {
                String welcomeMessage = String.format(
                    "Welcome to ATMOS, %s! 🌍\n" +
                    "Your registration is successful. You will now receive real-time weather alerts and daily morning briefings.",
                    event.getEmail().split("@")[0]
                );
                twilioService.sendSms(event.getPhoneNumber(), welcomeMessage);
            }
        }
    }
}
