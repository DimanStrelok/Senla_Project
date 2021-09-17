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
public class GroupChatDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("group_id")
    private long groupId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
