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
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
    private final AuthenticationAccess authenticationAccess;

    @Transactional
    @Override
    public FriendInviteDto create(CreateFriendInviteDto createFriendInviteDto) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        long fromAccountId = createFriendInviteDto.getFromAccountId();
        long toAccountId = createFriendInviteDto.getToAccountId();
        Account fromAccount = accountRepository.get(fromAccountId);
        Account toAccount = accountRepository.get(toAccountId);
        if (authenticatedAccount.getId() != fromAccount.getId()) {
            throw new AccessDeniedException("access to create friend invite from account " + fromAccount + " via account " + authenticatedAccount + " denied");
        }
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
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        FriendInvite entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getToAccount().getId()) {
            throw new AccessDeniedException("access to accept friend invite " + entity + " via account " + authenticatedAccount + " denied");
        }
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
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        FriendInvite entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getToAccount().getId()) {
            throw new AccessDeniedException("access to reject friend invite " + entity + " via account " + authenticatedAccount + " denied");
        }
        if (entity.getStatus() == InviteStatus.Created) {
            entity.setStatus(InviteStatus.Rejected);
            repository.update(entity);
        } else {
            throw new AppException("to reject friend invite, it must have status 'Created'");
        }
    }
}
