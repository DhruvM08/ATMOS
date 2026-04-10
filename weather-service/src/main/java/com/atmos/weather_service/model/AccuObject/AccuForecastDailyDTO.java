package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuForecastDailyDTO {

    private String date;
    private String sunRise;
    private String sunSet;
    private String moonRise;
    private String moonSet;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer minFeelTemp;
    private Integer maxFeelTemp;
    private Double hoursOfSun;
    private String airQualityCategory;
    private Integer airQualityValue;
    private String moonPhase;
    private String dayShortPhrase;
    private String dayLongPhrase;
    private String nightShortPhrase;
    private String nightLongPhrase;

}
