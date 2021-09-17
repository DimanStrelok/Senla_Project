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
public class CreateFriendInviteDto {
    @JsonProperty("from_account_id")
    private long fromAccountId;
    @JsonProperty("to_account_id")
    private long toAccountId;
}
