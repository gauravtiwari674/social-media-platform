package com.socialmedia.like.service;

import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.like.entity.Like;
import com.socialmedia.like.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikeService {
	
	public boolean toggleLike(int postID, int userID) {
	    if (likeRepository.existsByPostIDAndUserID(postID, userID)) {
	        // User already liked → unlike it
	        Like existing = likeRepository.findByPostIDAndUserID(postID, userID).orElseThrow(() -> new ResourceNotFoundException("No Like Existing"));
	        likeRepository.delete(existing);
	        return false; // means unliked
	    } else {
	        Like like = new Like();
	        like.setPostID(postID);
	        like.setUserID(userID);
	        like.setTimestamp(LocalDateTime.now());
	        likeRepository.save(like);
	        return true; // means liked
	    }
	}

	public long getLikeCount(int postID) {
	    return likeRepository.countByPostID(postID);
	}

    @Autowired
    private LikeRepository likeRepository;

    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    public List<Like> getLikesByPost(int postID) {
        return likeRepository.findByPostID(postID);
    }

    public Like addLike(Like like) {
        return likeRepository.save(like);
    }

    public void deleteLike(int likeID) {
        likeRepository.deleteById(likeID);
    }
}