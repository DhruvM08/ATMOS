package com.atmos.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherAlertEvent {
    private String alertId;
    private String cityName;
    private String alertType;
    private String message;
    private String severity;
    private LocalDateTime timestamp;
}
