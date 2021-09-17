package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senlainc.entity.InviteStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FriendInviteDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("from_account_id")
    private long fromAccountId;
    @JsonProperty("to_account_id")
    private long toAccountId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("status")
    private InviteStatus status;
}
