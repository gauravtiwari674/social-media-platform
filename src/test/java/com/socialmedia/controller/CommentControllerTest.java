package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.entity.Comment;
import com.socialmedia.security.JwtUtils;
import com.socialmedia.service.CommentService;
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

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    static {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment sampleComment;

    @BeforeEach
    void setUp() {
        sampleComment = new Comment();
        sampleComment.setCommentID(1);
        sampleComment.setCommentText("Test comment");
    }

    @Test
    void testAddComment() throws Exception {
        when(commentService.addComment(eq(1), eq(1), any(Comment.class))).thenReturn("Comment added successfully!");

        mockMvc.perform(post("/api/comments/add")
                .param("postID", "1")
                .param("userID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleComment)))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment added successfully!"));
    }

    @Test
    void testGetCommentsByPostId() throws Exception {
        when(commentService.getCommentsByPostId(1)).thenReturn(Arrays.asList(sampleComment));

        mockMvc.perform(get("/api/comments/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commentText").value("Test comment"));
    }

    @Test
    void testDeleteComment() throws Exception {
        when(commentService.deleteComment(1)).thenReturn("Comment deleted successfully!");

        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted successfully!"));
    }

    @Test
    void testUpdateComment() throws Exception {
        when(commentService.updateComment(eq(1), any(Comment.class))).thenReturn("Comment updated successfully!");

        mockMvc.perform(put("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleComment)))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment updated successfully!"));
    }

    @Test
    void testGetCommentCountByPost() throws Exception {
        when(commentService.getCommentCountByPost(1)).thenReturn(5L);

        mockMvc.perform(get("/api/comments/count/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
