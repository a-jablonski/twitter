package com.twitter.repository;

import com.twitter.dto.PostDto;
import com.twitter.entity.MessageEntity;
import com.twitter.entity.UserEntity;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

public class MessageRepositoryTest {

    public static final String TEST = "test";
    private MessageRepository messageRepository = new MessageRepository();

    @AfterEach
    public void clearData() throws NoSuchFieldException, IllegalAccessException {
        Field userMessages = MessageRepository.class.getDeclaredField("userMessages");
        userMessages.setAccessible(true);
        Map map = (Map) userMessages.get(messageRepository);
        map.clear();
    }

    @Test
    public void shouldReturnCreatedMessageEntity() {
        PostDto postDto = new PostDto();
        postDto.setText(TEST);
        postDto.setUsername(TEST);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(TEST);

        MessageEntity messageEntity = messageRepository.createMessageEntity(postDto, userEntity);

        Assertions.assertEquals(TEST, messageEntity.getText());
        Assertions.assertEquals(userEntity, messageEntity.getUser());
    }

    @Test
    public void shouldReturnEmptyCollectionForInvalidUsername() {
        BlockingDeque<MessageEntity> test = messageRepository.getUserMessages(TEST);
        Assertions.assertTrue(test.isEmpty());
    }
}
