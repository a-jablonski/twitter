package com.twitter.service;

import com.twitter.dto.MessageDto;
import com.twitter.entity.MessageEntity;
import com.twitter.exception.UserException;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

@Service
public class WallService {


    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public WallService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDto> getUserMessages(String username) {
        if (!userRepository.userExists(username)) {
            throw new UserException();
        }
        BlockingDeque<MessageEntity> userMessages = messageRepository.getUserMessages(username);
        return userMessages.stream()
                .map(MessageDto::new)
                .sorted(Comparator.comparing(MessageDto::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

}
