package com.socialmedia.service;

import com.socialmedia.entity.*;
import com.socialmedia.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock MessageRepository msgRepo;
    @Mock UserRepository userRepo;
    @Mock FriendshipRepository friendRepo;
    @InjectMocks MessageService service;

    // Minimal dummy objects configured concisely as class variables
    User u1 = new User(1, "A", "a@email.com", "pass");
    User u2 = new User(2, "B", "b@email.com", "pass");
    Message msg = new Message();

    @Test
    void testSendMessage() {
        when(userRepo.findById(1)).thenReturn(Optional.of(u1));
        when(userRepo.findById(2)).thenReturn(Optional.of(u2));
        when(friendRepo.existsByUser1AndUser2AndStatus(u1, u2, FriendshipStatus.accepted)).thenReturn(true);

        assertEquals("Message sent successfully!", service.sendMessage(1, 2, 100, "Hi"));
        verify(msgRepo).save(any(Message.class)); 
    }

    @Test
    void testGetMessages() {
        // Combines verification of all basic READ operations
        when(msgRepo.findByReceiver_UserIDOrderByTimestampDesc(2)).thenReturn(List.of(msg));
        when(msgRepo.findBySender_UserIDOrderByTimestampDesc(1)).thenReturn(List.of(msg));
        when(msgRepo.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(u1, u2, u1, u2)).thenReturn(List.of(msg));

        when(userRepo.findById(1)).thenReturn(Optional.of(u1));
        when(userRepo.findById(2)).thenReturn(Optional.of(u2));

        assertEquals(1, service.getInbox(2).size());
        assertEquals(1, service.getSent(1).size());
        assertEquals(1, service.getConversation(1, 2).size());
    }

    @Test
    void testDeleteAndCount() {
        // Delete Check
        when(msgRepo.existsById(100)).thenReturn(true);
        assertEquals("Message deleted successfully!", service.deleteMessage(100));

        // Count Check
        when(userRepo.findById(1)).thenReturn(Optional.of(u1));
        when(userRepo.findById(2)).thenReturn(Optional.of(u2));
        when(msgRepo.countBySenderAndReceiver(any(), any())).thenReturn(5L);
        assertEquals(10L, service.countMessagesBetweenUsers(1, 2));
    }
}
