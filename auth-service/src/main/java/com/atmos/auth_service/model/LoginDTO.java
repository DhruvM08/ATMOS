package com.atmos.auth_service.model;

import lombok.Data;

@Data
public class LoginDTO {

    private String name;
    private String email;
    private UserProfile userProfile;
    private String token;
    private String role;
}
