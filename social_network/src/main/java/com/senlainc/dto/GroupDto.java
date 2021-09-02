package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
}
