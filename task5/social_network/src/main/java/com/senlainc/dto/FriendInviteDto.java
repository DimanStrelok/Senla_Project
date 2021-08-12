package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FriendInviteDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("from_account_id")
    private Long fromAccountId;
    @JsonProperty("to_account_id")
    private Long toAccountId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("status")
    private byte status;
}
