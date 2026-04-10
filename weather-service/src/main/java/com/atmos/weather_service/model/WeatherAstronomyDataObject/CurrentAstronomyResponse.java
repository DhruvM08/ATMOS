package com.atmos.weather_service.model.WeatherAstronomyDataObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAstronomyResponse {

    @JsonProperty("astronomy")
    private Astronomy astronomy;

    @Data
    public static class Astronomy {
        @JsonProperty("astro")
        private Astro astro;
    }

    @Data
    public static class Astro {

        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        @JsonProperty("moonrise")
        private String moonrise;

        @JsonProperty("moonset")
        private String moonset;

        @JsonProperty("moon_phase")
        private String moonPhase;

        @JsonProperty("moon_illumination")
        private Double moonIllumination;

    }

}
