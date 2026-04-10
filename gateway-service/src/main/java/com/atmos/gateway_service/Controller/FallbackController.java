package com.atmos.gateway_service.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/auth")
    public ResponseEntity<String> authFallback() {
        return new ResponseEntity<>("Auth service is currently unavailable. Please try again later.", 
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/notification")
    public ResponseEntity<String> notificationFallback() {
        return new ResponseEntity<>("Notification service is currently unavailable. Please try again later.", 
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/weather")
    public ResponseEntity<String> weatherFallback() {
        return new ResponseEntity<>("Weather service is currently unavailable. Please try again later.", 
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/astronomy")
    public ResponseEntity<String> astronomyFallback() {
        return new ResponseEntity<>("Astronomy service is currently unavailable. Please try again later.", 
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/news")
    public ResponseEntity<String> newsFallback() {
        return new ResponseEntity<>("News service is currently unavailable. Please try again later.", 
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}
