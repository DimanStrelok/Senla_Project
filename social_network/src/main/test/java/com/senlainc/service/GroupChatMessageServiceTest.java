package com.senlainc.service;

import com.senlainc.dto.CreateGroupChatMessageDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupChat;
import com.senlainc.entity.GroupChatMessage;
import com.senlainc.mapper.GroupChatMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupChatMessageRepository;
import com.senlainc.repository.GroupChatRepository;
import com.senlainc.security.AuthenticationAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupChatMessageServiceTest {
    @Mock
    GroupChatMessageRepository repository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    GroupChatRepository groupChatRepository;
    @Mock
    GroupAccountService groupAccountService;
    @Mock
    AuthenticationAccess authenticationAccess;

    GroupChatMessageMapper mapper = Mappers.getMapper(GroupChatMessageMapper.class);

    GroupChatMessageService service;

    @BeforeEach
    void setUp() {
        service = new GroupChatMessageServiceImpl(
                repository, mapper, accountRepository, groupChatRepository, groupAccountService, authenticationAccess
        );
    }

    @Test
    void shouldCreateMessageWhenAuthenticatedAccountHasAccess() {
        long chatId = 1;
        long accountId = 1;
        CreateGroupChatMessageDto createDto = new CreateGroupChatMessageDto();
        createDto.setChatId(chatId);
        createDto.setAccountId(accountId);
        Group group = new Group();
        GroupChat groupChat = new GroupChat();
        groupChat.setId(chatId);
        groupChat.setGroup(group);
        Account account = new Account();
        account.setId(accountId);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(groupChatRepository.get(chatId)).thenReturn(groupChat);
        when(accountRepository.get(accountId)).thenReturn(account);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        doNothing().when(repository).create(any());
        GroupChatMessageDto result = service.create(createDto);
        assertEquals(chatId, result.getChatId());
        assertEquals(accountId, result.getAccountId());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(groupChatRepository, times(1)).get(chatId);
        verify(accountRepository, times(1)).get(accountId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
        verify(repository, times(1)).create(any());
    }

    @Test
    void shouldGetMessageWhenAuthenticatedAccountHasAccess() {
        long messageId = 1;
        long chatId = 1;
        long accountId = 1;
        Group group = new Group();
        GroupChat groupChat = new GroupChat();
        groupChat.setId(chatId);
        groupChat.setGroup(group);
        Account account = new Account();
        account.setId(accountId);
        GroupChatMessage message = new GroupChatMessage();
        message.setId(messageId);
        message.setChat(groupChat);
        message.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(messageId)).thenReturn(message);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        GroupChatMessageDto result = service.get(messageId);
        assertEquals(messageId, result.getId());
        assertEquals(chatId, result.getChatId());
        assertEquals(accountId, result.getAccountId());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
    }

    @Test
    void shouldChangeTextInMessageWhenAuthenticatedAccountHasAccess() {
        long messageId = 1;
        long chatId = 1;
        long accountId = 1;
        String text = "text";
        Group group = new Group();
        GroupChat groupChat = new GroupChat();
        groupChat.setId(chatId);
        groupChat.setGroup(group);
        Account account = new Account();
        account.setId(accountId);
        GroupChatMessage message = new GroupChatMessage();
        message.setId(messageId);
        message.setChat(groupChat);
        message.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(messageId)).thenReturn(message);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        doNothing().when(repository).update(any());
        GroupChatMessageDto result = service.changeText(messageId, text);
        assertEquals(messageId, result.getId());
        assertEquals(chatId, result.getChatId());
        assertEquals(accountId, result.getAccountId());
        assertEquals(text, result.getText());
        assertNotNull(result.getUpdatedAt());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
        verify(repository, times(1)).update(any());
    }

    @Test
    void shouldDeleteMessageWhenAuthenticatedAccountHasAccess() {
        long messageId = 1;
        Group group = new Group();
        GroupChat groupChat = new GroupChat();
        groupChat.setGroup(group);
        Account account = new Account();
        GroupChatMessage message = new GroupChatMessage();
        message.setId(messageId);
        message.setChat(groupChat);
        message.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(messageId)).thenReturn(message);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        doNothing().when(repository).delete(message);
        service.delete(messageId);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(messageId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
        verify(repository, times(1)).delete(message);
    }
}
