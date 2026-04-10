package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuHistoric24HourResponse {

    @JsonProperty("LocalObservationDateTime")
    private String observationTime;

    @JsonProperty("WeatherText")
    private String WeatherDescription;

    @JsonProperty("Temperature")
    private temperature temperature;

    @JsonProperty("RealFeelTemperature")
    private feelsLike feelsLike;

    @JsonProperty("RelativeHumidity")
    private Double humidity;

    @JsonProperty("DewPoint")
    private dewPoint dewPoint;

    @JsonProperty("Wind")
    private wind wind;

    @JsonProperty("WindGust")
    private windGust windGust;

    @JsonProperty("UVIndex")
    private Integer uvIndex;

    @JsonProperty("CloudCover")
    private Double cloudCover;


    @Data
    public static class temperature{

        @JsonProperty("Metric")
        private metric metric;

        @JsonProperty("Imperial")
        private imperial imperial;

    }

    @Data
    public static class metric{

        @JsonProperty("Value")
        private Double value;
    }

    @Data
    public static class imperial{

        @JsonProperty("Value")
        private Double value;
    }


    @Data
    public static class feelsLike{

        @JsonProperty("Metric")
        private metric metric;

        @JsonProperty("Imperial")
        private imperial imperial;

    }


    @Data
    public static class dewPoint{
        @JsonProperty("Metric")
        private metric metric;

        @JsonProperty("Imperial")
        private imperial imperial;
    }



    @Data
    public static class wind{

        @JsonProperty("Direction")
        private direction direction;

        @JsonProperty("Speed")
        private speed speed;
    }

    @Data
    public static class direction{

        @JsonProperty("Degrees")
        private Double degrees;

        @JsonProperty("Localized")
        private String direction;
    }

    @Data
    public static class speed{

        @JsonProperty("Metric")
        private metric metric;

        @JsonProperty("Imperial")
        private imperial imperial;

    }

    @Data
    public static class windGust{
        @JsonProperty("Speed")
        private speed speed;

    }







}
