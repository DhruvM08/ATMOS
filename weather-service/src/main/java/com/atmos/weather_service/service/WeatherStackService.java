package com.atmos.weather_service.service;

import com.atmos.weather_service.model.WeatherStackObject.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherStackService {

    @Autowired
    private AccuService accuService;

    @Value("${weather.api4.key}")
    private String weatherStackApiKey;

    private final RestTemplate restTemplate;

    public WeatherStackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String WEATHERSTACK_CURRENT_DATA = "https://api.weatherstack.com/current?query=%s,%s&units=m&access_key=%s";

    public WeatherStackCurrentDTO getWeatherStackCurrent(String city)
    {
        Double latitude = accuService.getLatandLongByCity(city).get("latitude");
        Double longitude = accuService.getLatandLongByCity(city).get("longitude");
        String finalUrl = String.format(WEATHERSTACK_CURRENT_DATA,latitude,longitude,weatherStackApiKey);
        WeatherStackCurrentResponse  weatherStackCurrentResponse = restTemplate.getForObject(finalUrl, WeatherStackCurrentResponse.class);
        WeatherStackCurrentDTO weatherStackCurrentDTO = new WeatherStackCurrentDTO();

        if(weatherStackCurrentResponse!=null) {

            weatherStackCurrentDTO.setPm2P5(weatherStackCurrentResponse.getCurrent().getAirQuality().getPm2P5());
            weatherStackCurrentDTO.setObservationTime(weatherStackCurrentResponse.getCurrent().getObservationTime());
            weatherStackCurrentDTO.setPm10(weatherStackCurrentResponse.getCurrent().getAirQuality().getPm10());
            weatherStackCurrentDTO.setCo(weatherStackCurrentResponse.getCurrent().getAirQuality().getCo());
            weatherStackCurrentDTO.setMoonPhase(weatherStackCurrentResponse.getCurrent().getAstro().getMoonPhase());
            weatherStackCurrentDTO.setMoonSet(weatherStackCurrentResponse.getCurrent().getAstro().getMoonSet());
            weatherStackCurrentDTO.setO3(weatherStackCurrentResponse.getCurrent().getAirQuality().getO3());
            weatherStackCurrentDTO.setNo2(weatherStackCurrentResponse.getCurrent().getAirQuality().getNo2());
            weatherStackCurrentDTO.setMoonRise(weatherStackCurrentResponse.getCurrent().getAstro().getMoonRise());
            weatherStackCurrentDTO.setVisibility(weatherStackCurrentResponse.getCurrent().getVisibility());
            weatherStackCurrentDTO.setSunRise(weatherStackCurrentResponse.getCurrent().getAstro().getSunRise());
            weatherStackCurrentDTO.setSunSet(weatherStackCurrentResponse.getCurrent().getAstro().getSunSet());
            weatherStackCurrentDTO.setSo2(weatherStackCurrentResponse.getCurrent().getAirQuality().getSo2());
            weatherStackCurrentDTO.setMoonIllumination(weatherStackCurrentResponse.getCurrent().getAstro().getMoonIllumination());

        }

        return weatherStackCurrentDTO;
    }

}

