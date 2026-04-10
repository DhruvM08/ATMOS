package com.atmos.weather_service.model.ForecaObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecaLocationKeyResponse {

    @JsonProperty("locations")
    private List<Location> locations;

    @Data
    public static class Location{

        @JsonProperty("id")
        private String locationKey;
    }

}
