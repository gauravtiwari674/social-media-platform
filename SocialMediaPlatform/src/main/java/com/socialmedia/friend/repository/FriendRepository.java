package com.socialmedia.friend.repository;

import com.socialmedia.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findByUserID1OrUserID2(int userID1, int userID2);

    List<Friend> findByUserID2AndStatus(int userID2, String status);

}