package com.atmos.weather_service.model.OpenWeatherObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForecastDayDTO {
    private String day;
    private String date;
    private Float tempMin;
    private Float tempMax;
    private String condition;
    private String icon;
    private String description;
    private Integer humidity;
    private Float windSpeed;
}
















