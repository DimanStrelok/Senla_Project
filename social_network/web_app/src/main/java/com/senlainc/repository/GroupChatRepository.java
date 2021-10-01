package com.senlainc.repository;

import com.senlainc.entity.GroupChat;
import com.senlainc.entity.GroupChatMessage;

import java.util.List;

public interface GroupChatRepository extends CrudRepository<GroupChat> {
    List<GroupChatMessage> getMessages(GroupChat groupChat);
}
