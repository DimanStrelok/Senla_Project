package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PostDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
