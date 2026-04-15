package com.socialmedia.service;

import com.socialmedia.entity.Comment;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    static {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CommentService commentService;

    private User sampleUser;
    private Post samplePost;
    private Comment sampleComment;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1, "testuser", "test@example.com", "password123");
        samplePost = new Post(1, sampleUser, "Test content", LocalDateTime.now());
        sampleComment = new Comment(1, samplePost, sampleUser, "Great post!", LocalDateTime.now());
    }

    @Test
    void testAddComment_Success() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));

        String result = commentService.addComment(1, 1, new Comment());

        assertEquals("Comment added successfully!", result);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(notificationService, times(1)).createNotification(any(), anyString());
    }

    @Test
    void testAddComment_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        String result = commentService.addComment(1, 1, new Comment());

        assertEquals("Error: Post or User not found!", result);
    }

    @Test
    void testGetCommentsByPostId() {
        when(commentRepository.findByPost_PostID(1)).thenReturn(Arrays.asList(sampleComment));
        List<Comment> result = commentService.getCommentsByPostId(1);
        assertEquals(1, result.size());
        assertEquals("Great post!", result.get(0).getCommentText());
    }

    @Test
    void testDeleteComment_Success() {
        when(commentRepository.existsById(1)).thenReturn(true);
        String result = commentService.deleteComment(1);
        assertEquals("Comment deleted successfully!", result);
        verify(commentRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteComment_NotFound() {
        when(commentRepository.existsById(1)).thenReturn(false);
        String result = commentService.deleteComment(1);
        assertEquals("Error: Comment not found!", result);
    }

    @Test
    void testUpdateComment_Success() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(sampleComment));
        Comment updatedComment = new Comment();
        updatedComment.setCommentText("Updated text");

        String result = commentService.updateComment(1, updatedComment);

        assertEquals("Comment updated successfully!", result);
        verify(commentRepository, times(1)).save(sampleComment);
        assertEquals("Updated text", sampleComment.getCommentText());
    }

    @Test
    void testGetCommentCountByPost() {
        when(commentRepository.countByPost_PostID(1)).thenReturn(10L);
        long count = commentService.getCommentCountByPost(1);
        assertEquals(10L, count);
    }
}
