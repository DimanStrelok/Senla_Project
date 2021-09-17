package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GroupChatMessageDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("chat_id")
    private long chatId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
