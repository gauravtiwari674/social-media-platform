package com.socialmedia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @Column(name = "messageID")
    private int messageID;

    @ManyToOne
    @JoinColumn(name = "senderID", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverID", nullable = false)
    private User receiver;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // Default Constructor
    public Message() {
    }

    // Parameterized Constructor
    public Message(int messageID, User sender, User receiver, String messageText, LocalDateTime timestamp) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
