package com.healthsync.notification.service;

import com.healthsync.notification.model.Notification;
import com.healthsync.notification.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public Notification createNotification(Notification notification){
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotification(){
        return notificationRepository.findAll();
    }

    //@Scheduled(fixedRate = 86400000)
    @Scheduled(fixedRate = 60000)
    public void setMailSender(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);

        List<Notification> notifications = notificationRepository.findBySentFalseAndAppointmentTimeBetween(now, oneHourLater);
        for (Notification notification : notifications){
            sendEmail(notification.getPatientEmail(),"Appointment Reminder", "Dear Patient, this is a friendly reminder for your appointment scheduled on "
                    + notification.getAppointmentTime() + " ." );
            notification.setSent(true);
            notificationRepository.save(notification);
        }
    }

    private void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
