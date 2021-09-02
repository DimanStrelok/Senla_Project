package com.senlainc.service;

import com.senlainc.dto.CreateGroupChatMessageDto;
import com.senlainc.dto.GroupChatMessageDto;

public interface GroupChatMessageService {
    GroupChatMessageDto create(CreateGroupChatMessageDto createChatCommentDto);

    GroupChatMessageDto get(long id);

    GroupChatMessageDto changeText(long id, String text);

    void delete(long id);
}
