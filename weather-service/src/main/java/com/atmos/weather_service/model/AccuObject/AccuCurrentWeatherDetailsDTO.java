package com.atmos.weather_service.model.AccuObject;

import lombok.Data;

@Data
public class AccuCurrentWeatherDetailsDTO {

   private Integer temperature;
   private Integer feelsLike;
   private Double cloudCover;
   private Double precipitation;
   private Double windSpeed;
   private Double windGustSpeed;
   private String windDirection;
   private Double windDirectionDegrees;
   private Integer uvIndex;
   private String uvIndexDescription;
   private Double pressure;
   private String pressureTendency;


}
