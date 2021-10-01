package com.senlainc.service;

import com.senlainc.dto.GroupChatDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupChat;
import com.senlainc.mapper.GroupChatMapper;
import com.senlainc.mapper.GroupChatMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.GroupChatRepository;
import com.senlainc.repository.GroupRepository;
import com.senlainc.security.AuthenticationAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupChatServiceTest {
    @Mock
    GroupChatRepository repository;
    @Mock
    GroupRepository groupRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    GroupAccountService groupAccountService;
    @Mock
    AuthenticationAccess authenticationAccess;

    GroupChatMapper mapper = Mappers.getMapper(GroupChatMapper.class);
    GroupChatMessageMapper messageMapper = Mappers.getMapper(GroupChatMessageMapper.class);

    GroupChatService service;

    @BeforeEach
    void setUp() {
        service = new GroupChatServiceImpl(
                repository, mapper, messageMapper, groupRepository,
                accountRepository, groupAccountService, authenticationAccess
        );
    }

    @Test
    void create() {
    }

    @Test
    void get() {
    }

    @Test
    void shouldChangeTitleInGroupChatWhenAuthenticatedAccountHasAccess() {
        long groupId = 1;
        long accountId = 1;
        long chatId = 1;
        String title = "title";
        Group group = new Group();
        group.setId(groupId);
        Account account = new Account();
        account.setId(accountId);
        GroupChat groupChat = new GroupChat();
        groupChat.setId(chatId);
        groupChat.setGroup(group);
        groupChat.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(chatId)).thenReturn(groupChat);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        doNothing().when(repository).update(any());
        GroupChatDto result = service.changeTitle(chatId, title);
        assertEquals(chatId, result.getId());
        assertEquals(groupId, result.getGroupId());
        assertEquals(accountId, result.getAccountId());
        assertEquals(title, result.getTitle());
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(chatId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
        verify(repository, times(1)).update(any());
    }

    @Test
    void shouldDeleteGroupChatWhenAuthenticatedAccountHasAccess() {
        long groupId = 1;
        long accountId = 1;
        long chatId = 1;
        Group group = new Group();
        group.setId(groupId);
        Account account = new Account();
        account.setId(accountId);
        GroupChat groupChat = new GroupChat();
        groupChat.setId(chatId);
        groupChat.setGroup(group);
        groupChat.setAccount(account);
        when(authenticationAccess.getAuthenticatedAccount()).thenReturn(account);
        when(repository.get(chatId)).thenReturn(groupChat);
        when(groupAccountService.isGroupMember(group, account)).thenReturn(true);
        doNothing().when(repository).delete(groupChat);
        service.delete(chatId);
        verify(authenticationAccess, times(1)).getAuthenticatedAccount();
        verify(repository, times(1)).get(chatId);
        verify(groupAccountService, times(1)).isGroupMember(group, account);
        verify(repository, times(1)).delete(groupChat);
    }

    @Test
    void getMessages() {
    }
}
