package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateGroupInviteDto {
    @JsonProperty("group_id")
    private long groupId;
    @JsonProperty("account_id")
    private long accountId;
}
