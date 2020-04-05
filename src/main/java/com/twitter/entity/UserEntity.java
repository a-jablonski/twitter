package com.twitter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserEntity {

    private String username;
    @Setter(AccessLevel.NONE)
    private Set<String> followedUsers = new HashSet<>();
}
