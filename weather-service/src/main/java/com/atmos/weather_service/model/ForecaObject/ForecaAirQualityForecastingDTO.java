package com.atmos.weather_service.model.ForecaObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class ForecaAirQualityForecastingDTO {
    private String time;
    private Integer airQualityIndex;
}
