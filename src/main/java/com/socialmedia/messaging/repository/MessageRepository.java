package com.socialmedia.messaging.repository;

import com.socialmedia.messaging.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE " +
           "(m.senderID = :userID AND m.receiverID = :otherID) OR " +
           "(m.senderID = :otherID AND m.receiverID = :userID) " +
           "ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("userID") int userID,
                                   @Param("otherID") int otherID);

    List<Message> findByReceiverIDOrderByTimestampDesc(int receiverID);
}