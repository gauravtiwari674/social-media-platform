package com.socialmedia.friend.service;

import com.socialmedia.friend.entity.Friend;
import com.socialmedia.friend.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    // 🔹 Send Request
    public String sendRequest(int senderId, int receiverId) {

        Friend friend = new Friend();
        friend.setFriendshipID((int)(Math.random()*100000));
        friend.setUserID1(senderId);
        friend.setUserID2(receiverId);
        friend.setStatus("pending");

        friendRepository.save(friend);

        return "Friend request sent";
    }

    // 🔹 Accept Request
    public String acceptRequest(int friendshipId) {

        Friend friend = friendRepository.findById(friendshipId).orElse(null);

        if (friend == null) {
            return "Request not found";
        }

        friend.setStatus("accepted");
        friendRepository.save(friend);

        return "Friend request accepted";
    }

    // 🔹 Get All Friends
    public List<Friend> getFriends(int userId) {
        return friendRepository.findByUserID1OrUserID2(userId, userId);
    }

    // 🔹 Get Pending Requests
    public List<Friend> getPendingRequests(int userId) {
        return friendRepository.findByUserID2AndStatus(userId, "pending");
    }
}