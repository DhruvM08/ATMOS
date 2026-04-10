package com.atmos.weather_service.model.WeatherAstronomyDataObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAstronomyDTO {

    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moonPhase;
    private Double moonIlluminance;
    private Double visibility;

}
