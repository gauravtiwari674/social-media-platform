package com.socialmedia.notification.controller;

import com.socialmedia.notification.dto.NotificationDTO;
import com.socialmedia.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userID}")
    public String getNotifications(@PathVariable int userID, Model model) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userID);
        model.addAttribute("notifications", notifications);
        model.addAttribute("userID", userID);
        return "notification/notifications";
    }

    @DeleteMapping("/{notificationID}")
    @ResponseBody
    public String deleteNotification(@PathVariable int notificationID) {
        notificationService.deleteNotification(notificationID);
        return "deleted";
    }
}