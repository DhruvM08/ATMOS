package com.atmos.Astronomy_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "astronomy_history")
public class AstronomyData {

    @Id
    private String id;
    private String city;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moonPhase;
    private Double moonIlluminance;
    private Double visibility;
    private LocalDateTime timestamp;

}
