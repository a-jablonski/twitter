package com.twitter.service;

import com.twitter.dto.MessageDto;
import com.twitter.entity.UserEntity;
import com.twitter.exception.UserException;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimelineService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public TimelineService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    public List<MessageDto> getFollowedUsersMessages(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserException::new);
        Set<String> following = userEntity.getFollowedUsers();
        return following.stream().map(messageRepository::getUserMessages).flatMap(Collection::stream)
                .map(MessageDto::new)
                .sorted(Comparator.comparing(MessageDto::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}
