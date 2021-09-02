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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class GroupChatMessageServiceImpl implements GroupChatMessageService {
    private final GroupChatMessageRepository repository;
    private final GroupChatMessageMapper mapper;
    private final AccountRepository accountRepository;
    private final GroupChatRepository groupChatRepository;

    @Transactional
    @Override
    public GroupChatMessageDto create(CreateGroupChatMessageDto createChatCommentDto) {
        GroupChat groupChat = groupChatRepository.get(createChatCommentDto.getChatId());
        Account account = accountRepository.get(createChatCommentDto.getAccountId());
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
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public GroupChatMessageDto changeText(long id, String text) {
        GroupChatMessage entity = repository.get(id);
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.delete(repository.get(id));
    }
}
