package com.senlainc.service;

import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;

public interface FriendInviteService {
    FriendInviteDto create(CreateFriendInviteDto createFriendInviteDto);

    void acceptFriendInvite(long id);
}
