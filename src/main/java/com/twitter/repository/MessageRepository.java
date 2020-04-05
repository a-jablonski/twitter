package com.twitter.repository;

import com.google.common.collect.Lists;
import com.twitter.dto.PostDto;
import com.twitter.entity.MessageEntity;
import com.twitter.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class MessageRepository {

    private static final Map<String, BlockingDeque<MessageEntity>> userMessages = new ConcurrentHashMap<>();

    public MessageEntity createMessageEntity(PostDto postDto, UserEntity userEntity) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setCreationTime(LocalDateTime.now());
        messageEntity.setUser(userEntity);
        messageEntity.setText(postDto.getText());
        if (userMessages.containsKey(userEntity.getUsername())) {
            userMessages.get(userEntity.getUsername()).add(messageEntity);
        } else {
            userMessages.put(userEntity.getUsername(), new LinkedBlockingDeque<MessageEntity>(Lists.newArrayList(messageEntity)));
        }
        return messageEntity;
    }

    public BlockingDeque<MessageEntity> getUserMessages(String username) {
        return userMessages.getOrDefault(username, new LinkedBlockingDeque<MessageEntity>());
    }

}
