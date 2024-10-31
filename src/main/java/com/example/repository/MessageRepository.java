package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>
{
    Message findMessageByMessageId(Integer messageId);

    Integer deleteMessageByMessageId(Integer messageId);

    Integer patchMessageByMessageId(Integer messageId, Message message);

    List<Message> findMessagesByPostedBy(Integer accountId);
}
