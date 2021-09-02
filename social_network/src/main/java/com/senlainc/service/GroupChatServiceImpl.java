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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupChatServiceImpl implements GroupChatService {
    private final GroupChatRepository repository;
    private final GroupChatMapper mapper;
    private final GroupChatMessageMapper messageMapper;
    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public GroupChatDto create(CreateGroupChatDto createGroupChatDto) {
        GroupChat entity = new GroupChat();
        Group group = groupRepository.get(createGroupChatDto.getGroupId());
        Account account = accountRepository.get(createGroupChatDto.getAccountId());
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
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public GroupChatDto changeTitle(long id, String text) {
        GroupChat entity = repository.get(id);
        entity.setTitle(text);
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.delete(repository.get(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupChatMessageDto> getMessages(long id) {
        GroupChat groupChat = repository.get(id);
        return messageMapper.entityListToDtoList(repository.getMessages(groupChat));
    }
}
