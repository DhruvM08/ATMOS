package com.atmos.weather_service.model.AccuObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuLocationKeyDTO {
    private String city;
    private String country;
    private String timeZone;
    private Double latitude;
    private Double longitude;

}
