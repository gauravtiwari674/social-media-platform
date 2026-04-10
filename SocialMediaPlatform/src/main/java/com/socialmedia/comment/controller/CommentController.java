package com.socialmedia.comment.controller;

import com.socialmedia.comment.entity.Comment;
import com.socialmedia.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	// Update a comment
	@PutMapping("/{commentID}")
	public Comment updateComment(@PathVariable int commentID, @RequestParam String newText) {
	    return commentService.updateComment(commentID, newText);
	}

	// Get comment count for a post
	@GetMapping("/count/{postID}")
	public long getCommentCount(@PathVariable int postID) {
	    return commentService.getCommentCount(postID);
	}

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/post/{postID}")
    public List<Comment> getCommentsByPost(@PathVariable int postID) {
        return commentService.getCommentsByPost(postID);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @DeleteMapping("/{commentID}")
    public String deleteComment(@PathVariable int commentID) {
        commentService.deleteComment(commentID);
        return "Comment deleted successfully";
    }
}