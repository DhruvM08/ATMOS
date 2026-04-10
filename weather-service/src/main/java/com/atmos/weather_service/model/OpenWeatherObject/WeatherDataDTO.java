package com.atmos.weather_service.model.OpenWeatherObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "weatherData")
public class WeatherDataDTO {
    private String city;
    private String country;
    private Float lat;
    private Float lon;
    private Float temperature;
    private Float feelsLike;
    private String condition;
    private String description;
    private Integer humidity;
    private Float windSpeed;
    private String icon;
    private String iconCode; // Raw icon code from OpenWeatherMap (e.g., "01d", "01n")
    private Long sunrise; // Unix timestamp
    private Long sunset; // Unix timestamp
    private Integer timezoneOffset; // UTC offset in seconds for the city
    private Double min_temp;
    private Double max_temp;
    private Integer pressure;
    private Integer visibility;
    private Float dewPoint;
    private Float moonPhase;
    private List<ForecastDayDTO> forecast;
    private List<HourlyDataDTO> hourlyData;
    private Map<String, List<String>> finalEmaillist;
}
