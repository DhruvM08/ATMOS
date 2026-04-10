package com.atmos.Astronomy_service.controller;

import com.atmos.Astronomy_service.model.CurrentAstronomyDTO;
import com.atmos.Astronomy_service.service.WeatherAstronomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astronomy")
public class AstronomyController {

    @Autowired
    private WeatherAstronomyService astronomyService;

    @GetMapping("/getAstronomyData")
    public ResponseEntity<CurrentAstronomyDTO> getAstronomyData(@RequestParam String city) {
        return ResponseEntity.ok(astronomyService.getCurrentAstronomyDTO(city));
    }
}
