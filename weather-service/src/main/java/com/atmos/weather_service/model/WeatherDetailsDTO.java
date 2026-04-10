package com.atmos.weather_service.model;

import com.atmos.weather_service.model.AccuObject.AccuCurrentWeatherDetailsDTO;
import com.atmos.weather_service.model.ForecaObject.ForecaAirQualityForecastingDTO;
import com.atmos.weather_service.model.ForecaObject.ForecaCurrentWeatherDTO;
import com.atmos.weather_service.model.WeatherAstronomyDataObject.CurrentAstronomyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDetailsDTO {

    private AccuCurrentWeatherDetailsDTO accuCurrentWeatherDetailsDTO;
    private CurrentAstronomyDTO currentAstronomyDTO;
    private ForecaCurrentWeatherDTO forecaCurrentWeatherDTO;
    private ForecaAirQualityForecastingDTO forecaAirQualityForecastingDTO;

  
}
