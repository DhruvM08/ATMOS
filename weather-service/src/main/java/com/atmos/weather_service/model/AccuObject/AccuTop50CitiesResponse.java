package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuTop50CitiesResponse {


    @JsonProperty("Key")
    private String locationKey;

    @JsonProperty("LocalizedName")
    private String cityName;

    @JsonProperty("Country")
    private Country country;

    @JsonProperty("TimeZone")
    private TimeZone timeZone;

    @JsonProperty("GeoPosition")
    private GeoPosition geoPosition;

    @JsonProperty("LocalObservationDateTime")
    private String time;

    @JsonProperty("WeatherText")
    private String weatherText;

    @JsonProperty("IsDayTime")
    private Boolean isDayTime;

    @JsonProperty("Temperature")
    private Temperature temperature;

    @Data
    public static class TimeZone{
        @JsonProperty("Code")
        private String code;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("GmtOffset")
        private Integer gmtOffset;

    }

     @Data
    public static class GeoPosition{

        @JsonProperty("Latitude")
        private Float latitude;
        @JsonProperty("Longitude")
        private Float longitude;
    }

    @Data
    public static class Temperature{

        @JsonProperty("Metric")
        private UnitValues metric;
        @JsonProperty("Imperial")
        private UnitValues imperial;

    }

    @Data
    public static class UnitValues{
        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Unit")
        private String unit;
    }

    @Data
    public static class Country{

        @JsonProperty("ID")
        private String id;

        @JsonProperty("LocalizedName")
        private String countryName;
    }


}
