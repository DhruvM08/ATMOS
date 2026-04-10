package com.atmos.notification_service.model.AlertandNotificationObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ForecastBriefing {

    private List<Weather> weather;
    private Main main;

    @JsonProperty("dt_txt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Data
    public static class Weather {

        @JsonProperty("description")
        private String Description;
    }

    @Data
    public static class Main {

        @JsonProperty("temp_min")
        private Double minTemp;

        @JsonProperty("temp_max")
        private Double maxTemp;
    }

}
