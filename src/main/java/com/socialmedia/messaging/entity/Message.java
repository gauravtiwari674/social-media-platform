package com.socialmedia.messaging.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;

    private int senderID;
    private int receiverID;
    private String message_text;
    private LocalDateTime timestamp;

    public int getMessageID() { return messageID; }
    public void setMessageID(int messageID) { this.messageID = messageID; }
    public int getSenderID() { return senderID; }
    public void setSenderID(int senderID) { this.senderID = senderID; }
    public int getReceiverID() { return receiverID; }
    public void setReceiverID(int receiverID) { this.receiverID = receiverID; }
    public String getMessage_text() { return message_text; }
    public void setMessage_text(String message_text) { this.message_text = message_text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}