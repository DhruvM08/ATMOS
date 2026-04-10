package com.atmos.auth_service.repository;

import com.atmos.auth_service.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    // Queries for users with specific notification settings
    List<UserEntity> findByUserProfileListNotificationSettingsDailyBriefingTrue();
    List<UserEntity> findByUserProfileListNotificationSettingsSeverWeatherTrue();
    List<UserEntity> findByUserProfileListNotificationSettingsRainAlarmTrue();
}
