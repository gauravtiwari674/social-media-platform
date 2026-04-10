package com.socialmedia.notification.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private int notificationID;
    private int userID;
    private String content;
    private LocalDateTime timestamp;

    public NotificationDTO() {}

    public NotificationDTO(int notificationID, int userID,
                           String content, LocalDateTime timestamp) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getNotificationID() { return notificationID; }
    public void setNotificationID(int notificationID) { this.notificationID = notificationID; }
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}