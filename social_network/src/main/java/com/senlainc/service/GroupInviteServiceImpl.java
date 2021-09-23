package com.senlainc.service;

import com.senlainc.dto.CreateGroupInviteDto;
import com.senlainc.dto.GroupInviteDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupInvite;
import com.senlainc.entity.InviteStatus;
import com.senlainc.exception.AppException;
import com.senlainc.mapper.GroupInviteMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupInviteRepository;
import com.senlainc.repository.GroupRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class GroupInviteServiceImpl implements GroupInviteService {
    private final GroupInviteRepository repository;
    private final GroupInviteMapper mapper;
    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;
    private final GroupAccountService groupAccountService;
    private final AuthenticationAccess authenticationAccess;

    @Transactional
    @Override
    public GroupInviteDto create(CreateGroupInviteDto createGroupInviteDto) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        long groupId = createGroupInviteDto.getGroupId();
        long accountId = createGroupInviteDto.getAccountId();
        Group group = groupRepository.get(groupId);
        Account account = accountRepository.get(accountId);
        if (authenticatedAccount.getId() != account.getId()) {
            throw new AccessDeniedException("access to create group invite from account " + account + " via account " + authenticatedAccount + " denied");
        }
        if (groupAccountService.isGroupMember(group, account)) {
            throw new AppException("Account with id = " + accountId + " already a member of the group with id = " + groupId);
        }
        GroupInvite entity = new GroupInvite();
        entity.setGroup(group);
        entity.setAccount(account);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(InviteStatus.Created);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void acceptInvite(long id) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        GroupInvite entity = repository.get(id);
        if (authenticatedAccount.getId() != groupAccountService.getGroupCreator(entity.getGroup()).getId()) {
            throw new AccessDeniedException("access to accept group invite " + entity + " via account " + authenticatedAccount + " denied");
        }
        if (entity.getStatus() == InviteStatus.Created) {
            groupAccountService.createMember(entity.getGroup(), entity.getAccount());
            entity.setStatus(InviteStatus.Accepted);
            repository.update(entity);
        } else {
            throw new AppException("to accept group invite, it must have status 'Created'");
        }
    }

    @Transactional
    @Override
    public void rejectInvite(long id) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        GroupInvite entity = repository.get(id);
        if (authenticatedAccount.getId() != groupAccountService.getGroupCreator(entity.getGroup()).getId()) {
            throw new AccessDeniedException("access to reject group invite " + entity + " via account " + authenticatedAccount + " denied");
        }
        if (entity.getStatus() == InviteStatus.Created) {
            entity.setStatus(InviteStatus.Rejected);
            repository.update(entity);
        } else {
            throw new AppException("to reject group invite, it must have status 'Created'");
        }
    }
}
