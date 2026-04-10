package com.atmos.notification_service.model.AlertandNotificationObject;

import lombok.Data;

import java.util.List;

@Data
public class dailyBriefingResponse {

    private List<ForecastBriefing> list;

}
