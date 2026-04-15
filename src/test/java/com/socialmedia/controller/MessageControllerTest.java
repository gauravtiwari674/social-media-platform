package com.socialmedia.controller;

import com.socialmedia.entity.Message;
import com.socialmedia.security.JwtUtils;
import com.socialmedia.service.MessageService;
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

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MessageControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean MessageService service;
    @MockBean JwtUtils jwtUtils;

    @Test
    void testSendAndReads() throws Exception {
        // Send Msg Test
        when(service.sendMessage(anyInt(), anyInt(), anyInt(), anyString())).thenReturn("Success!");
        mockMvc.perform(post("/api/messages/send")
                        .param("senderID", "1")
                        .param("receiverID", "2")
                        .param("messageID", "10")
                        .content("Hi"))
               .andExpect(status().isOk())
               .andExpect(content().string("Success!"));

        // Read Inbox Test
        Message msg = new Message(); 
        msg.setMessageID(10);
        when(service.getInbox(1)).thenReturn(List.of(msg));
        
        mockMvc.perform(get("/api/messages/inbox/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].messageID").value(10));
    }

    @Test
    void testDeleteAndCount() throws Exception {
        when(service.deleteMessage(10)).thenReturn("Deleted!");
        mockMvc.perform(delete("/api/messages/10")).andExpect(content().string("Deleted!"));

        when(service.countMessagesBetweenUsers(1, 2)).thenReturn(5L);
        mockMvc.perform(get("/api/messages/count").param("user1ID", "1").param("user2ID", "2"))
               .andExpect(content().string("5"));
    }
}
