package com.twitter.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageEntity {

    private UserEntity user;
    private String text;
    private LocalDateTime creationTime;
}
