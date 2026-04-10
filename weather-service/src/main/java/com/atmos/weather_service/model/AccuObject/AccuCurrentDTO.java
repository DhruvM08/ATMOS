package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuCurrentDTO {

    private String weatherText;
    private Boolean isDayTime;
    private Double relativeHumidity;
    private Integer temperature;
    private Integer feelsLike;
    private Integer uvIndex;
    private Double pressure;
    private Double windSpeed;
    private String direction;
    private String uvIndexText;
    private Double cloudCover;
    private Double windGustSpeed;
    private Double directionDegrees;
    private Double dewPoint;
    private Double visibility;
    private String pressureTendency;
    private Double precipitation;


}
