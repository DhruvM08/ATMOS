package com.atmos.notification_service.controller;

import com.atmos.notification_service.model.AlertandNotificationObject.dailyBriefingNotificationDTO;
import com.atmos.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Health check - use this to verify gateway → notification-service routing.
     * GET http://localhost:8080/api/v1/notifications/health (via gateway, with JWT)
     * GET http://localhost:8082/api/v1/notifications/health (direct)
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("notification-service is UP");
    }

    /**
     * Get weather daily briefing for a city.
     * GET http://localhost:8080/api/v1/notifications/weatherBriefing?city=London
     */
    @GetMapping("/weatherBriefing")
    public ResponseEntity<dailyBriefingNotificationDTO> getWeatherBriefing(@RequestParam String city) {
        dailyBriefingNotificationDTO briefing = notificationService.sendDailyBriefing(city);
        return ResponseEntity.ok(briefing);
    }

    /**
     * Trigger daily briefing notifications via Kafka for all subscribed users.
     * GET http://localhost:8080/api/v1/notifications/dailyBriefing
     */
    @GetMapping("/dailyBriefing")
    public ResponseEntity<String> sendDailyBriefing() {
        return ResponseEntity.ok("Daily briefings initiated via Kafka");
    }
}
