package com.senlainc.service;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import com.senlainc.mapper.DialogMapper;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.DialogRepository;
import com.senlainc.security.AuthenticationAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DialogServiceTest {
    @Mock
    DialogRepository repository;
    @Mock
    AuthenticationAccess authenticationAccess;

    DialogMapper mapper = Mappers.getMapper(DialogMapper.class);
    DialogMessageMapper messageMapper = Mappers.getMapper(DialogMessageMapper.class);

    DialogService service;

    @BeforeEach
    void setUp() {
        service = new DialogServiceImpl(repository, mapper, messageMapper, authenticationAccess);
    }

    @Test
    void shouldGetDialogWhenAuthenticatedAccountHasAccess() {
        long account1Id = 1;
        long account2Id = 2;
        long dialogId = 1;
        Account account1 = new Account();
        account1.setId(account1Id);
        Account account2 = new Account();
        account2.setId(account2Id);
        Dialog entity = new Dialog();
        entity.setId(dialogId);
        entity.setAccount1(account1);
        entity.setAccount2(account2);
        DialogDto dto = mapper.entityToDto(entity);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account1);
        when(repository.get(dialogId)).thenReturn(entity);
        DialogDto result = service.get(dialogId);
        assertEquals(dto, result);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(dialogId);
    }

    @Test
    void shouldCreateDialogWhenDialogBetweenAccountsDoesNotExist() {
        long fromAccountId = 1;
        long toAccountId = 2;
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        Dialog dialog = new Dialog();
        dialog.setAccount1(fromAccount);
        dialog.setAccount2(toAccount);
        when(repository.get(fromAccount, toAccount)).thenReturn(Optional.empty());
        doNothing().when(repository).create(any());
        Dialog result = service.getOrCreate(fromAccount, toAccount);
        assertEquals(fromAccountId, result.getAccount1().getId());
        assertEquals(toAccountId, result.getAccount2().getId());
        verify(repository, times(1)).get(fromAccount, toAccount);
        verify(repository, times(1)).create(any());
    }

    @Test
    void shouldGetDialogWhenDialogBetweenAccountsDoesExist() {
        long fromAccountId = 1;
        long toAccountId = 2;
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        Dialog dialog = new Dialog();
        dialog.setAccount1(fromAccount);
        dialog.setAccount2(toAccount);
        when(repository.get(fromAccount, toAccount)).thenReturn(Optional.of(dialog));
        Dialog result = service.getOrCreate(fromAccount, toAccount);
        assertEquals(fromAccountId, result.getAccount1().getId());
        assertEquals(toAccountId, result.getAccount2().getId());
        verify(repository, times(1)).get(fromAccount, toAccount);
    }

    @Test
    void shouldGetMessagesInDialogWhenAuthenticatedAccountHasAccess() {
        long account1Id = 1;
        long account2Id = 2;
        long dialogId = 1;
        Account account1 = new Account();
        account1.setId(account1Id);
        Account account2 = new Account();
        account2.setId(account2Id);
        Dialog dialog = new Dialog();
        dialog.setId(dialogId);
        dialog.setAccount1(account1);
        dialog.setAccount2(account2);
        DialogMessage message = new DialogMessage();
        message.setDialog(dialog);
        message.setAccount(account2);
        List<DialogMessage> entityList = new ArrayList<>();
        entityList.add(message);
        List<DialogMessageDto> dtoList = messageMapper.entityListToDtoList(entityList);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account1);
        when(repository.get(dialogId)).thenReturn(dialog);
        when(repository.getMessages(dialog)).thenReturn(entityList);
        List<DialogMessageDto> result = service.getMessages(dialogId);
        assertEquals(dtoList, result);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(dialogId);
    }
}
