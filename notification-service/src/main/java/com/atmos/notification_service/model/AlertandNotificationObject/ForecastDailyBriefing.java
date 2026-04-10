package com.atmos.notification_service.model.AlertandNotificationObject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForecastDailyBriefing {

    private Double minTemp;
    private Double maxTemp;
    private LocalDateTime localDateTime;

}
