package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senlainc.entity.InviteStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class GroupInviteDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("group_id")
    private long groupId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("status")
    private InviteStatus status;
}
