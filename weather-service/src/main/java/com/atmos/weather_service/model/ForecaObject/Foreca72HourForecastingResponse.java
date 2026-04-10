package com.atmos.weather_service.model.ForecaObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foreca72HourForecastingResponse {

    @JsonProperty("forecast")
    private List<Forecast> forecastList;

    @Data
    public static class Forecast{

        @JsonProperty("time")
        private String time;

        //Description of Weather
        @JsonProperty("symbolPhrase")
        private String symbolPhrase;

        // % (Relative Humidity)
        @JsonProperty("relHumidity")
        private Double humidity;

        //degree C
        @JsonProperty("dewPoint")
        private Double dewPoint;

        @JsonProperty("uvIndex")
        private Integer uvIndex;

        // %
        @JsonProperty("precipProb")
        private Double precipProbability;

        //Sea level (hPa)
        @JsonProperty("pressure")
        private Double pressure;

        //M(meter)
        @JsonProperty("visibility")
        private Double visibility;

        //Degree C
        @JsonProperty("temperature")
        private Double temperature;

        @JsonProperty("feelsLikeTemp")
        private Double feelsLikeTemperature;

        @JsonProperty("windSpeed")
        private Double windSpeed;

        @JsonProperty("windGust")
        private Double windGust;

        @JsonProperty("windDir")
        private Double windDirDegree;

        @JsonProperty("windDirString")
        private String windDirection;

        @JsonProperty("precipType")
        private String precipitationType;



    }
}

//
//0° corresponds to North (N)
//
//90° corresponds to East (E)
//
//180° corresponds to South (S)
//
//270° corresponds to West (W)
