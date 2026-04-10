package com.atmos.weather_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "weather_history")
public class WeatherData {

    @Id
    private String id;
    private String city;
    private WeatherDetailsDTO details;
    private LocalDateTime timestamp;

}
