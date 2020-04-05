package com.twitter.dto;

import lombok.Data;

@Data
public class FollowingDto {

    private String username;
    private String followedUser;
}
