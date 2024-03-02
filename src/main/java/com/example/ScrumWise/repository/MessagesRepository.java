package com.example.ScrumWise.repository;

import com.example.ScrumWise.model.entity.Messages;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    List<Messages> findAllByChannel(String channel);

    @Modifying
    @Query(value = "update message set readDate = now() "
            + " where channel = ?1 and sender = ?2 and receiver = ?3 and readDate is null", nativeQuery = true)
    void sendReadReceipt(String channel, String sender, String receiver);

    @Query("SELECT DISTINCT m.receiver FROM Messages m WHERE m.sender = :sender")
    List<String> findDistinctReceiverBySender(@Param("sender") String sender);
    @Query("SELECT DISTINCT CASE WHEN m.sender = :receiver THEN m.receiver ELSE m.sender END FROM Messages m WHERE m.sender = :receiver OR m.receiver = :receiver")
    List<String> findDistinctUsers(@Param("receiver") String receiver);
    @Query("SELECT m FROM Messages m WHERE m.channel = :channel AND m.timestamp = (SELECT MAX(m2.timestamp) FROM Messages m2 WHERE m2.channel = :channel)")
    Messages findLastMessageByChannel(@Param("channel") String channel);
}
