package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuTop50CitiesDTO {

    private String locationKey;
    private String observationDateTime;
    private String cityName;
    private String countryID;
    private String countryName;
    private String timeZoneCode;
    private String timeZoneName;
    private Integer gmtOffSet;
    private Float latitude;
    private Float longitude;
    private String weatherText;
    private Boolean isDayTime;
    private Integer temperature;
    private String temperatureUnit;

}
