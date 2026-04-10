package com.atmos.weather_service.service;

import com.atmos.weather_service.model.WeatherAstronomyDataObject.CurrentAstronomyResponse;
import com.atmos.weather_service.model.WeatherAstronomyDataObject.CurrentAstronomyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherAstronomyService {

    private final RestTemplate restTemplate;

    public WeatherAstronomyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${weather.api5.key}")
    private String astronomyKey;

    private static final String WEATHER_ASTRONOMY_DATA = "https://api.weatherapi.com/v1/astronomy.json?key=%s&q=%s&dt=%s";
    private static final String WEATHER_CURRENT_DATA = "https://api.weatherapi.com/v1/current.json?key=%s&q=%s";

    public LocalDate getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localDate;
    }

    public CurrentAstronomyDTO getCurrentAstronomyDTO(String city) {
        String finalUrl = String.format(WEATHER_ASTRONOMY_DATA, astronomyKey, city, getCurrentDate());
        CurrentAstronomyResponse response = restTemplate.getForObject(finalUrl, CurrentAstronomyResponse.class);

        String currentUrl = String.format(WEATHER_CURRENT_DATA, astronomyKey, city);
        java.util.Map<String, Object> currentResponse = restTemplate.getForObject(currentUrl, java.util.Map.class);

        CurrentAstronomyDTO currentAstronomyDTO = new CurrentAstronomyDTO();

        CurrentAstronomyResponse.Astro astro = response.getAstronomy().getAstro();

        currentAstronomyDTO.setSunrise(astro.getSunrise());
        currentAstronomyDTO.setSunset(astro.getSunset());
        currentAstronomyDTO.setMoonPhase(astro.getMoonPhase());
        currentAstronomyDTO.setMoonrise(astro.getMoonrise());
        currentAstronomyDTO.setMoonset(astro.getMoonset());
        currentAstronomyDTO.setMoonIlluminance(astro.getMoonIllumination());

        if (currentResponse != null && currentResponse.containsKey("current")) {
            java.util.Map<String, Object> current = (java.util.Map<String, Object>) currentResponse.get("current");
            if (current.containsKey("vis_km")) {
                currentAstronomyDTO.setVisibility(((Number) current.get("vis_km")).doubleValue());
            }
        }

        return currentAstronomyDTO;

    }

}
