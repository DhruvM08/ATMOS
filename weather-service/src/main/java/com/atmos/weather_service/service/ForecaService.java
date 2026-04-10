package com.atmos.weather_service.service;

import com.atmos.weather_service.model.ForecaObject.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForecaService {

    @Value("${weather.api3.key}")
    private String forecaApiKey;

    static final String FORECA_LOCATION_KEY = "https://pfa.foreca.com/api/v1/location/search/?q=%s&lang=en&token=%s";
    static final String FORECA_HOURLY_FORECASTING = "https://pfa.foreca.com/api/v1/forecast/hourly/%s?dataset=full&lang=en&token=%s&periods=120";
    static final String FORECA_AIRQUALITY_FORECASTING = "https://pfa.foreca.com/api/v1/air-quality/forecast/hourly/%s?lang=en&token=%s&periods=85";
    static final String FORECA_CURRENT_WEATHER = "https://pfa.foreca.com/api/v1/current/%s&lang=en&windunit=KMH&token=%s&dataset=full";

    private final RestTemplate restTemplate;

    public ForecaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Foreca72HourForecastingDTO> get72HourForecasting(String city) {
        String forecaLocationKey = getLocationKey(city);
        String finalUrl = String.format(FORECA_HOURLY_FORECASTING, forecaLocationKey, forecaApiKey);
        // Foreca72HourForecastingResponse.Forecast forecast =
        // restTemplate.getForObject(finalUrl,
        // Foreca72HourForecastingResponse.Forecast.class);
        Foreca72HourForecastingResponse forecastingResponse = restTemplate.getForObject(finalUrl,
                Foreca72HourForecastingResponse.class);

        List<Foreca72HourForecastingDTO> list = new ArrayList<>();
        ZonedDateTime date = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX");

        for (int i = 0; i < forecastingResponse.getForecastList().size(); i++) {

            ZonedDateTime incomingDateTime = ZonedDateTime.parse(forecastingResponse.getForecastList().get(i).getTime(),
                    formatter);

//            if (!date.toLocalDate().equals(incomingDateTime.toLocalDate())) {
                Foreca72HourForecastingResponse.Forecast response1 = forecastingResponse.getForecastList().get(i);
                Foreca72HourForecastingDTO foreca72HourForecastingDTO = new Foreca72HourForecastingDTO();
                foreca72HourForecastingDTO.setTemperature((int) Math.round(response1.getTemperature()));
                foreca72HourForecastingDTO.setDateTime(response1.getTime());
                foreca72HourForecastingDTO.setWindDirection(response1.getWindDirection());
                foreca72HourForecastingDTO
                        .setFeelsLikeTemperature((int) Math.round(response1.getFeelsLikeTemperature()));
                foreca72HourForecastingDTO.setWindSpeed(response1.getWindSpeed());
                foreca72HourForecastingDTO.setWindGust(response1.getWindGust());
                foreca72HourForecastingDTO.setWindDirectionDegree(response1.getWindDirDegree());
                foreca72HourForecastingDTO.setPressure(response1.getPressure());
                foreca72HourForecastingDTO.setDescription(response1.getSymbolPhrase());
                foreca72HourForecastingDTO.setDewPoint(response1.getDewPoint());
                foreca72HourForecastingDTO.setUvIndex(response1.getUvIndex());
                foreca72HourForecastingDTO.setVisibility(((response1.getVisibility()) / 1000));
                foreca72HourForecastingDTO.setPrecipitationType(response1.getPrecipitationType());
                foreca72HourForecastingDTO.setHumidityChance(response1.getHumidity());
                foreca72HourForecastingDTO.setPrecipitationProbability(response1.getPrecipProbability());

                list.add(foreca72HourForecastingDTO);
//            }

        }

        return list;
    }

    public String getLocationKey(String city) {
        String finalUrl = String.format(FORECA_LOCATION_KEY, city, forecaApiKey);
        ForecaLocationKeyResponse response = restTemplate.getForObject(finalUrl, ForecaLocationKeyResponse.class);
        String finalLocationKey = response.getLocations().getFirst().getLocationKey();
        return finalLocationKey;
    }

    public List<ForecaAirQualityForecastingDTO> getForecaAQI(String city) {
        String finalUrl = String.format(FORECA_AIRQUALITY_FORECASTING,getLocationKey(city),forecaApiKey);
        ForecaAirQualityForecastingResponse response = restTemplate.getForObject(finalUrl,
                ForecaAirQualityForecastingResponse.class);
        List<ForecaAirQualityForecastingResponse.Forecast> list = response.getForecastList();
        List<ForecaAirQualityForecastingDTO> forecaAirQualityList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ForecaAirQualityForecastingDTO dto = new ForecaAirQualityForecastingDTO();
            ForecaAirQualityForecastingResponse.Forecast response1 = list.get(i);
            dto.setTime(response1.getTime());
            dto.setAirQualityIndex(response1.getAirQualityIndex());
            forecaAirQualityList.add(dto);
        }

        return forecaAirQualityList;

    }

    public ForecaCurrentWeatherDTO getCurrentWeather(String city) {

        String locationKey = getLocationKey(city);
        String finalUrl = String.format(FORECA_CURRENT_WEATHER,locationKey,forecaApiKey);

        ForecaCurrentWeatherResponse forecaCurrentWeatherResponse = restTemplate.getForObject(finalUrl, ForecaCurrentWeatherResponse.class);
        ForecaCurrentWeatherDTO current = new ForecaCurrentWeatherDTO();
        current.setTime(forecaCurrentWeatherResponse.getCurrent().getTime());
        current.setHumidity(forecaCurrentWeatherResponse.getCurrent().getHumidity());
        current.setDewPoint(forecaCurrentWeatherResponse.getCurrent().getDewPoint());

        return current;
    }

}
