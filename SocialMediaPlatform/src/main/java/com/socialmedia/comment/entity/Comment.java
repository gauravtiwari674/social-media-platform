package com.socialmedia.comment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @Column(name = "commentID")
    private int commentID;

    @Column(name = "postID")
    private int postID;

    @Column(name = "userID")
    private int userID;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // Constructors
    public Comment() {}

    public Comment(int commentID, int postID, int userID, String commentText, LocalDateTime timestamp) {
        this.commentID = commentID;
        this.postID = postID;
        this.userID = userID;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getCommentID() { return commentID; }
    public void setCommentID(int commentID) { this.commentID = commentID; }

    public int getPostID() { return postID; }
    public void setPostID(int postID) { this.postID = postID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}