package com.socialmedia.notification.service;

import com.socialmedia.notification.dto.NotificationDTO;
import com.socialmedia.notification.entity.Notification;
import com.socialmedia.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Async
    public void sendNotification(int userID, String content) {
        Notification notification = new Notification();
        notification.setUserID(userID);
        notification.setContent(content);
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<NotificationDTO> getNotificationsForUser(int userID) {
        return notificationRepository.findByUserIDOrderByTimestampDesc(userID)
                .stream()
                .map(n -> new NotificationDTO(
                        n.getNotificationID(),
                        n.getUserID(),
                        n.getContent(),
                        n.getTimestamp()))
                .collect(Collectors.toList());
    }

    public void deleteNotification(int notificationID) {
        notificationRepository.deleteById(notificationID);
    }
}