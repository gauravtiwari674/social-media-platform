package com.socialmedia.like.repository;

import com.socialmedia.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
    // Check if a user already liked a post (prevent duplicate likes)
    boolean existsByPostIDAndUserID(int postID, int userID);

    // Count likes on a post
    long countByPostID(int postID);

    // Get all likes on a post
    List<Like> findByPostID(int postID);

    // Get all likes by a user
    List<Like> findByUserID(int userID);

    // Find a specific like by postID and userID (used in toggle)
    Optional<Like> findByPostIDAndUserID(int postID, int userID);
}