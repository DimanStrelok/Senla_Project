package com.senlainc.service;

import com.senlainc.dto.CreateGroupChatMessageDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.GroupChat;
import com.senlainc.entity.GroupChatMessage;
import com.senlainc.mapper.GroupChatMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupChatMessageRepository;
import com.senlainc.repository.GroupChatRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class GroupChatMessageServiceImpl implements GroupChatMessageService, AuthenticationAccess {
    private final GroupChatMessageRepository repository;
    private final GroupChatMessageMapper mapper;
    private final AccountRepository accountRepository;
    private final GroupChatRepository groupChatRepository;
    private final GroupAccountService groupAccountService;

    @Transactional
    @Override
    public GroupChatMessageDto create(CreateGroupChatMessageDto createChatCommentDto) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChat groupChat = groupChatRepository.get(createChatCommentDto.getChatId());
        Account account = accountRepository.get(createChatCommentDto.getAccountId());
        if (authenticatedAccount.getId() != account.getId() || !groupAccountService.isGroupMember(groupChat.getGroup(), account)) {
            throw new AccessDeniedException("access to create group chat message from account " + account + " via account " + authenticatedAccount + " denied");
        }
        LocalDateTime now = LocalDateTime.now();
        GroupChatMessage entity = new GroupChatMessage();
        entity.setChat(groupChat);
        entity.setAccount(account);
        entity.setText(createChatCommentDto.getText());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupChatMessageDto get(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChatMessage entity = repository.get(id);
        if (!groupAccountService.isGroupMember(entity.getChat().getGroup(), authenticatedAccount)) {
            throw new AccessDeniedException("access to read group chat message " + entity + " via account " + authenticatedAccount + " denied");
        }
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public GroupChatMessageDto changeText(long id, String text) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChatMessage entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId() || !groupAccountService.isGroupMember(entity.getChat().getGroup(), entity.getAccount())) {
            throw new AccessDeniedException("access to update group chat message " + entity + " via account " + authenticatedAccount + " denied");
        }
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        GroupChatMessage entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId() || !groupAccountService.isGroupMember(entity.getChat().getGroup(), entity.getAccount())) {
            throw new AccessDeniedException("access delete group chat message " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }
}
