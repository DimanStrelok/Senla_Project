package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DialogDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("account1_id")
    private long account1Id;
    @JsonProperty("account2_id")
    private long account2Id;
}
