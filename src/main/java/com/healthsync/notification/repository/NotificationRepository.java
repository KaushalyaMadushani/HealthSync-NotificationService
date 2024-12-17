package com.healthsync.notification.repository;

import com.healthsync.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findBySentFalseAndAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
}
