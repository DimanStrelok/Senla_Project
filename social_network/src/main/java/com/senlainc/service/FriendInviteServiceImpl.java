package com.senlainc.service;

import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.InviteStatus;
import com.senlainc.exception.AppException;
import com.senlainc.mapper.FriendInviteMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.FriendInviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FriendInviteServiceImpl implements FriendInviteService {
    private final FriendInviteRepository repository;
    private final FriendInviteMapper mapper;
    private final AccountRepository accountRepository;
    private final RelationService relationService;

    @Transactional
    @Override
    public FriendInviteDto create(CreateFriendInviteDto createFriendInviteDto) {
        long fromAccountId = createFriendInviteDto.getFromAccountId();
        long toAccountId = createFriendInviteDto.getToAccountId();
        Account fromAccount = accountRepository.get(fromAccountId);
        Account toAccount = accountRepository.get(toAccountId);
        if (relationService.isRelationExist(fromAccount, toAccount)) {
            throw new AppException("Account with id = " + fromAccountId + " and account with id = " + toAccountId + " already have relation");
        }
        FriendInvite entity = new FriendInvite();
        entity.setFromAccount(fromAccount);
        entity.setToAccount(toAccount);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(InviteStatus.Created);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void acceptInvite(long id) {
        FriendInvite entity = repository.get(id);
        if (entity.getStatus() == InviteStatus.Created) {
            relationService.createRelation(entity.getFromAccount(), entity.getToAccount());
            entity.setStatus(InviteStatus.Accepted);
            repository.update(entity);
        } else {
            throw new AppException("to accept friend invite, it must have status 'Created'");
        }
    }

    @Transactional
    @Override
    public void rejectInvite(long id) {
        FriendInvite entity = repository.get(id);
        if (entity.getStatus() == InviteStatus.Created) {
            entity.setStatus(InviteStatus.Rejected);
            repository.update(entity);
        } else {
            throw new AppException("to reject friend invite, it must have status 'Created'");
        }
    }
}
