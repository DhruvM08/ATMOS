package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuHistoric24HourDTO {

    private Integer temperature;
    private Integer feelsLikeTemperature;
    private Double humidity;
    private Integer uvIndex;
    private Double cloudCover;
    private Double dewPoint;
    private Double windSpeed;
    private Double windGustSpeed;

}
