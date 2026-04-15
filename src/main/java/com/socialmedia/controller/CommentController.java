package com.socialmedia.controller;

import com.socialmedia.entity.Comment;
import com.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String addComment(@RequestParam int postID, @RequestParam int userID, @RequestBody Comment comment) {
        return commentService.addComment(postID, userID, comment);
    }

    @GetMapping("/post/{postID}")
    public List<Comment> getCommentsByPostId(@PathVariable int postID) {
        return commentService.getCommentsByPostId(postID);
    }
    
    @GetMapping("/post/{postID}/user/{userID}")
    public List<Comment> getCommentsByPostAndUser(@PathVariable int postID,
                                                  @PathVariable int userID) {
        return commentService.getCommentsByPostAndUser(postID, userID);
    }
    
    @GetMapping("/{commentID}")
    public Comment getCommentById(@PathVariable int commentID) {
        return commentService.getCommentById(commentID);
    }
    
    @GetMapping("/user/{userID}")
    public List<Comment> getCommentsByUser(@PathVariable int userID) {
        return commentService.getCommentsByUser(userID);
    }
    
    @DeleteMapping("/{commentID}")
    public String deleteComment(@PathVariable int commentID) {
        return commentService.deleteComment(commentID);
    }
    
    @PutMapping("/{commentID}")
    public String updateComment(@PathVariable int commentID,
                                @RequestBody Comment comment) {
        return commentService.updateComment(commentID, comment);
    }
    
    @GetMapping("/count/{postID}")
    public long getCommentCountByPost(@PathVariable int postID) {
        return commentService.getCommentCountByPost(postID);
    }

    @GetMapping("/user/{userID}/count")
    public long getCommentCountByUser(@PathVariable int userID) {
        return commentService.getCommentCountByUser(userID);
    }
    
    @DeleteMapping("/post/{postID}")
    public String deleteCommentsByPost(@PathVariable int postID) {
        return commentService.deleteCommentsByPost(postID);
    }

    @DeleteMapping("/user/{userID}")
    public String deleteCommentsByUser(@PathVariable int userID) {
        return commentService.deleteCommentsByUser(userID);
    }
}
