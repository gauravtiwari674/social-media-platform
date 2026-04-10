package com.socialmedia.comment.service;

import com.socialmedia.comment.entity.Comment;
import com.socialmedia.comment.repository.CommentRepository;
import com.socialmedia.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
	
	public Comment updateComment(int commentID, String newText) {
	    Comment comment = commentRepository.findById(commentID)
	        .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
	    comment.setCommentText(newText);
	    return commentRepository.save(comment);
	}

	public long getCommentCount(int postID) {
	    return commentRepository.countByPostID(postID);
	}

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPost(int postID) {
        return commentRepository.findByPostID(postID);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(int commentID) {
        commentRepository.deleteById(commentID);
    }
}