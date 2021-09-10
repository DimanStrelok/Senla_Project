package com.senlainc.service;

import com.senlainc.AppConfig;
import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.mapper.FriendInviteMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.DialogMessageRepository;
import com.senlainc.security.AccountPrincipal;
import com.senlainc.security.AppSecurityConfig;
import liquibase.pro.packaged.A;
import liquibase.pro.packaged.D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(setupBefore = TestExecutionEvent.TEST_EXECUTION, factory = WithMockCustomUserSecurityContextFactory.class)
@interface WithMockCustomUser {
    long accountId() default 1;
}

class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Account account = new Account();
        account.setId(customUser.accountId());
        AccountPrincipal principal = new AccountPrincipal(account);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class DialogMessageServiceImplTest {
    private final DialogMessageMapper dialogMessageMapper = Mappers.getMapper(DialogMessageMapper.class);

    @Mock
    DialogMessageRepository dialogMessageRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    DialogService dialogService;

    @Test
    @WithMockCustomUser(accountId = 1)
    void shouldCreateDialogMessageInNewDialog() {
        long fromAccountId = 1;
        long toAccountId = 2;
        String text = "text";
        long dialogId = 1;
        CreateDialogMessageDto createDialogMessageDto = new CreateDialogMessageDto();
        createDialogMessageDto.setFromAccountId(fromAccountId);
        createDialogMessageDto.setText(text);
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        Dialog dialog = new Dialog();
        dialog.setId(dialogId);
        dialog.setAccount1(fromAccount);
        dialog.setAccount2(toAccount);
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setAccount(fromAccount);
        dialogMessage.setText(text);
        when(accountRepository.get(fromAccountId)).thenReturn(fromAccount);
        when(accountRepository.get(toAccountId)).thenReturn(toAccount);
        when(dialogService.getOrCreate(fromAccount, toAccount)).thenReturn(dialog);
        doNothing().when(dialogMessageRepository).create(dialogMessage);
        DialogMessageService dialogMessageService = new DialogMessageServiceImpl(
                dialogMessageRepository, dialogMessageMapper, accountRepository, dialogService
        );
        DialogMessageDto result = dialogMessageService.create(createDialogMessageDto);
        assertEquals(fromAccountId, result.getAccountId());
        assertEquals(dialogId, result.getDialogId());
        assertEquals(text, result.getText());
        verify(accountRepository, times(1)).get(fromAccountId);
        verify(accountRepository, times(1)).get(toAccountId);
        verify(dialogService, times(1)).getOrCreate(fromAccount, toAccount);
        verify(dialogMessageRepository, times(1)).create(dialogMessage);

    }

    @Test
    @WithMockCustomUser(accountId = 2)
    void shouldThrowWhenCreateDialogMessageBecauseIncorrectAuthenticatedAccount() {
        long fromAccountId = 1;
        long toAccountId = 2;
        String text = "text";
        long dialogId = 1;
        CreateDialogMessageDto createDialogMessageDto = new CreateDialogMessageDto();
        createDialogMessageDto.setFromAccountId(fromAccountId);
        createDialogMessageDto.setText(text);
        Account fromAccount = new Account();
        fromAccount.setId(fromAccountId);
        Account toAccount = new Account();
        toAccount.setId(toAccountId);
        Dialog dialog = new Dialog();
        dialog.setId(dialogId);
        dialog.setAccount1(fromAccount);
        dialog.setAccount1(toAccount);
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setAccount(fromAccount);
        dialogMessage.setText(text);
        when(accountRepository.get(fromAccountId)).thenReturn(fromAccount);
        when(accountRepository.get(toAccountId)).thenReturn(toAccount);
        when(dialogService.getOrCreate(fromAccount, toAccount)).thenReturn(dialog);
        doNothing().when(dialogMessageRepository).create(dialogMessage);
        DialogMessageService dialogMessageService = new DialogMessageServiceImpl(
                dialogMessageRepository, dialogMessageMapper, accountRepository, dialogService
        );
        assertThrows(AccessDeniedException.class, () -> dialogMessageService.create(createDialogMessageDto));
        verify(accountRepository, times(1)).get(fromAccountId);
        verify(accountRepository, times(1)).get(toAccountId);

    }

    @Test
    void get() {
    }

    @Test
    void changeText() {
    }

    @Test
    void delete() {
    }
}
