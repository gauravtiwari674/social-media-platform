package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.entity.Like;
import com.socialmedia.security.JwtUtils;
import com.socialmedia.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LikeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LikeControllerTest {

    static {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private Like sampleLike;

    @BeforeEach
    void setUp() {
        sampleLike = new Like();
        sampleLike.setLikeID(1);
    }

    @Test
    void testLikePost() throws Exception {
        when(likeService.likePost(eq(1), eq(1), any(Like.class))).thenReturn("Post liked successfully!");

        mockMvc.perform(post("/api/likes/add")
                .param("postID", "1")
                .param("userID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleLike)))
                .andExpect(status().isOk())
                .andExpect(content().string("Post liked successfully!"));
    }

    @Test
    void testGetLikeCount() throws Exception {
        when(likeService.getLikeCount(1)).thenReturn(10L);

        mockMvc.perform(get("/api/likes/count/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testRemoveLike() throws Exception {
        when(likeService.removeLike(1, 1)).thenReturn("Like removed successfully!");

        mockMvc.perform(delete("/api/likes/remove")
                .param("postID", "1")
                .param("userID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Like removed successfully!"));
    }

    @Test
    void testCheckLike() throws Exception {
        when(likeService.isPostLikedByUser(1, 1)).thenReturn(true);

        mockMvc.perform(get("/api/likes/check")
                .param("postID", "1")
                .param("userID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testToggleLike() throws Exception {
        when(likeService.toggleLike(1, 1)).thenReturn("Post liked!");

        mockMvc.perform(post("/api/likes/toggle")
                .param("postID", "1")
                .param("userID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post liked!"));
    }

    @Test
    void testGetLikesByPost() throws Exception {
        when(likeService.getLikesByPost(1)).thenReturn(Arrays.asList(sampleLike));

        mockMvc.perform(get("/api/likes/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].likeID").value(1));
    }
}
