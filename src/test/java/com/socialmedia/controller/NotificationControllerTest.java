package com.socialmedia.controller;

import com.socialmedia.entity.Notification;
import com.socialmedia.security.JwtUtils;
import com.socialmedia.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NotificationControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean NotificationService service;
    @MockBean JwtUtils jwtUtils;

    @Test
    void testCreateAndRead() throws Exception {
        // Create Note Test
        when(service.createNotification(anyInt(), anyString())).thenReturn("Success!");
        mockMvc.perform(post("/api/notifications/create")
                        .param("userID", "1")
                        .param("content", "Alert"))
               .andExpect(status().isOk())
               .andExpect(content().string("Success!"));

        // Read Note Test
        Notification note = new Notification(); 
        note.setNotificationID(50);
        when(service.getUserNotifications(1)).thenReturn(List.of(note));
        
        mockMvc.perform(get("/api/notifications/user/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].notificationID").value(50));
    }

    @Test
    void testDeleteAndCount() throws Exception {
        when(service.deleteNotification(50)).thenReturn("Deleted!");
        mockMvc.perform(delete("/api/notifications/50")).andExpect(content().string("Deleted!"));

        when(service.countNotifications(1)).thenReturn(3L);
        mockMvc.perform(get("/api/notifications/count/1")).andExpect(content().string("3"));
    }
}
