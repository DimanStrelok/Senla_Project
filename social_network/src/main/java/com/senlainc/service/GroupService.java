package com.senlainc.service;

import com.senlainc.dto.AccountDto;
import com.senlainc.dto.CreateGroupDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto create(CreateGroupDto createGroupDto);

    GroupDto get(long id);

    GroupDto update(GroupDto groupDto);

    void delete(long id);

    List<GroupDto> getGroups();

    List<GroupChatDto> getChats(long id);

    List<AccountDto> getMembers(long id);
}
