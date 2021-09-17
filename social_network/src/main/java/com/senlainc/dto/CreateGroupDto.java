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
public class CreateGroupDto {
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
}
