package com.atmos.notification_service.model.AlertandNotificationObject;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class dailyBriefingNotificationDTO {

    private String Description;
    private Double minTemp;
    private Double maxTemp;
    private Map<String, List<String>> emailList;
}
