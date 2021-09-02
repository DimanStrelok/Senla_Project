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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final GroupMapper mapper;
    private final GroupChatMapper chatMapper;
    private final AccountRepository accountRepository;
    private final GroupAccountService groupAccountService;

    @Transactional
    @Override
    public GroupDto create(CreateGroupDto createGroupDto) {
        Account account = accountRepository.get(createGroupDto.getAccountId());
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
        Group entity = repository.get(groupDto.getId());
        entity.setTitle(groupDto.getTitle());
        entity.setDescription(groupDto.getDescription());
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
    public List<GroupDto> getGroups() {
        return mapper.entityListToDtoList(repository.getGroups());
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupChatDto> getChats(long id) {
        Group group = repository.get(id);
        return chatMapper.entityListToDtoList(repository.getChats(group));
    }
}
