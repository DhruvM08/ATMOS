package com.atmos.weather_service.model.WeatherStackObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherStackCurrentResponse {

    @JsonProperty("current")
    private  Current current;

    @Data
    public static class Current{

        @JsonProperty("observation_time")
        private String observationTime;

        @JsonProperty("astro")
        private Astro astro;

        @JsonProperty("air_quality")
        private airQuality airQuality;

        @JsonProperty("visibility")
        private Double visibility;


    }

    @Data
    public  static class Astro{

        @JsonProperty("sunrise")
        private String sunRise;
        @JsonProperty("sunset")
        private String sunSet;
        @JsonProperty("moonrise")
        private String moonRise;
        @JsonProperty("moonset")
        private String moonSet;
        @JsonProperty("moon_phase")
        private String moonPhase;
        @JsonProperty("moon_illumination")
        private Double moonIllumination;

    }

    @Data
    public static class airQuality{

        @JsonProperty("co")
        private Double co;

        @JsonProperty("no2")
        private Double no2;

        @JsonProperty("o3")
        private Double o3;

        @JsonProperty("so2")
        private Double so2;

        @JsonProperty("pm2_5")
        private Double pm2P5;

        @JsonProperty("pm10")
        private Double pm10;

    }

}
