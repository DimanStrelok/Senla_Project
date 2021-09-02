package com.senlainc.service;

import com.senlainc.dto.CreateGroupChatDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupChatMessageDto;

import java.util.List;

public interface GroupChatService {
    GroupChatDto create(CreateGroupChatDto createGroupChatDto);

    GroupChatDto get(long id);

    GroupChatDto changeTitle(long id, String text);

    void delete(long id);

    List<GroupChatMessageDto> getMessages(long id);
}
