package com.senlainc.service;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.DialogMessageRepository;
import com.senlainc.security.AuthenticationAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DialogMessageServiceTest {
    @Mock
    DialogMessageRepository repository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    DialogService dialogService;
    @Mock
    AuthenticationAccess authenticationAccess;

    DialogMessageMapper mapper = Mappers.getMapper(DialogMessageMapper.class);

    DialogMessageService service;

    @BeforeEach
    void setUp() {
        service = new DialogMessageServiceImpl(repository, mapper, accountRepository, dialogService, authenticationAccess);
    }

    @Test
    void shouldCreateMessageWhenAuthenticatedAccountHasAccess() {
        long fromAccountId = 1;
        long toAccountId = 2;
        CreateDialogMessageDto createDto = new CreateDialogMessageDto();
        createDto.setFromAccountId(fromAccountId);
        createDto.setToAccountId(toAccountId);
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        Dialog dialog = new Dialog();
        dialog.setAccount1(fromAccount);
        dialog.setAccount2(toAccount);
        DialogMessage entity = new DialogMessage();
        entity.setDialog(dialog);
        entity.setAccount(fromAccount);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        DialogMessageDto dto = mapper.entityToDto(entity);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(fromAccount);
        when(accountRepository.get(createDto.getFromAccountId())).thenReturn(fromAccount);
        when(accountRepository.get(createDto.getToAccountId())).thenReturn(toAccount);
        when(dialogService.getOrCreate(fromAccount, toAccount)).thenReturn(dialog);
        doAnswer((invocation) -> {
            DialogMessage t = invocation.getArgument(0);
            t.setCreatedAt(entity.getCreatedAt());
            t.setUpdatedAt(entity.getUpdatedAt());
            return null;
        }).when(repository).create(any());
        DialogMessageDto result = service.create(createDto);
        assertEquals(dto, result);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(accountRepository, times(1)).get(createDto.getFromAccountId());
        verify(accountRepository, times(1)).get(createDto.getToAccountId());
        verify(dialogService, times(1)).getOrCreate(fromAccount, toAccount);
        verify(repository, times(1)).create(any());
    }

    @Test
    void shouldGetMessageWhenAuthenticatedAccountHasAccess() {
        long account1Id = 1;
        long account2Id = 2;
        long messageId = 1;
        Account account1 = new Account();
        account1.setId(account1Id);
        Account account2 = new Account();
        account2.setId(account2Id);
        Dialog dialog = new Dialog();
        dialog.setAccount1(account1);
        dialog.setAccount2(account2);
        DialogMessage entity = new DialogMessage();
        entity.setId(messageId);
        entity.setDialog(dialog);
        entity.setAccount(account1);
        DialogMessageDto dto = mapper.entityToDto(entity);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account1);
        when(repository.get(messageId)).thenReturn(entity);
        DialogMessageDto result = service.get(messageId);
        assertEquals(dto, result);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
    }

    @Test
    void shouldChangeTextInMessageWhenAuthenticatedAccountHasAccess() {
        long accountId = 1;
        long messageId = 1;
        String text = "text";
        LocalDateTime updatedAt = LocalDateTime.now();
        Account account = new Account();
        account.setId(accountId);
        DialogMessage entity = new DialogMessage();
        entity.setId(messageId);
        entity.setAccount(account);
        entity.setText(text);
        entity.setUpdatedAt(updatedAt);
        DialogMessageDto dto = mapper.entityToDto(entity);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(messageId)).thenReturn(entity);
        doAnswer((invocation) -> {
            DialogMessage t = invocation.getArgument(0);
            t.setUpdatedAt(updatedAt);
            return null;
        }).when(repository).update(any());
        DialogMessageDto result = service.changeText(messageId, text);
        assertEquals(dto, result);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
        verify(repository, times(1)).update(any());
    }

    @Test
    void shouldDeleteMessageWhenAuthenticatedAccountHasAccess() {
        long accountId = 1;
        long messageId = 1;
        Account account = new Account();
        account.setId(accountId);
        DialogMessage entity = new DialogMessage();
        entity.setId(messageId);
        entity.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(messageId)).thenReturn(entity);
        doNothing().when(repository).delete(entity);
        service.delete(messageId);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
        verify(repository, times(1)).delete(entity);
    }
}
