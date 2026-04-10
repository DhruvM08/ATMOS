package com.atmos.weather_service.model.ForecaObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Foreca72HourForecastingDTO {

    private String dateTime;
    private String description;
    private Double humidityChance;
    private Double dewPoint;
    private Integer uvIndex;
    private Double pressure;
    private Double precipitationProbability;
    private Double visibility;
    private String precipitationType;
    private Integer temperature;
    private Integer feelsLikeTemperature;
    private Double windSpeed;
    private Double windGust;
    private Double windDirectionDegree;
    private String windDirection;
    private String maxTemp;
    private String minTemp;

}
