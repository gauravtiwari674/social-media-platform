package com.socialmedia.repository;

import com.socialmedia.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost_PostID(int postID);
    List<Comment> findByPost_PostIDAndUser_UserID(int postID, int userID);
    Optional<Comment> findById(int commentID);
    List<Comment> findByUser_UserID(int userID);
    long countByPost_PostID(int postID);
    long countByUser_UserID(int userID);
    void deleteByPost_PostID(int postID);
    void deleteByUser_UserID(int userID);
}

