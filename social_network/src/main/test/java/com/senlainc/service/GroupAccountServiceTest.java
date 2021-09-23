package com.senlainc.service;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupRole;
import com.senlainc.repository.GroupAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupAccountServiceTest {
    @Mock
    GroupAccountRepository repository;

    GroupAccountService service;

    @BeforeEach
    void setUp() {
        service = new GroupAccountServiceImpl(repository);
    }

    @Test
    void shouldAddAccountToGroupAsACreator() {
        long groupId = 1;
        long accountId = 1;
        GroupRole role = GroupRole.Creator;
        Group group = new Group();
        group.setId(groupId);
        Account account = new Account();
        account.setId(accountId);
        doNothing().when(repository).create(argThat((arg) ->
                arg.getGroup().getId() == groupId
                        && arg.getAccount().getId() == accountId
                        && arg.getRole() == role
        ));
        service.createCreator(group, account);
        verify(repository, times(1)).create(argThat((arg) ->
                arg.getGroup().getId() == groupId
                        && arg.getAccount().getId() == accountId
                        && arg.getRole() == role
        ));
    }

    @Test
    void shouldAddAccountToGroupAsAMember() {
        long groupId = 1;
        long accountId = 1;
        GroupRole role = GroupRole.Member;
        Group group = new Group();
        group.setId(groupId);
        Account account = new Account();
        account.setId(accountId);
        doNothing().when(repository).create(argThat((arg) ->
                arg.getGroup().getId() == groupId
                        && arg.getAccount().getId() == accountId
                        && arg.getRole() == role
        ));
        service.createMember(group, account);
        verify(repository, times(1)).create(argThat((arg) ->
                arg.getGroup().getId() == groupId
                        && arg.getAccount().getId() == accountId
                        && arg.getRole() == role
        ));
    }

    @Test
    void shouldReturnTrueWhenAccountIsAGroupMember() {
        Group group = new Group();
        Account account = new Account();
        when(repository.isMember(group, account)).thenReturn(true);
        assertTrue(service.isGroupMember(group, account));
        verify(repository, times(1)).isMember(group, account);
    }

    @Test
    void shouldReturnFalseWhenAccountIsNotAGroupMember() {
        Group group = new Group();
        Account account = new Account();
        when(repository.isMember(group, account)).thenReturn(false);
        assertFalse(service.isGroupMember(group, account));
        verify(repository, times(1)).isMember(group, account);
    }

    @Test
    void shouldGetGroupCreatorAccount() {
        long accountId = 1;
        Group group = new Group();
        Account creator = new Account();
        creator.setId(accountId);
        when(repository.getGroupCreator(group)).thenReturn(creator);
        Account result = service.getGroupCreator(group);
        assertEquals(accountId, result.getId());
        verify(repository, times(1)).getGroupCreator(group);
    }
}
