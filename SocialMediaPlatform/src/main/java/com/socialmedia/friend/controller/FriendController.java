package com.socialmedia.friend.controller;

import com.socialmedia.friend.entity.Friend;
import com.socialmedia.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    // 🔹 Send Request
    @PostMapping("/request")
    public String sendRequest(@RequestParam int senderId,
                              @RequestParam int receiverId) {
        return friendService.sendRequest(senderId, receiverId);
    }

    // 🔹 Accept Request
    @PostMapping("/accept")
    public String acceptRequest(@RequestParam int friendshipId) {
        return friendService.acceptRequest(friendshipId);
    }

    // 🔹 Get Friends
    @GetMapping("/list/{userId}")
    public List<Friend> getFriends(@PathVariable int userId) {
        return friendService.getFriends(userId);
    }

    // 🔹 Pending Requests
    @GetMapping("/pending/{userId}")
    public List<Friend> getPending(@PathVariable int userId) {
        return friendService.getPendingRequests(userId);
    }
}