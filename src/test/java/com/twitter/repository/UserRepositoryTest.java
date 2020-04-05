package com.twitter.repository;

import com.twitter.dto.FollowingDto;
import com.twitter.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryTest {

    public static final String TEST = "TEST";
    public static final String USER_TO_BE_FOLLOWED = "USER_TO_BE_FOLLOWED";
    private UserRepository userRepository = new UserRepository();

    @AfterEach
    public void clearData() throws NoSuchFieldException, IllegalAccessException {
        Field users = UserRepository.class.getDeclaredField("users");
        users.setAccessible(true);
        Map map = (Map) users.get(userRepository);
        map.clear();
    }
    @Test
    public void shouldReturnTrueForExistingUser() {
        userRepository.createUser(TEST);

        Assertions.assertTrue(userRepository.userExists(TEST));
    }

    @Test
    public void shouldReturnFalseForNotExistingUser() {

        Assertions.assertFalse(userRepository.userExists(TEST));
    }


    @Test
    public void shouldAddOnFollowList() {
        userRepository.createUser(TEST);
        userRepository.createUser(USER_TO_BE_FOLLOWED);

        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(TEST);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);

        userRepository.follow(followingDto);
        Optional<UserEntity> userOpt = userRepository.findByUsername(TEST);

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals(1, userOpt.get().getFollowedUsers().size());
    }
    @Test
    public void shouldUnfollow() {
        userRepository.createUser(TEST);
        userRepository.createUser(USER_TO_BE_FOLLOWED);

        FollowingDto followingDto = new FollowingDto();
        followingDto.setUsername(TEST);
        followingDto.setFollowedUser(USER_TO_BE_FOLLOWED);

        userRepository.follow(followingDto);
        Optional<UserEntity> userOpt = userRepository.findByUsername(TEST);

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals(1, userOpt.get().getFollowedUsers().size());

        userRepository.unfollow(followingDto);
        userOpt = userRepository.findByUsername(TEST);

        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals(0, userOpt.get().getFollowedUsers().size());
    }

}
