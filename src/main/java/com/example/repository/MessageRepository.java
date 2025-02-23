package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>
{
    Message findMessageByMessageId(Integer messageId);

    Integer deleteByMessageId(Integer messageId);

    List<Message> findMessagesByPostedBy(Integer accountId);
}
