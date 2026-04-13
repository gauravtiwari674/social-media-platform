package com.socialmedia.service;

import com.socialmedia.entity.Notification;
import com.socialmedia.entity.User;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(User user, String content) {
        Notification notification = new Notification();
        // Generate a random ID for now since DB script doesn't have auto-increment
        notification.setNotificationID(new Random().nextInt(1000000)); 
        notification.setUser(user);
        notification.setContent(content);
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(int userID) {
        return notificationRepository.findByUser_UserIDOrderByTimestampDesc(userID);
    }
}