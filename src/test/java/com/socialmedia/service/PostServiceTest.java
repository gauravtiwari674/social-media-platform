package com.socialmedia.service;

import com.socialmedia.dto.PostFeedResponse;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
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
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User sampleUser;
    private Post samplePost;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1, "testuser", "test@example.com", "password123");
        samplePost = new Post(1, sampleUser, "My first post", LocalDateTime.now());
    }

    @Test
    void testCreatePost_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(postRepository.save(any(Post.class))).thenReturn(samplePost);

        String result = postService.createPost(1, samplePost);

        assertEquals("Post created successfully!", result);
        verify(postRepository, times(1)).save(samplePost);
    }

    @Test
    void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(samplePost));

        List<Post> posts = postService.getAllPosts();

        assertEquals(1, posts.size());
        assertEquals("My first post", posts.get(0).getContent());
    }

    @Test
    void testGetPostsByUserId() {
        when(postRepository.findByUser_UserID(1)).thenReturn(Arrays.asList(samplePost));

        List<Post> posts = postService.getPostsByUserId(1);

        assertEquals(1, posts.size());
    }

    @Test
    void testSearchPosts() {
        when(postRepository.findByContentContainingIgnoreCase("first")).thenReturn(Arrays.asList(samplePost));

        List<Post> posts = postService.searchPosts("first");

        assertEquals(1, posts.size());
    }

    @Test
    void testUpdatePost() {
        when(postRepository.findById(1)).thenReturn(Optional.of(samplePost));
        when(postRepository.save(any(Post.class))).thenReturn(samplePost);

        Post updated = new Post();
        updated.setContent("Updated content");
        
        Post result = postService.updatePost(1, updated);

        assertEquals("Updated content", result.getContent());
    }

    @Test
    void testGetPaginatedFeed() {
        List<Post> posts = Arrays.asList(
            new Post(1, sampleUser, "Post 1", LocalDateTime.now()),
            new Post(2, sampleUser, "Post 2", LocalDateTime.now())
        );
        when(postRepository.findAll()).thenReturn(posts);

        PostFeedResponse response = postService.getPaginatedFeed(12345, 0, 1);

        assertEquals(1, response.getPosts().size());
        assertEquals(2, response.getTotalCount());
        assertEquals(0, response.getCurrentPage());
    }
}
