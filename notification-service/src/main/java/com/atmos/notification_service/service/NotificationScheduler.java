package com.atmos.notification_service.service;

import com.atmos.notification_service.client.UserClient;
import com.atmos.notification_service.model.NotificationHistory;
import com.atmos.notification_service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationScheduler {

    @Autowired
    private UserClient userClient;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private NotificationRepository notificationRepository;

    // Scheduled to run every morning at 7:00 AM
    @Scheduled(cron = "0 0 7 * * *")
    public void sendDailyBriefings() {
        log.info("Starting Daily Briefing execution...");
        
        try {
            List<Map<String, Object>> users = userClient.getAllUsers();
            
            for (Map<String, Object> user : users) {
                try {
                    String email = (String) user.get("email");
                    String phone = (String) user.get("phoneNumber");
                    
                    // Safely extract city from userProfileList
                    List<Map<String, Object>> profiles = (List<Map<String, Object>>) user.get("userProfileList");
                    if (profiles != null && !profiles.isEmpty() && phone != null) {
                        String city = (String) profiles.get(0).get("city");
                        
                        if (city != null) {
                            String briefingSms = notificationService.getFormattedBriefingSms(city);
                            twilioService.sendSms(phone, briefingSms);
                            log.info("Daily Briefing sent via SMS to {} for city: {}", email, city);

                            // Save to database history
                            NotificationHistory history = NotificationHistory.builder()
                                    .type("WEATHER_BRIEFING")
                                    .title("Daily Weather Summary: " + city)
                                    .message(briefingSms)
                                    .recipient(email)
                                    .timestamp(LocalDateTime.now())
                                    .build();
                            notificationRepository.save(history);
                        }
                    }
                } catch (Exception e) {
                    log.error("Error processing briefing for a user: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch users for daily briefing: {}", e.getMessage());
        }
    }
}
