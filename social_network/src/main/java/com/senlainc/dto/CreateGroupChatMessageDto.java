package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateGroupChatMessageDto {
    @JsonProperty("chat_id")
    private long chatId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("text")
    private String text;
}
