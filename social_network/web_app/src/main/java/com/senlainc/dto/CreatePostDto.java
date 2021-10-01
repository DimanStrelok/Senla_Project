package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreatePostDto {
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("text")
    private String text;
}
