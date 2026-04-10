package com.atmos.weather_service.model.ForecaObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecaCurrentWeatherResponse {

    @JsonProperty("current")
    private Current current;

    @Data
    public static class Current{

        @JsonProperty("time")
        private String time;

        @JsonProperty("dewPoint")
        private Double dewPoint;

        @JsonProperty("relHumidity")
        private Double humidity;

    }

}
