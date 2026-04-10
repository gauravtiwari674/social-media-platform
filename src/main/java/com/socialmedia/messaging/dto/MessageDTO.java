package com.socialmedia.messaging.dto;

import java.time.LocalDateTime;

public class MessageDTO {

    private int messageID;
    private int senderID;
    private int receiverID;
    private String message_text;
    private LocalDateTime timestamp;

    public MessageDTO() {}

    public MessageDTO(int messageID, int senderID, int receiverID,
                      String message_text, LocalDateTime timestamp) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message_text = message_text;
        this.timestamp = timestamp;
    }

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