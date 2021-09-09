package com.senlainc.service;

import com.senlainc.dto.CreateGroupChatDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupChat;
import com.senlainc.mapper.GroupChatMapper;
import com.senlainc.mapper.GroupChatMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupChatRepository;
import com.senlainc.repository.GroupRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupChatServiceImpl implements GroupChatService, AuthenticationAccess {
    private final GroupChatRepository repository;
    private final GroupChatMapper mapper;
    private final GroupChatMessageMapper messageMapper;
    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;
    private final GroupAccountService groupAccountService;

    @Transactional
    @Override
    public GroupChatDto create(CreateGroupChatDto createGroupChatDto) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat entity = new GroupChat();
        Group group = groupRepository.get(createGroupChatDto.getGroupId());
        Account account = accountRepository.get(createGroupChatDto.getAccountId());
        if (authenticatedAccount.getId() != account.getId() || !groupAccountService.isGroupMember(group, account)) {
            throw new AccessDeniedException("access to create group chat from account " + account + " via account " + authenticatedAccount + " denied");
        }
        entity.setGroup(group);
        entity.setAccount(account);
        entity.setTitle(createGroupChatDto.getTitle());
        entity.setCreatedAt(LocalDateTime.now());
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupChatDto get(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat entity = repository.get(id);
        if (!groupAccountService.isGroupMember(entity.getGroup(), authenticatedAccount)) {
            throw new AccessDeniedException("access to read group chat via account " + authenticatedAccount + " denied");
        }
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public GroupChatDto changeTitle(long id, String text) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId() || !groupAccountService.isGroupMember(entity.getGroup(), entity.getAccount())) {
            throw new AccessDeniedException("access to change title group chat " + entity + " via account " + authenticatedAccount + " denied");
        }
        entity.setTitle(text);
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId() || !groupAccountService.isGroupMember(entity.getGroup(), entity.getAccount())) {
            throw new AccessDeniedException("access to delete group chat " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupChatMessageDto> getMessages(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat groupChat = repository.get(id);
        if (!groupAccountService.isGroupMember(groupChat.getGroup(), authenticatedAccount)) {
            throw new AccessDeniedException("access to read group chat messages from " + groupChat + " via account " + authenticatedAccount + " denied");
        }
        return messageMapper.entityListToDtoList(repository.getMessages(groupChat));
    }
}
