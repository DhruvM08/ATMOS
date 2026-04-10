package com.atmos.weather_service.model.OpenWeatherObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HourlyDataDTO {
    private String hour;
    private Float temperature;
    private Integer humidity;
    private Float windSpeed;
}
















