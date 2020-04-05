package com.twitter.service;

import com.twitter.dto.PostDto;
import com.twitter.entity.UserEntity;
import com.twitter.exception.MessageValidationError;
import com.twitter.exception.UserException;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class PostingService {

    public static final int MAX_LENGTH = 140;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public PostingService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }


    public void createMessage(PostDto postDto) {
        if (Strings.isBlank(postDto.getText())) {
            throw new MessageValidationError("Message cannot be empty");
        }
        if (postDto.getText().length() > MAX_LENGTH) {
            throw new MessageValidationError("Message is too long");
        }
        if (Strings.isBlank(postDto.getUsername())) {
            throw new UserException();
        }
        UserEntity userEntity = userRepository.findByUsername(postDto.getUsername())
                .orElseGet(() -> userRepository.createUser(postDto.getUsername()));
        messageRepository.createMessageEntity(postDto, userEntity);
    }

}
