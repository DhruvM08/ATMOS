package com.atmos.auth_service.model;

import lombok.Data;

@Data
public class SignupDTO {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private String phoneNumber;

}
