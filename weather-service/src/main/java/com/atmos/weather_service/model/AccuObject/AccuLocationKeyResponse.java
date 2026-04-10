package com.atmos.weather_service.model.AccuObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuLocationKeyResponse {

    @JsonProperty("Key")
    private String locationKey;

    @JsonProperty("LocalizedName")
    private String cityName;

    @JsonProperty("Country")
    private Country country;

    @JsonProperty("GeoPosition")
    private GeoPosition geoPosition;

    @JsonProperty("TimeZone")
    private TimeZone timeZone;

    @Data
    public static class GeoPosition {

        @JsonProperty("Latitude")
        private Double latitude;
        @JsonProperty("Longitude")
        private Double longitude;
    }

    @Data
    public static class TimeZone{
        @JsonProperty("Name")
        private String timeZoneName;
    }

    @Data
    public static class Country{
        @JsonProperty("LocalizedName")
        private String countryName;
    }
}
