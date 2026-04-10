package com.atmos.auth_service.model;

import lombok.Data;

@Data
public class NotificationSettings {

    private boolean dailyBriefing;
    private boolean severWeather;
    private boolean rainAlarm;

}
