package com.senlainc.service;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupAccount;
import com.senlainc.entity.GroupRole;
import com.senlainc.repository.GroupAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupAccountServiceImpl implements GroupAccountService {
    private final GroupAccountRepository repository;

    @Transactional
    @Override
    public void createCreator(Group group, Account account) {
        GroupAccount entity = new GroupAccount();
        entity.setGroup(group);
        entity.setAccount(account);
        entity.setRole(GroupRole.Creator);
        repository.create(entity);
    }

    @Transactional
    @Override
    public void createMember(Group group, Account account) {
        GroupAccount entity = new GroupAccount();
        entity.setGroup(group);
        entity.setAccount(account);
        entity.setRole(GroupRole.Member);
        repository.create(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isGroupMember(Group group, Account account) {
        return repository.isMember(group, account);
    }

    @Override
    public Account getGroupCreator(Group group) {
        return repository.getGroupCreator(group);
    }
}
