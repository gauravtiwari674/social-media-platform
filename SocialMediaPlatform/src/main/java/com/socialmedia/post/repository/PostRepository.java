package com.socialmedia.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByTimestampDesc();

    List<Post> findByUserIdOrderByTimestampDesc(Long userId);
}
