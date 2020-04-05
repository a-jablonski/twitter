package com.twitter.dto;

import com.twitter.entity.MessageEntity;
import lombok.Data;

import java.time.ZoneOffset;

@Data
public class MessageDto {

    private String text;
    private Long timestamp;
    private String username;

    public MessageDto(MessageEntity messageEntity) {
        this.text = messageEntity.getText();
        this.timestamp = messageEntity.getCreationTime().toEpochSecond(ZoneOffset.UTC);
        this.username = messageEntity.getUser().getUsername();
    }
}
