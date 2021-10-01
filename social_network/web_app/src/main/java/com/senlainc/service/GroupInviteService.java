package com.senlainc.service;

import com.senlainc.dto.CreateGroupInviteDto;
import com.senlainc.dto.GroupInviteDto;

public interface GroupInviteService {
    GroupInviteDto create(CreateGroupInviteDto createGroupInviteDto);

    void acceptInvite(long id);

    void rejectInvite(long id);
}
