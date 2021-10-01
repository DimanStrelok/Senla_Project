package com.senlainc.service;

import com.senlainc.dto.*;
import com.senlainc.entity.Account;
import com.senlainc.mapper.*;
import com.senlainc.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final PostMapper postMapper;
    private final DialogMapper dialogMapper;
    private final FriendInviteMapper friendInviteMapper;
    private final GroupMapper groupMapper;
    private final GroupInviteMapper groupInviteMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public AccountDto create(CreateAccountDto createAccountDto) {
        Account entity = mapper.entityFromDto(createAccountDto);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public AccountDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @PreAuthorize("#accountDto.id == authentication.principal.id")
    @Transactional
    @Override
    public AccountDto update(AccountDto accountDto) {
        Account entity = repository.get(accountDto.getId());
        entity.setFirstName(accountDto.getFirstName());
        entity.setLastName(accountDto.getLastName());
        entity.setMiddleName(accountDto.getMiddleName());
        entity.setEmail(accountDto.getEmail());
        entity.setPhoneNumber(accountDto.getPhoneNumber());
        entity.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        entity.setCity(accountDto.getCity());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @Transactional
    @Override
    public void delete(long id) {
        Account account = repository.get(id);
        repository.delete(account);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> getAccounts() {
        return mapper.entityListToDtoList(repository.getAccounts());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> findAccounts(FindAccountDto findAccountDto) {
        return mapper.entityListToDtoList(repository.findAccounts(findAccountDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getPosts(long id) {
        Account account = repository.get(id);
        return postMapper.entityListToDtoList(repository.getPosts(account));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @Transactional(readOnly = true)
    @Override
    public List<DialogDto> getDialogs(long id) {
        Account account = repository.get(id);
        return dialogMapper.entityListToDtoList(repository.getDialogs(account));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> getFriends(long id) {
        Account account = repository.get(id);
        return mapper.entityListToDtoList(repository.getFriends(account));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @Transactional(readOnly = true)
    @Override
    public List<FriendInviteDto> getFriendInvites(long id) {
        Account account = repository.get(id);
        return friendInviteMapper.entityListToDtoList(repository.getFriendInvites(account));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupDto> getGroups(long id) {
        Account account = repository.get(id);
        return groupMapper.entityListToDtoList(repository.getGroups(account));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @Transactional(readOnly = true)
    @Override
    public List<GroupInviteDto> getGroupInvites(long id) {
        Account account = repository.get(id);
        return groupInviteMapper.entityListToDtoList(repository.getGroupInvites(account));
    }
}
