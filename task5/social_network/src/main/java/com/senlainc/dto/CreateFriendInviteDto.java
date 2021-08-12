package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateFriendInviteDto {
    @JsonProperty("from_account_id")
    private Long fromAccountId;
    @JsonProperty("to_account_id")
    private Long toAccountId;
}
