package com.senlainc.service;

import com.senlainc.dto.CreateGroupDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.mapper.GroupChatMapper;
import com.senlainc.mapper.GroupMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService, AuthenticationAccess {
    private final GroupRepository repository;
    private final GroupMapper mapper;
    private final GroupChatMapper chatMapper;
    private final AccountRepository accountRepository;
    private final GroupAccountService groupAccountService;

    @Transactional
    @Override
    public GroupDto create(CreateGroupDto createGroupDto) {
        Account authenticatedAccount = getAuthenticatedAccount();
        Account account = accountRepository.get(createGroupDto.getAccountId());
        if (authenticatedAccount.getId() != account.getId()) {
            throw new AccessDeniedException("access to create group from account " + account + " via account " + authenticatedAccount + " denied");
        }
        Group entity = new Group();
        entity.setTitle(createGroupDto.getTitle());
        entity.setDescription(createGroupDto.getDescription());
        repository.create(entity);
        groupAccountService.createCreator(entity, account);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public GroupDto update(GroupDto groupDto) {
        Account authenticatedAccount = getAuthenticatedAccount();
        Group entity = repository.get(groupDto.getId());
        if (authenticatedAccount.getId() != groupAccountService.getGroupCreator(entity).getId()) {
            throw new AccessDeniedException("access to update group " + entity + " via account " + authenticatedAccount + " denied");
        }
        entity.setTitle(groupDto.getTitle());
        entity.setDescription(groupDto.getDescription());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        Group entity = repository.get(id);
        if (authenticatedAccount.getId() != groupAccountService.getGroupCreator(entity).getId()) {
            throw new AccessDeniedException("access to delete group " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDto> getGroups() {
        return mapper.entityListToDtoList(repository.getGroups());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupChatDto> getChats(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        Group entity = repository.get(id);
        if (!groupAccountService.isGroupMember(entity, authenticatedAccount)) {
            throw new AccessDeniedException("access to read group chats from " + entity + " via account " + authenticatedAccount + " denied");
        }
        return chatMapper.entityListToDtoList(repository.getChats(entity));
    }
}
