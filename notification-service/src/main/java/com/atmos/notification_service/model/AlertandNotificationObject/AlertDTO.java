package com.atmos.notification_service.model.AlertandNotificationObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlertDTO {

    // Here we only Need to Display the Summary of Alert by Gov in Respective City
    // by Hourly Time ::

    @JsonProperty("Area")
    private List<Area> areaList;

    @JsonProperty("Description")
    private Description description;

    @JsonProperty("MobileLink")
    private String mobileLink;

    @JsonProperty("AlarmLevel")
    private String alarmLevel;

    @Data
    public static class Area {

        @JsonProperty("Summary")
        private String summary;

        @JsonProperty("Name")
        private String name;
    }

    @Data
    public static class Description {

        @JsonProperty("English")
        private String english;
    }

}
