package com.atmos.weather_service.controller;


import com.atmos.weather_service.model.AccuObject.AccuCurrentDTO;
import com.atmos.weather_service.service.AccuService;
import com.atmos.weather_service.service.ForecaService;
import com.atmos.weather_service.service.WeatherDetailsService;
import com.atmos.weather_service.model.WeatherDetailsDTO;
import com.atmos.weather_service.model.ForecaObject.*;
import com.atmos.weather_service.model.AccuObject.*;
import com.atmos.weather_service.service.KafkaProducerService;
import com.atmos.weather_service.event.WeatherAlertEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/weather")
public class WeatherController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AccuService accuService;

    @Autowired
    private ForecaService forecaService;

    @Autowired
    private WeatherDetailsService weatherDetailsService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("triggerAlert")
    public ResponseEntity<String> triggerAlert(@RequestBody WeatherAlertEvent event) {
        if (event.getAlertId() == null) event.setAlertId(UUID.randomUUID().toString());
        if (event.getTimestamp() == null) event.setTimestamp(LocalDateTime.now());
        kafkaProducerService.sendWeatherAlert(event);
        return new ResponseEntity<>("Weather alert sent to Kafka topic", HttpStatus.ACCEPTED);
    }


    @GetMapping("getCurrentWeather")
    public ResponseEntity<AccuCurrentDTO> getInfo(@RequestParam String city) {
        return new ResponseEntity<>(accuService.getCurrentWeather(city), HttpStatus.OK);
    }

    @GetMapping("getForecast5Daily")
    public ResponseEntity<List<AccuForecastDailyDTO>> getForecast5Daily(@RequestParam String city) {
        return new ResponseEntity<>(accuService.getForecast5Daily(city), HttpStatus.OK);
    }

    @GetMapping("getTodayHeadline")
    public ResponseEntity<TodayHeadlineDTO> getTodayHeadline(@RequestParam String city) {
        return new ResponseEntity<>(accuService.getTodayHeadline(city), HttpStatus.OK);
    }

    @GetMapping("getForecast12Hourly")
    public ResponseEntity<List<AccuForecast12HourlyDTO>> getForecast12Hourly(@RequestParam String city) {
        return new ResponseEntity<>(accuService.getAccuForecast12Hourly(city), HttpStatus.OK);
    }

    @GetMapping("getTop50Cities")
    public ResponseEntity<List<AccuTop50CitiesDTO>> getTop50Cities() {
        return new ResponseEntity<>(accuService.getTop50Cities(), HttpStatus.OK);
    }

    @GetMapping("getForecaLocationKey")
    public ResponseEntity<String> getLocationKey(@RequestParam String city) {
        return new ResponseEntity<>(forecaService.getLocationKey(city), HttpStatus.OK);
    }

    @GetMapping("getForeca72HourForecasting")
    public ResponseEntity<List<Foreca72HourForecastingDTO>> getForeca72HourForecasting(@RequestParam String city) {
        return new ResponseEntity<>(forecaService.get72HourForecasting(city), HttpStatus.OK);
    }

    @GetMapping("getLocationInfoByCity")
    public ResponseEntity<AccuLocationKeyDTO> getLocationInfoByCity(@RequestParam String city) {
        return new ResponseEntity<>(accuService.getLocationInfoByCity(city), HttpStatus.OK);
    }

    @GetMapping("getForecaAirQuality84Hourly")
    public ResponseEntity<List<ForecaAirQualityForecastingDTO>> getForeca84HourlyAirQuality(@RequestParam String city) {
        return new ResponseEntity<>(forecaService.getForecaAQI(city), HttpStatus.OK);
    }

    @GetMapping("getCurrentWeatherDetails")
    public ResponseEntity<WeatherDetailsDTO> getCurrentWeatherDetails(@RequestParam String city) {
        return new ResponseEntity<>(weatherDetailsService.getWeatherDetails(city), HttpStatus.OK);
    }

}
