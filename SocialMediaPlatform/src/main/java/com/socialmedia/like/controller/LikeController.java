package com.socialmedia.like.controller;

import com.socialmedia.like.entity.Like;
import com.socialmedia.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
	
	// Toggle like (like if not liked, unlike if already liked)
	@PostMapping("/toggle")
	public String toggleLike(@RequestParam int postID, @RequestParam int userID) {
	    boolean liked = likeService.toggleLike(postID, userID);
	    return liked ? "Post liked!" : "Post unliked!";
	}

	// Get like count for a post
	@GetMapping("/count/{postID}")
	public long getLikeCount(@PathVariable int postID) {
	    return likeService.getLikeCount(postID);
	}

    @Autowired
    private LikeService likeService;

    @GetMapping
    public List<Like> getAllLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/post/{postID}")
    public List<Like> getLikesByPost(@PathVariable int postID) {
        return likeService.getLikesByPost(postID);
    }

    @PostMapping
    public Like addLike(@RequestBody Like like) {
        return likeService.addLike(like);
    }

    @DeleteMapping("/{likeID}")
    public String deleteLike(@PathVariable int likeID) {
        likeService.deleteLike(likeID);
        return "Like deleted successfully";
    }
}