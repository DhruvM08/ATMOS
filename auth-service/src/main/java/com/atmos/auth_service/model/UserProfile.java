package com.atmos.auth_service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfile {

    private String city;
    private String country;
    private NotificationSettings notificationSettings;
    private List<SaveLocation> savedLocations = new ArrayList<>();
}
