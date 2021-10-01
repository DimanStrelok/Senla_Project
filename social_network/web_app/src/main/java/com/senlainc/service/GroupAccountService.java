package com.senlainc.service;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;

public interface GroupAccountService {
    void createCreator(Group group, Account account);

    void createMember(Group group, Account account);

    boolean isGroupMember(Group group, Account account);

    Account getGroupCreator(Group group);
}
