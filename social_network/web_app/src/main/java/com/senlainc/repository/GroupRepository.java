package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupChat;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group> {
    List<Group> getGroups();

    List<GroupChat> getChats(Group group);

    List<Account> getMembers(Group group);
}
