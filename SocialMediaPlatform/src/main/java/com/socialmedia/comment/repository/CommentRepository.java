package com.socialmedia.comment.repository;

import com.socialmedia.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// Count comments on a post
	long countByPostID(int postID);

    List<Comment> findByPostID(int postID);
    List<Comment> findByUserID(int userID);
}
