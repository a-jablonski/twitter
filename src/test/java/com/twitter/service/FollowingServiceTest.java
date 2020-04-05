package com.twitter.service;


import com.twitter.dto.FollowingDto;
import com.twitter.exception.FollowingValidationException;
import com.twitter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FollowingServiceTest {

    public static final String USER = "test";
    public static final String USER_TO_BE_FOLLOWED = "test2";
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FollowingService followingService;

    @Test
    public void shouldThrowExceptionIfUserNameIsIncorrect() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);
        Assertions.assertThrows(FollowingValidationException.class, () -> followingService.follow(followingDto), "Username is invalid");
    }

    @Test
    public void shouldThrowExceptionIfUsernameToBeFollowedIsIncorrect() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        Assertions.assertThrows(FollowingValidationException.class, () -> followingService.follow(followingDto), "Username to be followed is invalid");
    }

    @Test
    public void shouldThrowExceptionWhenUserToBeFollowedDoesntExist() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);
        Mockito.when(userRepository.userExists(USER_TO_BE_FOLLOWED)).thenReturn(false);
        Assertions.assertThrows(FollowingValidationException.class, () -> followingService.follow(followingDto), "User to be followed doesn't exist");
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesntExist() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);
        Mockito.when(userRepository.userExists(USER_TO_BE_FOLLOWED)).thenReturn(true);
        Mockito.when(userRepository.userExists(USER)).thenReturn(false);
        Assertions.assertThrows(FollowingValidationException.class, () -> followingService.follow(followingDto), "User doesn't exist");
    }

    @Test
    public void shouldThrowErrorForSameUsername() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        followingDto.setFollowedUser(USER);
        Assertions.assertThrows(FollowingValidationException.class, () -> followingService.follow(followingDto), "Username and user to be followed cannot be the same");
    }

    @Test
    public void followShouldPassForValidData() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);
        Mockito.when(userRepository.userExists(USER_TO_BE_FOLLOWED)).thenReturn(true);
        Mockito.when(userRepository.userExists(USER)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> followingService.follow(followingDto));
    }

    @Test
    public void unfollowShouldPassForValidData() {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(USER);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);
        Mockito.when(userRepository.userExists(USER_TO_BE_FOLLOWED)).thenReturn(true);
        Mockito.when(userRepository.userExists(USER)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> followingService.unfollow(followingDto));
    }
}
