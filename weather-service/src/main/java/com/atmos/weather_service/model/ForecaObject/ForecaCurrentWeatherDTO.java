package com.atmos.weather_service.model.ForecaObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecaCurrentWeatherDTO {

    private String time;
    private Double dewPoint;
    private Double humidity;

}
