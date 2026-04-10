package com.atmos.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "notifications")
public class NotificationHistory {

    @Id
    private String id;
    private String type; // e.g., "WEATHER" or "AUTH"
    private String title;
    private String message;
    private String recipient;
    private LocalDateTime timestamp;

}
