package com.twitter.service;


import com.twitter.dto.MessageDto;
import com.twitter.entity.MessageEntity;
import com.twitter.entity.UserEntity;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

@ExtendWith(MockitoExtension.class)
public class TimelineServiceTest {

    public static final String TEST = "test";
    public static final String FOLLOWED_USER = "followedUser";
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TimelineService timelineService;

    @Test
    public void shouldReturnFollowedUserMessages() {
        LocalDateTime now = LocalDateTime.now();
        LinkedBlockingDeque<MessageEntity> messageEntities = new LinkedBlockingDeque<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText(TEST);
        UserEntity user = new UserEntity();
        user.setUsername(TEST);
        user.getFollowedUsers().add(FOLLOWED_USER);
        messageEntity.setUser(user);
        messageEntity.setCreationTime(now);
        messageEntities.add(messageEntity);

        Mockito.when(messageRepository.getUserMessages(Mockito.anyString())).thenReturn(messageEntities);
        Mockito.when(userRepository.findByUsername(TEST)).thenReturn(Optional.of(user));

        List<MessageDto> userMessages = timelineService.getFollowedUsersMessages(TEST);

        Assertions.assertEquals(1, userMessages.size());
        Assertions.assertEquals(userMessages.get(0).getText(), TEST);
        Assertions.assertEquals(userMessages.get(0).getTimestamp(), now.toEpochSecond(ZoneOffset.UTC));
        Assertions.assertEquals(userMessages.get(0).getUsername(), TEST);
    }


}
