package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.PostFeedResponse;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.security.JwtUtils;
import com.socialmedia.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private User sampleUser;
    private Post samplePost;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1, "testuser", "test@example.com", "password123");
        samplePost = new Post(1, sampleUser, "Hello world!", LocalDateTime.now());
    }

    @Test
    void testCreatePost() throws Exception {
        when(postService.createPost(eq(1), any(Post.class))).thenReturn("Post created successfully!");

        mockMvc.perform(post("/api/posts/create")
                .param("userID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePost)))
                .andExpect(status().isOk())
                .andExpect(content().string("Post created successfully!"));
    }

    @Test
    void testGetAllPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(Arrays.asList(samplePost));

        mockMvc.perform(get("/api/posts/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Hello world!"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetPostsByUserId() throws Exception {
        when(postService.getPostsByUserId(1)).thenReturn(Arrays.asList(samplePost));

        mockMvc.perform(get("/api/posts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Hello world!"));
    }

    @Test
    void testSearchPosts() throws Exception {
        when(postService.searchPosts("Hello")).thenReturn(Arrays.asList(samplePost));

        mockMvc.perform(get("/api/posts/search").param("keyword", "Hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Hello world!"));
    }

    @Test
    void testGetPostById() throws Exception {
        when(postService.getPostById(1)).thenReturn(samplePost);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello world!"));
    }

    @Test
    void testUpdatePost() throws Exception {
        Post updatedPost = new Post(1, sampleUser, "Updated content", LocalDateTime.now());
        when(postService.updatePost(eq(1), any(Post.class))).thenReturn(updatedPost);

        mockMvc.perform(put("/api/posts/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    void testGetFeed() throws Exception {
        PostFeedResponse response = new PostFeedResponse(
                Arrays.asList(samplePost), 1, 1, 0, 12345
        );
        when(postService.getPaginatedFeed(any(), anyInt(), anyInt())).thenReturn(response);

        mockMvc.perform(get("/api/posts/feed")
                .param("page", "0")
                .param("limit", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[0].content").value("Hello world!"))
                .andExpect(jsonPath("$.totalCount").value(1));
    }
}
