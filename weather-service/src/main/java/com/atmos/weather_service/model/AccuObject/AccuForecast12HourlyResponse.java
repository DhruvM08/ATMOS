package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuForecast12HourlyResponse {

    @JsonProperty("DateTime")
    private String dateTime;

    @JsonProperty("IconPhrase")
    private String iconPhrase;

    @JsonProperty("HasPrecipitation")
    private Boolean hasPrecipitation;

    @JsonProperty("IsDaylight")
    private Boolean isDayLight;

    @JsonProperty("Temperature")
    private Temperature temperature;

    @JsonProperty("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;

    @JsonProperty("DewPoint")
    private DewPoint dewPoint;

    @JsonProperty("Wind")
    private Wind wind;

    @JsonProperty("WindGust")
    private WindGusts windGusts;

    @JsonProperty("RelativeHumidity")
    private Double relativeHumidity;

    @JsonProperty("Visibility")
    private Visibility visibility;

    @JsonProperty("UVIndex")
    private Integer uvIndex;

    @JsonProperty("UVIndexText")
    private String uvIndexText;

    @JsonProperty("CloudCover")
    private Double cloudCover;

    @Data
    public static class Visibility {

        @JsonProperty("Value")
        private Double value;

    }

    @Data
    public static class RealFeelTemperature {

        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Phrase")
        private String description;
    }

    @Data
    public static class DewPoint {
        @JsonProperty("Value")
        private Double value;
    }

    @Data
    public static class WindGusts {
        @JsonProperty("Speed")
        private Speed speed;
    }

    @Data
    public static class Wind {

        @JsonProperty("Speed")
        private Speed speed;

        @JsonProperty("Direction")
        private Directions direction;
    }

    @Data
    public static class Speed {
        @JsonProperty("Value")
        private Double value;
    }

    @Data
    public static class Directions {

        @JsonProperty("Localized")
        private String direction;
        @JsonProperty("Degrees")

        private Double degrees;
    }

    @Data
    public static class Temperature {

        @JsonProperty("Value")
        private Double value;
    }

}
