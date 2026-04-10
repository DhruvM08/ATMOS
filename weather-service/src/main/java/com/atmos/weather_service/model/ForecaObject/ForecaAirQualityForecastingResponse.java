package com.atmos.weather_service.model.ForecaObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecaAirQualityForecastingResponse {


    @JsonProperty("forecast")
    private List<Forecast> forecastList;

    @Data
    public static class Forecast{

        @JsonProperty("time")
        private String time;
        @JsonProperty("AQI")
        private Integer airQualityIndex;

    }



}
