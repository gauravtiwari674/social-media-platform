package com.socialmedia.like.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Likes")
public class Like {

    @Id
    @Column(name = "likeID")
    private int likeID;

    @Column(name = "postID")
    private int postID;

    @Column(name = "userID")
    private int userID;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // Constructors
    public Like() {}

    public Like(int likeID, int postID, int userID, LocalDateTime timestamp) {
        this.likeID = likeID;
        this.postID = postID;
        this.userID = userID;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getLikeID() { return likeID; }
    public void setLikeID(int likeID) { this.likeID = likeID; }

    public int getPostID() { return postID; }
    public void setPostID(int postID) { this.postID = postID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
