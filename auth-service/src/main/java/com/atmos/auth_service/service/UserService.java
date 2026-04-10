package com.atmos.auth_service.service;

import com.atmos.auth_service.model.*;
import com.atmos.auth_service.event.UserEvent;
import com.atmos.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public String registerUser(SignupDTO signup) {
        UserEntity userEntity = new UserEntity();

        if (userRepository.existsByEmail(signup.getEmail().trim())) {
            return "User is already registered!!!";
        }

        userEntity.setName(signup.getName().trim());
        userEntity.setEmail(signup.getEmail().trim());

        if (signup.getPassword().trim().equals(signup.getConfirmPassword().trim())) {
            userEntity.setUserProfileList(new ArrayList<>());
            userEntity.setPassword(encoder.encode(signup.getPassword()));
            userEntity.setRole(signup.getRole() != null ? signup.getRole() : "USER");
            userEntity.setPhoneNumber(signup.getPhoneNumber() != null ? signup.getPhoneNumber().trim() : null);
            userRepository.save(userEntity);

            // Trigger Registration Event
            UserEvent registrationEvent = UserEvent.builder()
                    .userId(userEntity.getId())
                    .email(userEntity.getEmail())
                    .phoneNumber(userEntity.getPhoneNumber())
                    .eventType("REGISTRATION")
                    .build();
            kafkaProducerService.sendUserEvent(registrationEvent);

            return "Registration Successful";
        }

        return "Passwords do not match!!!";
    }

    public LoginDTO loginUser(UserEntity userEntity) {
        LoginDTO loginDTO = new LoginDTO();
        Optional<UserEntity> userBody = userRepository.findByEmail(userEntity.getEmail());

        if (userBody.isEmpty()) {
            return null; // Don't throw exception, just return null
        }

        UserEntity userData = userBody.get();

        if (encoder.matches(userEntity.getPassword(), userData.getPassword())) {
            loginDTO.setName(userData.getName());
            loginDTO.setEmail(userData.getEmail());
            loginDTO.setRole(userData.getRole());
            loginDTO.setToken(jwtService.generateToken(userData.getEmail(), userData.getRole()));
            if (userData.getUserProfileList() != null && !userData.getUserProfileList().isEmpty()) {
                loginDTO.setUserProfile(userData.getUserProfileList().get(0));
            }
            return loginDTO;
        } else {
            return null; // Don't throw exception, just return null
        }
    }

    public String updateUserProfile(String emailId, UserProfile userProfile) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByEmail(emailId);
            if (optionalUser.isEmpty()) {
                return "User not found";
            }

            UserEntity userData = optionalUser.get();
            if (userData.getUserProfileList() == null) {
                userData.setUserProfileList(new ArrayList<>());
            }

            UserProfile profile;
            if (userData.getUserProfileList().isEmpty()) {
                profile = new UserProfile();
                userData.getUserProfileList().add(profile);
            } else {
                profile = userData.getUserProfileList().get(0);
            }

            profile.setCity(userProfile.getCity());
            profile.setCountry(userProfile.getCountry());

            if (profile.getNotificationSettings() == null) {
                profile.setNotificationSettings(new NotificationSettings());
            }

            NotificationSettings ns = profile.getNotificationSettings();
            NotificationSettings incomingNs = userProfile.getNotificationSettings();
            ns.setDailyBriefing(incomingNs.isDailyBriefing());
            ns.setSeverWeather(incomingNs.isSeverWeather());
            ns.setRainAlarm(incomingNs.isRainAlarm());

            if (profile.getSavedLocations() == null) {
                profile.setSavedLocations(new ArrayList<>());
            }
            profile.getSavedLocations().clear();
            for (SaveLocation incoming : userProfile.getSavedLocations()) {
                SaveLocation loc = new SaveLocation();
                loc.setId(incoming.getId());
                loc.setName(incoming.getName());
                loc.setCity(incoming.getCity());
                loc.setCountry(incoming.getCountry());
                profile.getSavedLocations().add(loc);
            }

            userRepository.save(userData);
            return "Profile updated successfully";
        } catch (Exception e) {
            return "Error updating profile";
        }
    }

    public String verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail(), user.getRole() != null ? user.getRole() : "USER");
        }
        return "Rejected";
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getUsersByCity(String city) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUserProfileList() != null && !u.getUserProfileList().isEmpty()
                        && city.equalsIgnoreCase(u.getUserProfileList().get(0).getCity()))
                .toList();
    }
}
