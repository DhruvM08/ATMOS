package com.atmos.weather_service.model.WeatherStackObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherStackCurrentDTO {

    private String observationTime;
    private String sunRise;
    private String sunSet;
    private String moonRise;
    private String moonSet;
    private String moonPhase;
    private Double moonIllumination;
    private Double co;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm2P5;
    private Double pm10;
    private Double visibility;

}
