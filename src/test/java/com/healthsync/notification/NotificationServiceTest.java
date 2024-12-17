package com.healthsync.notification;

import com.healthsync.notification.model.Notification;
import com.healthsync.notification.repository.NotificationRepository;
import com.healthsync.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {
    private NotificationRepository notificationRepository;
    private JavaMailSender mailSender;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        mailSender = mock(JavaMailSender.class);
        notificationService = new NotificationService(notificationRepository, mailSender);
    }

    @Test
    void testCreateNotification() {
        Notification notification = new Notification();
        notification.setPatientEmail("mkaushalya96@gmail.com");
        when(notificationRepository.save(notification)).thenReturn(notification);

        Notification savedNotification = notificationService.createNotification(notification);
        assertNotNull(savedNotification);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testGetAllNotification() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotification();
        assertEquals(1, result.size());
        verify(notificationRepository, times(1)).findAll();
    }

//    @Test
//    void testSetMailSender() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime oneHourLater = now.plusHours(1);
//        List<Notification> notifications = new ArrayList<>();
//        Notification notification = new Notification();
//        notification.setPatientEmail("mkaushalya96@gmail.com");
//     //   notification.setAppointmentTime(now);
//        notification.setAppointmentTime(now.plusMinutes(2));
//        notification.setSent(false);
//        notifications.add(notification);
//
//        when(notificationRepository.findBySentFalseAndAppointmentTimeBetween(now, oneHourLater)).thenReturn(notifications);
//
//        notificationService.setMailSender();
//
//        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
//        verify(notificationRepository, times(1)).save(captor.capture());
//        assertTrue(captor.getValue().isSent());
//    }
}
