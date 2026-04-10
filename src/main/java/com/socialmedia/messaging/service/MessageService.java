package com.socialmedia.messaging.service;

import com.socialmedia.messaging.dto.MessageDTO;
import com.socialmedia.messaging.entity.Message;
import com.socialmedia.messaging.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageDTO sendMessage(int senderID, int receiverID, String text) {
        Message message = new Message();
        message.setSenderID(senderID);
        message.setReceiverID(receiverID);
        message.setMessage_text(text);
        message.setTimestamp(LocalDateTime.now());
        Message saved = messageRepository.save(message);
        return toDTO(saved);
    }

    public List<MessageDTO> getConversation(int userID, int otherID) {
        return messageRepository.findConversation(userID, otherID)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getInbox(int receiverID) {
        return messageRepository.findByReceiverIDOrderByTimestampDesc(receiverID)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteMessage(int messageID) {
        messageRepository.deleteById(messageID);
    }

    private MessageDTO toDTO(Message m) {
        return new MessageDTO(
                m.getMessageID(),
                m.getSenderID(),
                m.getReceiverID(),
                m.getMessage_text(),
                m.getTimestamp());
    }
}