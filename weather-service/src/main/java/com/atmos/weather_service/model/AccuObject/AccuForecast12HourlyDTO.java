package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuForecast12HourlyDTO {

    private String dateTime;
    private String iconPhrase;
    private Boolean hasPrecipitation;
    private Boolean isDayLight;
    private Integer temperature;
    private Integer feelsLike;
    private String feelsLikeDescription;
    private Double visibility;
    private Integer uvIndex;
    private Double dewPoint;
    private String windDirection;
    private Double windSpeed;
    private Double windDegree;
    private Double windGustSpeed;
    private Double cloudCover;
    private Double humidity;
    private Integer aqi;


}
