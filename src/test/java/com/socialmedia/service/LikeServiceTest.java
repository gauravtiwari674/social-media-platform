package com.socialmedia.service;

import com.socialmedia.entity.Like;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.LikeRepository;
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
public class LikeServiceTest {

    static {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LikeService likeService;

    private User sampleUser;
    private Post samplePost;
    private Like sampleLike;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1, "testuser", "test@example.com", "password123");
        samplePost = new Post(1, sampleUser, "Test content", LocalDateTime.now());
        sampleLike = new Like(1, samplePost, sampleUser, LocalDateTime.now());
    }

    @Test
    void testLikePost_Success() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.empty());

        String result = likeService.likePost(1, 1, new Like());

        assertEquals("Post liked successfully!", result);
        verify(likeRepository, times(1)).save(any(Like.class));
        verify(notificationService, times(1)).createNotification(any(), anyString());
    }

    @Test
    void testLikePost_AlreadyLiked() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.of(sampleLike));

        String result = likeService.likePost(1, 1, new Like());

        assertEquals("Error: User already liked this post!", result);
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void testLikePost_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        String result = likeService.likePost(1, 1, new Like());

        assertEquals("Error: Post or User not found!", result);
    }

    @Test
    void testRemoveLike_Success() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.of(sampleLike));

        String result = likeService.removeLike(1, 1);

        assertEquals("Like removed successfully!", result);
        verify(likeRepository, times(1)).deleteByPostAndUser(samplePost, sampleUser);
    }

    @Test
    void testRemoveLike_NotLiked() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.empty());

        String result = likeService.removeLike(1, 1);

        assertEquals("Error: Like does not exist!", result);
    }

    @Test
    void testToggleLike_AddLike() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.empty());

        String result = likeService.toggleLike(1, 1);

        assertEquals("Post liked!", result);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void testToggleLike_RemoveLike() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(likeRepository.findByPostAndUser(samplePost, sampleUser)).thenReturn(Optional.of(sampleLike));

        String result = likeService.toggleLike(1, 1);

        assertEquals("Like removed!", result);
        verify(likeRepository, times(1)).delete(sampleLike);
    }

    @Test
    void testGetLikeCount() {
        when(likeRepository.countByPost_PostID(1)).thenReturn(5L);
        long count = likeService.getLikeCount(1);
        assertEquals(5L, count);
    }

    @Test
    void testGetLikesByPost() {
        when(likeRepository.findByPost_PostID(1)).thenReturn(Arrays.asList(sampleLike));
        List<Like> likes = likeService.getLikesByPost(1);
        assertFalse(likes.isEmpty());
        assertEquals(1, likes.size());
    }
}
