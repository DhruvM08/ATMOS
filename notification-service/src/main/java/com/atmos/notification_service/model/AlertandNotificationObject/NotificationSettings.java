package com.atmos.notification_service.model.AlertandNotificationObject;

import lombok.Data;

@Data
public class NotificationSettings {

    private boolean dailyBriefing;
    private boolean severWeather;
    private boolean rainAlarm;

}
