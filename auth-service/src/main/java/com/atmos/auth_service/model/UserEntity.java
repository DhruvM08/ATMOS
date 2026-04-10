package com.atmos.auth_service.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "signup")
public class UserEntity {

    @org.springframework.data.annotation.Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private List<UserProfile> userProfileList;

}
