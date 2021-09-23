package com.senlainc.service;

import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.InviteStatus;
import com.senlainc.mapper.FriendInviteMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.FriendInviteRepository;
import com.senlainc.security.AuthenticationAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendInviteServiceTest {
    @Mock
    FriendInviteRepository repository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    RelationService relationService;
    @Mock
    AuthenticationAccess authenticationAccess;

    FriendInviteMapper mapper = Mappers.getMapper(FriendInviteMapper.class);

    FriendInviteService service;

    @BeforeEach
    void setUp() {
        service = new FriendInviteServiceImpl(repository, mapper, accountRepository, relationService, authenticationAccess);
    }

    @Test
    void shouldCreateInviteWhenAuthenticatedAccountHasAccess() {
        long fromAccountId = 1;
        long toAccountId = 2;
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        CreateFriendInviteDto createDto = new CreateFriendInviteDto();
        createDto.setFromAccountId(fromAccountId);
        createDto.setToAccountId(toAccountId);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(fromAccount);
        when(accountRepository.get(fromAccountId)).thenReturn(fromAccount);
        when(accountRepository.get(toAccountId)).thenReturn(toAccount);
        when(relationService.isRelationExist(fromAccount, toAccount)).thenReturn(false);
        doNothing().when(repository).create(any());
        FriendInviteDto result = service.create(createDto);
        assertEquals(fromAccountId, result.getFromAccountId());
        assertEquals(toAccountId, result.getToAccountId());
        assertEquals(InviteStatus.Created, result.getStatus());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(accountRepository, times(1)).get(fromAccountId);
        verify(accountRepository, times(1)).get(toAccountId);
        verify(relationService, times(1)).isRelationExist(fromAccount, toAccount);
        verify(repository, times(1)).create(any());
    }

    @Test
    void shouldAcceptInviteWhenAuthenticatedAccountHasAccess() {
        long fromAccountId = 1;
        long toAccountId = 2;
        long inviteId = 1;
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        FriendInvite invite = new FriendInvite();
        invite.setFromAccount(fromAccount);
        invite.setToAccount(toAccount);
        invite.setStatus(InviteStatus.Created);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(toAccount);
        when(repository.get(inviteId)).thenReturn(invite);
        doNothing().when(relationService).createRelation(fromAccount, toAccount);
        doNothing().when(repository).update(any());
        service.acceptInvite(inviteId);
        assertEquals(InviteStatus.Accepted, invite.getStatus());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(inviteId);
        verify(relationService, times(1)).createRelation(fromAccount, toAccount);
        verify(repository, times(1)).update(any());
    }

    @Test
    void shouldRejectInviteWhenAuthenticatedAccountHasAccess() {
        long fromAccountId = 1;
        long toAccountId = 2;
        long inviteId = 1;
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        FriendInvite invite = new FriendInvite();
        invite.setFromAccount(fromAccount);
        invite.setToAccount(toAccount);
        invite.setStatus(InviteStatus.Created);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(toAccount);
        when(repository.get(inviteId)).thenReturn(invite);
        doNothing().when(repository).update(any());
        service.rejectInvite(inviteId);
        assertEquals(InviteStatus.Rejected, invite.getStatus());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(inviteId);
        verify(repository, times(1)).update(any());
    }
}
