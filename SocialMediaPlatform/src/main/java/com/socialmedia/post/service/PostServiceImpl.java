package com.socialmedia.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialmedia.post.dto.PostDTO;
import com.socialmedia.post.entity.Post;
import com.socialmedia.post.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return convertToDTO(post);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, String username) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setUserId(userId);
        post.setUsername(username);
        post.setTimestamp(LocalDateTime.now());
        return convertToDTO(postRepository.save(post));
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO postDTO, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You can edit only your own post");
        }

        post.setContent(postDTO.getContent());
        post.setTimestamp(LocalDateTime.now());
        return convertToDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You can delete only your own post");
        }

        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        return postRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setContent(post.getContent());
        dto.setTimestamp(post.getTimestamp());
        dto.setUserId(post.getUserId());
        dto.setUsername(post.getUsername());
        return dto;
    }
}
