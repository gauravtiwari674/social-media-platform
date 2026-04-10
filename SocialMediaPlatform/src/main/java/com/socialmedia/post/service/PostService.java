package com.socialmedia.post.service;

import java.util.List;

import com.socialmedia.post.dto.PostDTO;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    PostDTO createPost(PostDTO postDTO, Long userId, String username);

    PostDTO updatePost(Long postId, PostDTO postDTO, Long userId);

    void deletePost(Long postId, Long userId);

    List<PostDTO> getPostsByUser(Long userId);
}
