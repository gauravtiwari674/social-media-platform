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
public class NotificationServiceTest {

    @Mock NotificationRepository noteRepo;
    @Mock UserRepository userRepo;
    @InjectMocks NotificationService service;

    User u1 = new User(1, "A", "a@email.com", "pass");
    Notification note = new Notification();

    @Test
    void testCreateNotification() {
        when(userRepo.findById(1)).thenReturn(Optional.of(u1));
        
        assertEquals("Notification created successfully!", service.createNotification(1, "Alert"));
        verify(noteRepo).save(any(Notification.class));
    }

    @Test
    void testReadNotifications() {
        when(noteRepo.findByUser_UserIDOrderByTimestampDesc(1)).thenReturn(List.of(note));
        when(noteRepo.countByUser_UserID(1)).thenReturn(5L);

        assertEquals(1, service.getUserNotifications(1).size());
        assertEquals(5L, service.countNotifications(1));
    }

    @Test
    void testDeleteNotification() {
        when(noteRepo.existsById(100)).thenReturn(true);
        
        assertEquals("Notification deleted successfully!", service.deleteNotification(100));
        verify(noteRepo).deleteById(100);
    }
}
