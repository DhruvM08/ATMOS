package com.atmos.weather_service.repository;

import com.atmos.weather_service.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherData, String> {
}
