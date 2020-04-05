package com.twitter.service;

import com.twitter.dto.FollowingDto;
import com.twitter.exception.FollowingValidationException;
import com.twitter.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {

    private final UserRepository userRepository;

    public FollowingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void follow(FollowingDto followingDto) {
        validateDto(followingDto);
        userRepository.follow(followingDto);
    }

    public void unfollow(FollowingDto followingDto) {
        validateDto(followingDto);
        userRepository.unfollow(followingDto);
    }

    private void validateDto(FollowingDto followingDto) {
        if (Strings.isBlank(followingDto.getFollowedUser())) {
            throw new FollowingValidationException("Username to be followed is invalid");
        }
        if (Strings.isBlank(followingDto.getUsername())) {
            throw new FollowingValidationException("Username is invalid");
        }
        if (followingDto.getFollowedUser().equals(followingDto.getUsername())) {
            throw new FollowingValidationException("Username and user to be followed cannot be the same");
        }
        if (!userRepository.userExists(followingDto.getFollowedUser())) {
            throw new FollowingValidationException("User to be followed doesn't exist");
        }
        if (!userRepository.userExists(followingDto.getUsername())) {
            throw new FollowingValidationException("User doesn't exist");
        }
    }
}
