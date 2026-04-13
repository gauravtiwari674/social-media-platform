package com.socialmedia.controller;

import com.socialmedia.entity.Notification;
import com.socialmedia.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userID}")
    public List<Notification> getNotifications(@PathVariable int userID) {
        return notificationService.getNotificationsForUser(userID);
    }
}


