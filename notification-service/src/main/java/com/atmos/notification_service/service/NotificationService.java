package com.atmos.notification_service.service;

import com.atmos.notification_service.model.AlertandNotificationObject.dailyBriefingNotificationDTO;
import com.atmos.notification_service.model.AlertandNotificationObject.dailyBriefingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

        @Value("${weather.api1.key}")
        private String apiKey;

        private static final String CURRENT_WEATHER_API =
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";

    private static final String FORECAST_API =
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s";


    public dailyBriefingNotificationDTO sendDailyBriefing(String city) {
        String currentWeatherUrl = String.format(FORECAST_API, city, apiKey);
        ResponseEntity<dailyBriefingResponse> currentResponse =
                restTemplate.getForEntity(currentWeatherUrl, dailyBriefingResponse.class);

        dailyBriefingResponse currentData = currentResponse.getBody();
        LocalDate today = LocalDate.now();

        double min = currentData.getList().stream()
                .filter(f -> f.getDateTime().toLocalDate().equals(today))
                .mapToDouble(f -> f.getMain().getMinTemp())
                .min()
                .orElse(Double.NaN);

        double max = currentData.getList().stream()
                .filter(f -> f.getDateTime().toLocalDate().equals(today))
                .mapToDouble(f -> f.getMain().getMaxTemp())
                .max()
                .orElse(Double.NaN);

        dailyBriefingNotificationDTO dto = new dailyBriefingNotificationDTO();
        dto.setDescription(currentData.getList().get(0).getWeather().get(0).getDescription());
        dto.setMinTemp(min);
        dto.setMaxTemp(max);
        return dto;
    }

    public String getFormattedBriefingSms(String city) {
        dailyBriefingNotificationDTO briefing = sendDailyBriefing(city);
        String emoji = getWeatherEmoji(briefing.getDescription());

        return String.format(
            "ATMOS Daily Briefing %s\n\n" +
            "Today in %s:\n" +
            "🌡️ Max: %.1f°C | Min: %.1f°C\n" +
            "📋 Conditions: %s\n" +
            "Have a great day!",
            emoji, city, briefing.getMaxTemp(), briefing.getMinTemp(), briefing.getDescription()
        );
    }

    private String getWeatherEmoji(String description) {
        if (description == null) return "☀️";
        String desc = description.toLowerCase();
        if (desc.contains("rain") || desc.contains("drizzle")) return "🌧️";
        if (desc.contains("thunder") || desc.contains("storm")) return "⛈️";
        if (desc.contains("cloud") || desc.contains("overcast")) return "☁️";
        if (desc.contains("snow") || desc.contains("ice")) return "❄️";
        if (desc.contains("mist") || desc.contains("fog") || desc.contains("haze")) return "🌫️";
        if (desc.contains("clear") || desc.contains("sun")) return "☀️";
        return "🌈";
    }

    public Map<String, String> findUsermailandcityOfBriefing() {
        // For now, it will return empty if we don't have user data accessible.
        return Collections.emptyMap();
    }
}
