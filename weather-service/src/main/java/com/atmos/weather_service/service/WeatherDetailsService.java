package com.atmos.weather_service.service;
import com.atmos.weather_service.service.WeatherAstronomyService;
import com.atmos.weather_service.model.WeatherData;
import com.atmos.weather_service.model.WeatherDetailsDTO;
import com.atmos.weather_service.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherDetailsService {

    @Autowired
    private AccuService accuService;

    @Autowired
    private ForecaService forecaService;

    @Autowired
    private WeatherAstronomyService weatherAstronomyService;

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherDetailsDTO getWeatherDetails(String city) {

        WeatherDetailsDTO weatherDetailsDTO = new WeatherDetailsDTO();
        weatherDetailsDTO.setAccuCurrentWeatherDetailsDTO(accuService.getAccuWeatherCurrentDetails(city));
        weatherDetailsDTO.setForecaCurrentWeatherDTO(forecaService.getCurrentWeather(city));
        weatherDetailsDTO.setCurrentAstronomyDTO(weatherAstronomyService.getCurrentAstronomyDTO(city));
        weatherDetailsDTO.setForecaAirQualityForecastingDTO(forecaService.getForecaAQI(city).get(0));

        // Store in MongoDB
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setDetails(weatherDetailsDTO);
        weatherData.setTimestamp(java.time.LocalDateTime.now());
        weatherRepository.save(weatherData);

        return weatherDetailsDTO;
    }

}
