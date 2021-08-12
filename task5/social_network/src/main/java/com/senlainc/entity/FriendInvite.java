package com.senlainc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FriendInvite {
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private LocalDateTime createdAt;
    private byte status;
}
