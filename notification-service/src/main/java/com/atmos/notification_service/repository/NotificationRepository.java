package com.atmos.notification_service.repository;

import com.atmos.notification_service.model.NotificationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationHistory, String> {
}
