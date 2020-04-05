package com.twitter.repository;

import com.twitter.dto.FollowingDto;
import com.twitter.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserRepository {

    private static final Map<String, UserEntity> users = new ConcurrentHashMap<>();

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public UserEntity createUser(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        users.put(userEntity.getUsername(), userEntity);
        return userEntity;
    }

    public void follow(FollowingDto followingDto) {
        users.get(followingDto.getUsername())
                .getFollowedUsers()
                .add(followingDto.getFollowedUser());
    }

    public void unfollow(FollowingDto followingDto) {
        Set<String> following = users.get(followingDto.getUsername()).getFollowedUsers();
        following.remove(followingDto.getFollowedUser());
    }
}
