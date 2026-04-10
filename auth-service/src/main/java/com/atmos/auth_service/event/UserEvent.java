package com.atmos.auth_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {
    private String userId;
    private String email;
    private String phoneNumber;
    private String eventType; // REGISTRATION, LOGIN_FAILED, PASSWORD_CHANGED
}
